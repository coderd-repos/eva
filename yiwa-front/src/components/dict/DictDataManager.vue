<template>
  <GlobalWindow
    :title="dictName + '数据管理'"
    width="78%"
    :visible.sync="visible.dataManager"
    :with-footer="false"
  >
    <TableLayout :with-breadcrumb="false">
      <!-- 表格和分页 -->
      <template v-slot:table-wrap>
        <ul class="toolbar">
          <li><el-button type="primary" @click="create" icon="el-icon-plus">新建</el-button></li>
          <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete">删除</el-button></li>
        </ul>
        <el-table
          v-loading="isWorking.search"
          :data="tableData.list"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="code" label="数据值" min-width="100px"></el-table-column>
          <el-table-column prop="label" label="数据标签" min-width="100px"></el-table-column>
          <el-table-column prop="disabled" label="状态" min-width="100px">
            <template slot-scope="{row}">{{row.disabled | disabledText}}</template>
          </el-table-column>
          <el-table-column prop="createUser" label="创建人" min-width="100px">
            <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
          </el-table-column>
          <el-table-column prop="updateUser" label="更新人" min-width="100px">
            <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" min-width="100px"></el-table-column>
          <el-table-column prop="updateTime" label="更新时间" min-width="100px"></el-table-column>
          <el-table-column
            label="操作"
            min-width="120"
            fixed="right"
          >
            <template slot-scope="{row}">
              <el-button type="text" @click="edit(row)" icon="el-icon-edit">编辑</el-button>
              <el-button type="text" @click="deleteById(row.id)" icon="el-icon-delete">删除</el-button>
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
      <GlobalWindow
        :title="operaTableData.title"
        :visible.sync="visible.operaTable"
        :confirm-working="isWorking.create"
        @confirm="confirm"
      >
        <el-form :model="operaTableData.form" ref="operaTableDataForm" :rules="operaTableData.rules">
          <el-form-item label="数据标签" prop="label" required>
            <el-input v-model="operaTableData.form.label" placeholder="请输入数据标签"></el-input>
          </el-form-item>
          <el-form-item label="数据值" prop="code" required>
            <el-input v-model="operaTableData.form.code" placeholder="请输入数据值"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="code" required>
            <el-switch v-model="operaTableData.form.disabled" :active-value="false" :inactive-value="true"/>
          </el-form-item>
        </el-form>
      </GlobalWindow>
    </TableLayout>
  </GlobalWindow>
</template>

<script>
import Pagination from '../../components/common/Pagination'
import GlobalWindow from '../../components/common/GlobalWindow'
import TableLayout from '../../layouts/TableLayout'
import { fetchList, create, updateById, deleteById, deleteByIdInBatch } from '../../api/system/systemDictData'
import BaseTable from '../../views/BaseTable'
export default {
  name: 'DictDataManager',
  extends: BaseTable,
  components: { TableLayout, GlobalWindow, Pagination },
  data () {
    return {
      dictId: null,
      dictName: '',
      visible: {
        dataManager: false
      },
      // 新增/修改
      operaTableData: {
        title: '添加系统权限',
        // 表单数据
        form: {
          id: null,
          dictId: null,
          code: '',
          label: '',
          disabled: false
        },
        // 验证规则
        rules: {
          dictId: [
            { required: true, message: '请输入所属字典' }
          ],
          code: [
            { required: true, message: '请输入数据值' }
          ],
          label: [
            { required: true, message: '请输入数据标签' }
          ]
        }
      }
    }
  },
  methods: {
    // 打开数据管理
    open (dictId, dictName) {
      this.dictId = dictId
      this.dictName = dictName
      this.visible.dataManager = true
      this.search()
    },
    // 确认创建/修改
    confirm () {
      if (this.operaTableData.form.id == null || this.operaTableData.form.id === '') {
        this.confirmCreate()
        return
      }
      this.confirmEdit()
    },
    // 添加
    create () {
      this.visible.operaTable = true
      this.operaTableData.title = '添加字典数据'
      this.$nextTick(() => {
        this.$refs.operaTableDataForm.resetFields()
        this.operaTableData.form.id = null
        this.operaTableData.form.dictId = this.dictId
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
      this.operaTableData.title = '修改字典数据'
      this.visible.operaTable = true
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
      this.$confirm('确认删除此字典数据吗?', '提示', {
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
        model: {
          dictId: this.dictId
        }
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
  }
}
</script>

<style scoped lang="scss">
/deep/ .window__body {
  .table-content {
    padding: 0;
    .table-wrap {
      padding-top: 0;
    }
  }
}
</style>
