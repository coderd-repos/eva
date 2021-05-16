<template>
  <TableLayout v-permissions="['system:department:query']">
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:department:create', 'system:department:delete']">
        <li><el-button type="primary" @click="create" icon="el-icon-plus" v-permissions="['system:department:create']">新建</el-button></li>
        <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete" v-permissions="['system:department:delete']">删除</el-button></li>
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
        <el-table-column prop="name" label="部门名称" min-width="100px"></el-table-column>
        <el-table-column prop="phone" label="联系电话" min-width="100px"></el-table-column>
        <el-table-column prop="email" label="部门邮箱" min-width="100px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="100px"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="100px"></el-table-column>
        <el-table-column
          v-if="containPermissions(['system:department:update', 'system:department:create', 'system:department:delete'])"
          label="操作"
          min-width="120"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button type="text" @click="edit(row)" icon="el-icon-edit" v-permissions="['system:department:update']">编辑</el-button>
            <el-button type="text" @click="create(row)" icon="el-icon-edit" v-permissions="['system:department:create']">添加子部门</el-button>
            <el-button v-if="row.parentId != null" type="text" @click="deleteById(row.id)" icon="el-icon-delete" v-permissions="['system:department:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <!-- 添加/修改 -->
    <GlobalWindow
      :title="operaTableData.title"
      :visible.sync="visible.operaTable"
      :confirm-working="isWorking.create"
      @confirm="confirm"
    >
      <el-form :model="operaTableData.form" ref="operaTableDataForm" :rules="operaTableData.rules">
        <el-form-item v-if="operaTableData.form.id == null || operaTableData.form.parentId != null" label="上级部门" prop="parentId" required>
          <TreeSelect v-model="operaTableData.form.parentId" :data="operaTableData.parentDepartmentList"/>
        </el-form-item>
        <el-form-item label="部门名称" prop="name" required>
          <el-input v-model="operaTableData.form.name" placeholder="请输入部门名称"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="operaTableData.form.phone" placeholder="请输入联系电话"></el-input>
        </el-form-item>
        <el-form-item label="部门邮箱" prop="email">
          <el-input v-model="operaTableData.form.email" placeholder="请输入部门邮箱"></el-input>
        </el-form-item>
      </el-form>
    </GlobalWindow>
  </TableLayout>
</template>

<script>
import GlobalWindow from '../../components/common/GlobalWindow'
import TableLayout from '../../layouts/TableLayout'
import { fetchList, create, updateById, deleteById, deleteByIdInBatch } from '../../api/system/systemDepartment'
import BaseTable from '../BaseTable'
import TreeSelect from '../../components/common/TreeSelect'
export default {
  name: 'SystemDepartment',
  extends: BaseTable,
  components: { TreeSelect, TableLayout, GlobalWindow },
  data () {
    return {
      // 搜索
      searchForm: {
        name: ''
      },
      // 新增/修改
      operaTableData: {
        title: '添加系统权限',
        // 父部门数据
        parentDepartmentList: [],
        // 表单数据
        form: {
          id: null,
          parentId: null,
          name: '',
          phone: '',
          email: ''
        },
        // 验证规则
        rules: {
          name: [
            { required: true, message: '请输入部门名称' }
          ]
        }
      }
    }
  },
  methods: {
    // 确认创建/修改
    confirm () {
      if (this.operaTableData.form.id == null || this.operaTableData.form.id === '') {
        this.confirmCreate()
        return
      }
      this.confirmEdit()
    },
    // 添加
    create (row = {}) {
      this.visible.operaTable = true
      this.operaTableData.title = '添加部门'
      // 填充上级部门数据
      this.operaTableData.parentDepartmentList = []
      this.__fillParentDepartmentList(this.operaTableData.parentDepartmentList, this.tableData.list)
      this.$nextTick(() => {
        this.$refs.operaTableDataForm.resetFields()
        // 清空ID
        this.operaTableData.form.id = ''
        // 选中上级部门
        this.operaTableData.form.parentId = row.id || this.operaTableData.parentDepartmentList[0].id
      })
    },
    // 确定添加
    confirmCreate () {
      this.$refs.operaTableDataForm.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用添加接口
        this.isWorking.operaTable = true
        create(this.operaTableData.form)
          .then(() => {
            this.visible.operaTable = false
            this.handlePageChange(1)
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
      this.operaTableData.title = '修改'
      this.visible.operaTable = true
      // 填充上级部门数据
      this.operaTableData.parentDepartmentList = []
      this.__fillParentDepartmentList(this.operaTableData.parentDepartmentList, this.tableData.list, row.id)
      this.$nextTick(() => {
        for (const key in this.operaTableData.form) {
          this.operaTableData.form[key] = row[key]
        }
      })
    },
    // 确认修改
    confirmEdit () {
      this.$refs.operaTableDataForm.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用添加接口
        this.isWorking.operaTable = true
        updateById(this.operaTableData.form)
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
    deleteById (id) {
      this.$confirm('确认删除此吗?', '提示', {
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
    // 获取上级部门列表
    __fillParentDepartmentList (list, pool, excludeId) {
      for (const dept of pool) {
        if (dept.id === excludeId) {
          continue
        }
        const deptNode = {
          id: dept.id,
          label: dept.name,
          children: []
        }
        list.push(deptNode)
        if (dept.children != null && dept.children.length > 0) {
          this.__fillParentDepartmentList(deptNode.children, dept.children, excludeId)
        }
      }
    }
  },
  created () {
    this.search()
  }
}
</script>
