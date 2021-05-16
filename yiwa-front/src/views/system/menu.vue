<template>
  <TableLayout class="menu-layout" v-permissions="['system:menu:query']">
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:menu:create', 'system:menu:delete', 'system:menu:sort']">
        <li><el-button type="primary" @click="create(null)" icon="el-icon-plus" v-permissions="['system:menu:create']">新建</el-button></li>
        <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete" v-permissions="['system:menu:delete']">删除</el-button></li>
        <li><el-button @click="sort('top')" :loading="isWorking.sort" icon="el-icon-sort-up" v-permissions="['system:menu:sort']">上移</el-button></li>
        <li><el-button @click="sort('bottom')" :loading="isWorking.sort" icon="el-icon-sort-down" v-permissions="['system:menu:sort']">下移</el-button></li>
      </ul>
      <el-table
        v-loading="isWorking.search"
        :data="tableData.list"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        row-key="id"
        stripe
        default-expand-all
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="name" label="菜单名称" min-width="160px"></el-table-column>
        <el-table-column prop="icon" label="图标" min-width="80px" class-name="table-column-icon">
          <template slot-scope="{row}">
            <i v-if="row.icon != null" :class="{[row.icon]: true}"></i>
            <template v-else>无</template>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="访问路径" min-width="140px"></el-table-column>
        <el-table-column prop="remark" label="备注" min-width="120px"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="140px"></el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
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
            <el-button type="text" icon="el-icon-edit" @click="edit(row)" v-permissions="['system:menu:update']">编辑</el-button>
            <el-button type="text" icon="el-icon-plus" @click="create(row)" v-permissions="['system:menu:create']">添加子菜单</el-button>
            <el-button type="text" icon="el-icon-delete" @click="deleteById(row)" v-permissions="['system:menu:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <!-- 添加/修改 -->
    <GlobalWindow
      class="handle-table-dialog"
      :title="handleTableData.title"
      :visible.sync="visible.operaTable"
      :confirm-working="isWorking.operaTable"
      @confirm="confirm"
    >
      <p class="tip" v-if="handleTableData.form.parent != null && handleTableData.form.id == null">为 <em>{{handleTableData.form.parent.name}}</em> 添加子菜单</p>
      <el-form :model="handleTableData.form" ref="handleTableDataForm" :rules="handleTableData.rules">
        <el-form-item label="菜单名称" prop="name" required>
          <el-input v-model="handleTableData.form.name"></el-input>
        </el-form-item>
        <el-form-item label="访问路径" prop="path">
          <el-input v-model="handleTableData.form.path"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon" class="form-item-icon">
          <el-radio-group v-model="handleTableData.form.icon">
            <el-radio :label="icon" border v-for="icon in icons" :key="icon">
              <i :class="{[icon]: true}"></i>
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="handleTableData.form.remark"></el-input>
        </el-form-item>
      </el-form>
    </GlobalWindow>
  </TableLayout>
</template>

