<template>
  <TableLayout v-permissions="['system:role:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="100px" inline>
      <el-form-item label="角色CODE" prop="code">
        <el-input v-model="searchForm.code" placeholder="请输入角色CODE" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="searchForm.name" placeholder="请输入角色名称" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <section>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </section>
    </el-form>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:role:create', 'system:role:delete']">
        <li v-permissions="['system:role:create']"><el-button type="primary" @click="$refs.operaRoleWindow.open('新建角色')" icon="el-icon-plus">新建</el-button></li>
        <li v-permissions="['system:role:delete']"><el-button @click="deleteByIdInBatch" icon="el-icon-delete">删除</el-button></li>
      </ul>
      <el-table
        v-loading="isWorking.search"
        :data="tableData.list"
        :default-sort = "{prop: 'date', order: 'descending'}"
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="code" label="角色CODE" min-width="100px"></el-table-column>
        <el-table-column prop="name" label="角色名称" min-width="100px"></el-table-column>
        <el-table-column prop="remark" label="角色备注" min-width="120px"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="140px"></el-table-column>
        <el-table-column prop="updateUser" label="更新者" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column
          v-if="containPermissions(['system:role:update', 'system:role:createRolePermission', 'system:role:createRoleMenu', 'system:role:delete'])"
          label="操作"
          min-width="280"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button type="text" @click="$refs.operaRoleWindow.open('编辑角色', row)" icon="el-icon-edit" v-permissions="['system:role:update']">编辑</el-button>
            <el-button type="text" @click="selectPermission(row)" v-permissions="['system:role:createRolePermission']">配置权限</el-button>
            <el-button type="text" @click="selectMenu(row)" icon="el-icon-menu" v-permissions="['system:role:createRoleMenu']">授权菜单</el-button>
            <el-button type="text" @click="deleteById(row.id)" icon="el-icon-delete" v-permissions="['system:role:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
        :pagination="tableData.pagination"
      >
      </pagination>
    </template>
    <!-- 新建/修改 -->
    <OperaRoleWindow ref="operaRoleWindow" @create-success="search" @edit-success="handlePageChange(tableData.pagination.pageIndex)"/>
    <!-- 配置权限 -->
    <GlobalWindow
      class="permission-config-dialog"
      :visible.sync="visible.selectPermission"
      :confirm-working="isWorking.selectPermission"
      width="576px"
      title="配置角色权限"
      @confirm="confirmSelectPermission"
      @close="visible.selectPermission = false"
    >
      <p class="tip" v-if="selectPermissionData.role != null">为角色 <em>{{selectPermissionData.role.name}}</em> 配置权限</p>
      <el-transfer
        ref="permissionTransfer"
        v-model="selectPermissionData.selectedIds"
        filterable
        :filter-method="filterPermissions"
        :titles="['未授权权限', '已授权权限']"
        :props="{
          key: 'id',
          label: 'name'
        }"
        :data="selectPermissionData.permissions">
      </el-transfer>
    </GlobalWindow>
    <!-- 授权菜单 -->
    <GlobalWindow
      class="menu-config-dialog"
      :visible.sync="visible.selectMenu"
      :confirm-working="isWorking.selectMenu"
      width="576px"
      title="授权菜单"
      @confirm="confirmSelectMenu"
      @close="visible.selectMenu = false"
    >
      <p class="tip" v-if="selectMenuData.role != null">为角色 <em>{{selectMenuData.role.name}}</em> 配置可访问的菜单</p>
      <el-tree
        ref="menuTree"
        :data="selectMenuData.menus"
        show-checkbox
        node-key="id"
        default-expand-all
        :default-checked-keys="selectMenuData.selectedIds"
        :expand-on-click-node="false"
        :check-on-click-node="true"
        :props="{children: 'children',label: 'name'}">
      </el-tree>
    </GlobalWindow>
  </TableLayout>
</template>

