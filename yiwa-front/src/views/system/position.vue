<template>
  <TableLayout v-permissions="['system:position:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="100px" inline>
      <el-form-item label="岗位名称" prop="name">
        <el-input v-model="searchForm.name" placeholder="请输入岗位名称" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <section>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </section>
    </el-form>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:position:create', 'system:position:delete']">
        <li><el-button type="primary" @click="$refs.operaPositionWindow.open('新建岗位')" icon="el-icon-plus" v-permissions="['system:position:create']">新建</el-button></li>
        <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete" v-permissions="['system:position:delete']">删除</el-button></li>
      </ul>
      <el-table
        v-loading="isWorking.search"
        :data="tableData.list"
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="name" label="岗位名称" min-width="100px"></el-table-column>
        <el-table-column prop="userCount" label="岗位人数" min-width="100px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="100px"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="100px"></el-table-column>
        <el-table-column
          v-if="containPermissions(['system:position:update', 'system:position:query', 'system:position:delete'])"
          label="操作"
          min-width="120"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button type="text" @click="$refs.operaPositionWindow.open('编辑岗位', row)" icon="el-icon-edit" v-permissions="['system:position:update']">编辑</el-button>
            <el-button type="text" @click="$refs.userManagerWindow.open(row.id, row.name)" icon="el-icon-user-solid" v-permissions="['system:position:query']">人员管理</el-button>
            <el-button type="text" @click="deleteById(row.id)" icon="el-icon-delete" v-permissions="['system:position:delete']">删除</el-button>
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
    <OperaPositionWindow ref="operaPositionWindow" @create-success="search" @edit-success="handlePageChange(tableData.pagination.pageIndex)"/>
    <!-- 人员管理 -->
    <UserManagerWindow ref="userManagerWindow"/>
  </TableLayout>
</template>

<script>
import Pagination from '../../components/common/Pagination'
import TableLayout from '../../layouts/TableLayout'
import { fetchList, deleteById, deleteByIdInBatch } from '../../api/system/position'
import BaseTable from '../BaseTable'
import UserManagerWindow from '../../components/position/UserManagerWindow'
import OperaPositionWindow from '../../components/position/OperaPositionWindow'
export default {
  name: 'SystemPosition',
  extends: BaseTable,
  components: { OperaPositionWindow, UserManagerWindow, TableLayout, Pagination },
  data () {
    return {
      // 搜索
      searchForm: {
        name: ''
      }
    }
  },
  methods: {
    // 删除
    deleteById (id) {
      this.$confirm('确认删除此岗位吗?', '提示', {
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
