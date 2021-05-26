<template>
  <TableLayout class="menu-layout" v-permissions="['system:menu:query']">
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:menu:create', 'system:menu:delete', 'system:menu:sort']">
        <li><el-button type="primary" @click="$refs.operaMenuWindow.open('新建一级菜单')" icon="el-icon-plus" v-permissions="['system:menu:create']">新建</el-button></li>
        <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete" v-permissions="['system:menu:delete']">删除</el-button></li>
        <li><el-button @click="sort('top')" :loading="isWorking.sort" icon="el-icon-sort-up" v-permissions="['system:menu:sort']">上移</el-button></li>
        <li><el-button @click="sort('bottom')" :loading="isWorking.sort" icon="el-icon-sort-down" v-permissions="['system:menu:sort']">下移</el-button></li>
      </ul>
      <el-table
        ref="table"
        v-loading="isWorking.search"
        :data="tableData.list"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        row-key="id"
        stripe
        default-expand-all
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" fixed="left"></el-table-column>
        <el-table-column prop="name" label="菜单名称"  fixed="left" min-width="160px"></el-table-column>
        <el-table-column prop="icon" label="图标" min-width="80px" class-name="table-column-icon">
          <template slot-scope="{row}">
            <i v-if="row.icon != null && row.icon !== ''" :class="{[row.icon]: true}"></i>
            <template v-else>未设置</template>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="访问路径" min-width="140px"></el-table-column>
        <el-table-column prop="remark" label="备注" min-width="120px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px"></el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="140px"></el-table-column>
        <el-table-column prop="disabled" label="是否启用" min-width="80px">
          <template slot-scope="{row}">
            <el-switch v-model="row.disabled" :active-value="false" :inactive-value="true" @change="switchDisabled(row)"/>
          </template>
        </el-table-column>
        <el-table-column
          v-if="containPermissions(['system:menu:update', 'system:menu:create', 'system:menu:delete'])"
          label="操作"
          min-width="220"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button type="text" icon="el-icon-edit" @click="$refs.operaMenuWindow.open('编辑菜单', row)" v-permissions="['system:menu:update']">编辑</el-button>
            <el-button type="text" icon="el-icon-plus" @click="$refs.operaMenuWindow.open('新建子菜单', null, row)" v-permissions="['system:menu:create']">新建子菜单</el-button>
            <el-button v-if="!row.fixed" type="text" icon="el-icon-delete" @click="deleteById(row.id)" v-permissions="['system:menu:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <!-- 新建/修改 -->
    <OperaMenuWindow ref="operaMenuWindow" @success="handlePageChange(tableData.pagination.pageIndex)"/>
  </TableLayout>
</template>

