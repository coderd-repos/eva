<template>
  <TableLayout :permissions="['system:permission:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="100px" inline>
      <el-form-item label="权限编码" prop="code">
        <el-input v-model="searchForm.code" v-trim placeholder="请输入权限编码" @keypress.enter.native="search"/>
      </el-form-item>
      <el-form-item label="权限名称" prop="name">
        <el-input v-model="searchForm.name" v-trim placeholder="请输入权限名称" @keypress.enter.native="search"/>
      </el-form-item>
      <section>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </section>
    </el-form>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:permission:create', 'system:permission:delete']">
        <li><el-button type="primary" @click="$refs.operaPermissionWindow.open('新建系统权限')" icon="el-icon-plus" v-permissions="['system:permission:create']">新建</el-button></li>
        <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete" v-permissions="['system:permission:delete']">删除</el-button></li>
      </ul>
      <el-table
        v-loading="isWorking.search"
        :data="tableData.list"
        :default-sort = "{prop: 'createTime', order: 'descending'}"
        stripe
        @selection-change="handleSelectionChange"
        @sort-change="handleSortChange"
      >
        <el-table-column type="selection" fixed="left" width="55"></el-table-column>
        <el-table-column prop="code" label="权限编码" fixed="left" min-width="200px"></el-table-column>
        <el-table-column prop="name" label="权限名称" fixed="left" min-width="120px"></el-table-column>
        <el-table-column prop="remark" label="权限备注" min-width="120px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px" sortable="custom" sort-by="perm.CREATE_TIME"></el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="140px"></el-table-column>
        <el-table-column
          v-if="containPermissions(['system:permission:update', 'system:permission:delete'])"
          label="操作"
          min-width="130"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button v-if="!row.fixed" type="text" @click="$refs.operaPermissionWindow.open('编辑系统权限', row)" icon="el-icon-edit" v-permissions="['system:permission:update']">编辑</el-button>
            <el-button v-if="!row.fixed" type="text" @click="deleteById(row)" icon="el-icon-delete" v-permissions="['system:permission:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
        :pagination="tableData.pagination"
      ></pagination>
    </template>
    <!-- 新建/修改 -->
    <OperaPermissionWindow ref="operaPermissionWindow" @success="handlePageChange"/>
  </TableLayout>
</template>

<script>
import Pagination from '@/components/common/Pagination'
import TableLayout from '@/layouts/TableLayout'
import BaseTable from '@/components/base/BaseTable'
import OperaPermissionWindow from '@/components/system/permission/OperaPermissionWindow'
export default {
  name: 'SystemPermission',
  extends: BaseTable,
  components: { OperaPermissionWindow, TableLayout, Pagination },
  data () {
    return {
      // 搜索
      searchForm: {
        code: '',
        name: '',
        remark: ''
      }
    }
  },
  created () {
    this.config({
      module: '权限',
      api: '/system/permission',
      sorts: [{
        property: 'perm.CREATE_TIME',
        direction: 'DESC'
      }]
    })
    this.search()
  }
}
</script>