<script>
import icons from '../../utils/icons'
import GlobalWindow from '../../components/common/GlobalWindow'
import TableLayout from '../../layouts/TableLayout'
import { fetchList, create, updateById, deleteById, deleteByIdInBatch, sort } from '../../api/system/systemMenu'
import BaseTable from '../BaseTable'
export default {
  name: 'SystemMenu',
  extends: BaseTable,
  components: { TableLayout, GlobalWindow },
  data () {
    return {
      icons,
      // 是否展示
      visible: {
        handleChild: false
      },
      // 是否正在处理中
      isWorking: {
        handleChild: false,
        sort: false
      },
      // 新增/修改
      handleTableData: {
        title: '添加系统菜单',
        // 表单数据
        form: {
          id: null,
          parent: null,
          name: '',
          path: '',
          icon: '',
          remark: ''
        },
        // 验证规则
        rules: {
          name: [
            { required: true, message: '请输入菜单名称' }
          ]
        }
      }
    }
  },
  methods: {
    // 确认创建/修改
    confirm () {
      if (this.handleTableData.form.id == null) {
        this.confirmCreate()
        return
      }
      this.confirmEdit()
    },
    // 添加
    create (parent) {
      this.handleTableData.title = parent == null ? '添加菜单' : '添加子菜单'
      this.handleTableData.form.parent = parent
      this.visible.operaTable = true
      this.$nextTick(() => {
        this.$refs.handleTableDataForm && this.$refs.handleTableDataForm.resetFields()
      })
    },
    // 确定添加
    confirmCreate () {
      this.$refs.handleTableDataForm.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用添加接口
        this.isWorking.operaTable = true
        create({
          ...this.handleTableData.form,
          parentId: this.handleTableData.form.parent == null ? null : this.handleTableData.form.parent.id
        })
          .then(() => {
            this.visible.operaTable = false
            this.handlePageChange()
            this.$message.success('创建成功')
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking.operaTable = false
          })
      })
    },
    // 编辑
    edit (row) {
      this.handleTableData.title = '修改菜单'
      this.visible.operaTable = true
      this.$nextTick(() => {
        Object.assign(this.handleTableData.form, row)
      })
    },
    // 确认修改
    confirmEdit () {
      this.$refs.handleTableDataForm.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用添加接口
        this.isWorking.operaTable = true
        updateById(this.handleTableData.form)
          .then(() => {
            this.visible.operaTable = false
            this.search()
            this.$message.success('修改成功')
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking.operaTable = false
          })
      })
    },
    // 删除
    deleteById (row) {
      this.$confirm(`确认删除 ${row.name} 菜单吗?`, '提示', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.isWorking.delete = true
        deleteById(row.id)
          .then(() => {
            this.$message.success('删除成功')
            this.handlePageChange()
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
            this.handlePageChange()
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
    handlePageChange () {
      // 调用查询接口
      this.tableData.list.splice(0, this.tableData.list.length)
      this.isWorking.search = true
      fetchList()
        .then(records => {
          this.tableData.list = records
        })
        .catch(e => {
          this.$message.error(e.message)
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
        this.$message.warning('请选择一条数据')
        return
      }
      if (this.tableData.selectedRows.length > 1) {
        this.$message.warning('排序时仅允许选择一条数据')
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
        this.$message.warning('菜单已到顶部')
        return
      }
      // 下移校验
      if (direction === 'bottom' && menuIndex === menuPool.length - 1) {
        this.$message.warning('菜单已到底部')
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
          this.$message.error(e.message)
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
      this.$confirm(`确认禁用 ${row.name} 菜单吗？`, '提示', {
        confirmButtonText: '确认禁用',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.__updateMenuStatus(row)
      }).catch(() => {
        row.disabled = !row.disabled
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
      updateById({
        id: row.id,
        disabled: row.disabled
      })
        .then(() => {
          this.$message.success('修改成功')
        })
        .catch(e => {
          row.disabled = !row.disabled
          this.$message.error(e.message)
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
.menu-layout {
  /deep/ .table-content {
    margin-top: 0;
  }
}
// 图标列
.table-column-icon {
  i {
    font-size: 20px;
  }
}
// 新建/修改
.handle-table-dialog {
  .tip {
    margin-bottom: 12px;
    em {
      font-style: normal;
      color: $primary-color;
      font-weight: bold;
    }
  }
  // 图标
  /deep/ .form-item-icon {
    .el-form-item__content {
      height: 193px;
      overflow-y: auto;
    }
    .el-radio-group {
      .el-radio {
        margin-right: 0;
      }
      .el-radio__input {
        display: none;
      }
      .el-radio__label {
        padding-left: 0;
        i {
          font-size: 30px;
        }
        &:hover i{
          color: $primary-color;
        }
      }
      .el-radio--small.is-bordered {
        height: auto;
        padding: 8px;
        margin-left: 0;
      }
    }
  }
}
</style>
