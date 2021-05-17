<template>
  <TableLayout v-permissions="['system:user:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="80px" inline>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="searchForm.username" placeholder="请输入用户名" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="realname">
        <el-input v-model="searchForm.realname" placeholder="请输入姓名" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="手机号码" prop="mobile">
        <el-input v-model="searchForm.mobile" placeholder="请输入手机号码" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <section>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </section>
    </el-form>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:user:create', 'system:user:delete']">
        <li v-permissions="['system:user:create']"><el-button icon="el-icon-plus" type="primary" @click="create">新建</el-button></li>
        <li v-permissions="['system:user:delete']"><el-button icon="el-icon-delete" @click="deleteByIdInBatch">删除</el-button></li>
      </ul>
      <el-table
        v-loading="isWorking.search"
        :data="tableData.list"
        :default-sort = "{prop: 'date', order: 'descending'}"
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="avatar" label="头像" width="80px" class-name="table-column-avatar" fixed="left">
          <template slot-scope="{row}">
            <img :src="row.avatar == null ? '/static/avatar/man.png' : row.avatar">
          </template>
        </el-table-column>
        <el-table-column prop="realname" label="姓名" min-width="100px" fixed="left"></el-table-column>
        <el-table-column prop="username" label="用户名" min-width="100px"></el-table-column>
        <el-table-column prop="empNo" label="工号" min-width="80px"></el-table-column>
        <el-table-column prop="sex" label="性别" min-width="80px">
          <template slot-scope="{row}">
            {{row.sex | sex}}
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="160px"></el-table-column>
        <el-table-column prop="mobile" label="手机号码" min-width="100px"></el-table-column>
        <el-table-column prop="birthday" label="生日" min-width="100px"></el-table-column>
        <el-table-column prop="birthday" label="角色" min-width="160px" class-name="table-column-role">
          <template slot-scope="{row}">
            <ul>
              <li v-for="role in row.roles" :key="role.id">{{role.name}}</li>
            </ul>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="140px"></el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column label="操作" width="280" fixed="right">
          <template slot-scope="{row}">
            <el-button type="text" icon="el-icon-edit" @click="edit(row)" v-permissions="['system:user:update']">编辑</el-button>
            <el-button type="text" icon="el-icon-s-custom" @click="$refs.roleConfigWindow.open(row)" v-permissions="['system:user:createUserRole']">配置角色</el-button>
            <el-button type="text" @click="$refs.resetPwdWindow.open(row)">重置密码</el-button>
            <el-button type="text" icon="el-icon-delete" @click="deleteById(row.id)" v-permissions="['system:user:delete']">删除</el-button>
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
    <!-- 添加/修改 -->
    <OperaUserWindow
      ref="operaUserWindow"
      :title="operaTableData.title"
      :visible.sync="visible.operaTable"
      @create-success="search"
      @edit-success="search"
    />
    <!-- 配置角色 -->
    <RoleConfigWindow
      ref="roleConfigWindow"
      @success="search"
    />
    <!-- 重置密码 -->
    <ResetPwdWindow
      ref="resetPwdWindow"
      :visible.sync="visible.resetPwd"
      :user="resetPwdData.user"
    />
  </TableLayout>
</template>

<script>
import Pagination from '../../components/common/Pagination'
import TableLayout from '../../layouts/TableLayout'
import { fetchList, deleteById, deleteByIdInBatch } from '../../api/system/systemUser'
import BaseTable from '../BaseTable'
import OperaUserWindow from '../../components/user/OperaTableDataWindow'
import RoleConfigWindow from '../../components/user/RoleConfigWindow'
import ResetPwdWindow from '../../components/user/ResetPwdWindow'

export default {
  name: 'SystemUser',
  extends: BaseTable,
  components: { ResetPwdWindow, RoleConfigWindow, OperaUserWindow, TableLayout, Pagination },
  data () {
    return {
      // 搜索
      searchForm: {
        username: '', // 名字
        realname: '', // 姓名
        mobile: '' // 手机号码
      },
      // 添加/修改数据
      operaTableData: {
        title: '添加用户'
      },
      // 配置角色数据
      selectRoleData: {
        user: null,
        roles: []
      },
      // 重置密码
      resetPwdData: {
        user: null
      }
    }
  },
  methods: {
    // 添加
    create () {
      this.operaTableData.title = '添加用户'
      this.visible.operaTable = true
      this.$nextTick(() => {
        this.$refs.operaUserWindow.resetFields()
      })
    },
    // 编辑
    edit (row) {
      this.operaTableData.title = '修改用户'
      this.visible.operaTable = true
      this.$nextTick(() => {
        this.$refs.operaUserWindow.initFields(row)
      })
    },
    // 删除
    deleteById (id) {
      this.$confirm('确认删除此用户吗?', '提示', {
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
    }
  },
  created () {
    this.search()
  }
}
</script>

<style scoped lang="scss">
@import "../../assets/style/variables.scss";
// 列表头像处理
.table-column-avatar {
  img {
    width: 48px;
  }
}
// 列表角色处理
.table-column-role {
  ul {
    li {
      display: inline-block;
      background: #eee;
      border-radius: 3px;
      padding: 0 3px;
      margin-right: 3px;
    }
  }
}
</style>