<script>
import Pagination from '../../components/common/Pagination'
import GlobalWindow from '../../components/common/GlobalWindow'
import TableLayout from '../../layouts/TableLayout'
import { fetchList, deleteById, deleteByIdInBatch, createRolePermission, createRoleMenu } from '../../api/system/role'
import { fetchList as fetchPermissionList } from '../../api/system/permission'
import { fetchList as fetchMenuList } from '../../api/system/menu'
import BaseTable from '../BaseTable'
import OperaRoleWindow from '../../components/role/OperaRoleWindow'
export default {
  name: 'SystemRole',
  extends: BaseTable,
  components: { OperaRoleWindow, TableLayout, GlobalWindow, Pagination },
  data () {
    return {
      // 是否展示
      visible: {
        selectPermission: false,
        selectMenu: false
      },
      // 是否正在处理
      isWorking: {
        selectPermission: false,
        selectMenu: false
      },
      // 搜索
      searchForm: {
        code: '',
        name: '',
        remark: ''
      },
      // 配置权限数据
      selectPermissionData: {
        role: null,
        permissions: [],
        selectedIds: []
      },
      // 授权菜单数据
      selectMenuData: {
        role: null,
        menus: [],
        selectedIds: []
      }
    }
  },
  methods: {
    // 删除
    deleteById (id) {
      this.$confirm('确认删除此角色吗?', '提示', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.isWorking.delete = true
        deleteById(id)
          .then(() => {
            this.$message.success('删除成功')
            // 删除当前页最后一条记录时查询上一页数据
            if (this.tableData.list.length - 1 === 0) {
              this.handlePageChange(this.tableData.pagination.pageIndex - 1 === 0 ? 1 : this.tableData.pagination.pageIndex - 1)
            } else {
              this.handlePageChange(this.tableData.pagination.pageIndex)
            }
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking.delete = false
          })
      })
    },
    // 批量删除
    deleteByIdInBatch () {
      if (this.tableData.selectedRows.length === 0) {
        this.$message.warning('请至少选择一条数据')
        return
      }
      this.$confirm(`确认删除已选中的 ${this.tableData.selectedRows.length} 条数据吗?`, '提示', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.isWorking.delete = true
        deleteByIdInBatch(this.tableData.selectedRows.map(row => row.id).join(','))
          .then(() => {
            this.$message.success('删除成功')
            // 删除当前页最后一条记录时查询上一页数据
            if (this.tableData.list.length - 1 === 0) {
              this.handlePageChange(this.tableData.pagination.pageIndex - 1 === 0 ? 1 : this.tableData.pagination.pageIndex - 1)
            } else {
              this.handlePageChange(this.tableData.pagination.pageIndex)
            }
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking.delete = false
          })
      })
    },
    // 页码变更处理
    handlePageChange (pageIndex) {
      // 调用查询接口
      this.tableData.pagination.pageIndex = pageIndex
      this.isWorking.search = true
      fetchList({
        page: pageIndex,
        capacity: this.tableData.pagination.pageSize,
        model: this.searchForm
      })
        .then(data => {
          this.tableData.list = data.records
          this.tableData.pagination.total = data.total
        })
        .catch(e => {
          this.$message.error(e.message)
        })
        .finally(() => {
          this.isWorking.search = false
        })
    },
    // 选择权限
    selectPermission (row) {
      if (this.$refs.permissionTransfer) {
        this.$refs.permissionTransfer.clearQuery('left')
        this.$refs.permissionTransfer.clearQuery('right')
      }
      fetchPermissionList({
        page: 1,
        capacity: 100000
      })
        .then(data => {
          this.selectPermissionData.role = row
          this.selectPermissionData.permissions = data.records
          this.selectPermissionData.selectedIds = row.permissions.map(r => r.id)
          this.visible.selectPermission = true
        })
        .catch(e => {
          this.$message.error(e.message)
        })
    },
    // 确认选择权限
    confirmSelectPermission () {
      if (this.isWorking.selectPermission) {
        return
      }
      this.isWorking.selectPermission = true
      createRolePermission({
        roleId: this.selectPermissionData.role.id,
        permissionIds: this.selectPermissionData.selectedIds
      })
        .then(() => {
          this.$message.success('权限配置成功，用户重新登录后生效')
          this.visible.selectPermission = false
          this.handlePageChange(1)
        })
        .catch(e => {
          this.$message.error(e.message)
        })
        .finally(() => {
          this.isWorking.selectPermission = false
        })
    },
    // 搜索权限
    filterPermissions (query, item) {
      const lowerCaseQuery = query.toLowerCase()
      return item.code.toLowerCase().indexOf(lowerCaseQuery) > -1 || item.name.toLowerCase().indexOf(lowerCaseQuery) > -1
    },
    // 选择菜单
    selectMenu (row) {
      fetchMenuList({})
        .then(records => {
          // 重置disabled为false，避免无法选中
          records.map(r => {
            r.disabled = false
          })
          this.selectMenuData.role = row
          this.selectMenuData.menus = records
          // 找出叶节点
          row.menus = row.menus.filter(menu => row.menus.findIndex(m => m.parentId === menu.id) === -1)
          this.selectMenuData.selectedIds = row.menus.map(r => r.id)
          this.visible.selectMenu = true
        })
        .catch(e => {
          this.$message.error(e.message)
        })
    },
    // 确认选择权限
    confirmSelectMenu () {
      if (this.isWorking.selectMenu) {
        return
      }
      const selectedMenus = this.$refs.menuTree.getCheckedNodes(false, true)
      this.isWorking.selectMenu = true
      createRoleMenu({
        roleId: this.selectMenuData.role.id,
        menuIds: selectedMenus.map(menu => menu.id)
      })
        .then(() => {
          this.$message.success('菜单授权成功')
          this.visible.selectMenu = false
          this.handlePageChange(1)
        })
        .catch(e => {
          this.$message.error(e.message)
        })
        .finally(() => {
          this.isWorking.selectMenu = false
        })
    }
  },
  created () {
    this.search()
  }
}
</script>

<style lang="scss" scoped>
@import "../../assets/style/variables.scss";
// 角色配置/菜单授权
.permission-config-dialog,
.menu-config-dialog {
  .tip {
    margin-bottom: 12px;
    em {
      font-style: normal;
      color: $primary-color;
      font-weight: bold;
    }
  }
}
</style>