<script>
import TableLayout from '@/layouts/TableLayout'
import BaseTable from '@/components/base/BaseTable'
import OperaMenuWindow from '@/components/system/menu/OperaMenuWindow'
import { fetchTree, updateStatus, sort } from '@/api/system/menu'
export default {
  name: 'SystemMenu',
  extends: BaseTable,
  components: { OperaMenuWindow, TableLayout },
  data () {
    return {
      // 是否正在处理中
      isWorking: {
        sort: false
      }
    }
  },
  methods: {
    // 页码变更处理
    handlePageChange () {
      this.isWorking.search = true
      fetchTree()
        .then(records => {
          this.tableData.list = records
        })
        .catch(e => {
          this.$tip.error(e.message)
        })
        .finally(() => {
          this.isWorking.search = false
        })
    },
    // 排序
    sort (direction) {
      if (this.isWorking.sort) {
        return
      }
      if (this.tableData.selectedRows.length === 0) {
        this.$tip.warning('请选择一条数据')
        return
      }
      if (this.tableData.selectedRows.length > 1) {
        this.$tip.warning('排序时仅允许选择一条数据')
        return
      }
      const menuId = this.tableData.selectedRows[0].id
      // 找到菜单范围
      let menuPool
      for (const rootMenu of this.tableData.list) {
        const parent = this.__findParent(menuId, rootMenu)
        if (parent != null) {
          menuPool = parent.children
        }
      }
      menuPool = menuPool || this.tableData.list
      const menuIndex = menuPool.findIndex(menu => menu.id === menuId)
      // 上移校验
      if (direction === 'top' && menuIndex === 0) {
        this.$tip.warning('菜单已到顶部')
        return
      }
      // 下移校验
      if (direction === 'bottom' && menuIndex === menuPool.length - 1) {
        this.$tip.warning('菜单已到底部')
        return
      }
      this.isWorking.sort = true
      sort({
        id: this.tableData.selectedRows[0].id,
        direction
      })
        .then(() => {
          if (direction === 'top') {
            menuPool.splice(menuIndex, 0, menuPool.splice(menuIndex - 1, 1)[0])
          } else {
            menuPool.splice(menuIndex, 0, menuPool.splice(menuIndex + 1, 1)[0])
          }
        })
        .catch(e => {
          this.$tip.error(e.message)
        })
        .finally(() => {
          this.isWorking.sort = false
        })
    },
    // 启用/禁用菜单
    switchDisabled (row) {
      if (!row.disabled) {
        this.__updateMenuStatus(row)
        return
      }
      this.$dialog.confirm(`确认禁用 ${row.name} 菜单吗？`, '提示', {
        confirmButtonText: '确认禁用',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.__updateMenuStatus(row)
      }).catch(() => {
        row.disabled = !row.disabled
      })
    },
    // 批量删除
    deleteByIdInBatch () {
      if (this.tableData.selectedRows.length === 0) {
        this.$tip.warning('请至少选择一条数据')
        return
      }
      const containChildrenRows = []
      for (const row of this.tableData.selectedRows) {
        if (row.children != null && row.children.length > 0) {
          containChildrenRows.push(row.name)
        }
      }
      const message = containChildrenRows.length > 0 ? `本次将删除 【${containChildrenRows.join('、')}】 及其子菜单数据，确认删除吗？` : `确认删除已选中的 ${this.tableData.selectedRows.length} 条数据吗?`
      this.$dialog.confirm(message, '提示', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.isWorking.delete = true
        this.api.deleteByIdInBatch(this.tableData.selectedRows.map(row => row.id).join(','))
          .then(() => {
            this.$tip.success('删除成功')
            // 删除当前页最后一条记录时查询上一页数据
            if (this.tableData.list.length - 1 === 0) {
              this.handlePageChange(this.tableData.pagination.pageIndex - 1 === 0 ? 1 : this.tableData.pagination.pageIndex - 1)
            } else {
              this.handlePageChange(this.tableData.pagination.pageIndex)
            }
          })
          .catch(e => {
            this.$tip.error(e.message)
          })
          .finally(() => {
            this.isWorking.delete = false
          })
      })
    },
    // 查询父节点
    __findParent (id, parent) {
      if (parent.children === 0) {
        return
      }
      for (const menu of parent.children) {
        if (menu.id === id) {
          return parent
        }
        if (menu.children.length > 0) {
          const m = this.__findParent(id, menu)
          if (m != null) {
            return m
          }
        }
      }
      return null
    },
    // 修改菜单状态
    __updateMenuStatus (row) {
      updateStatus({
        id: row.id,
        parentId: row.parentId,
        disabled: row.disabled
      })
        .then(() => {
          this.$tip.success('修改成功')
        })
        .catch(e => {
          row.disabled = !row.disabled
          this.$tip.error(e.message)
        })
    }
  },
  created () {
    this.config({
      module: '菜单',
      api: '/system/menu'
    })
    this.search()
  }
}
</script>

<style lang="scss" scoped>
@import "@/assets/style/variables.scss";
.menu-layout {
  /deep/ .table-content {
    margin-top: 0;
  }
}
// 图标列
.table-column-icon {
  // element-ui图标
  i {
    background-color: $primary-color;
    opacity: 0.72;
    font-size: 20px;
    color: #fff;
    padding: 4px;
    border-radius: 50%;
  }
  // 自定义图标
  [class^="yw-icon-"] {
    width: 20px;
    height: 20px;
    background-size: 16px;
    vertical-align: middle;
  }
}
</style>
