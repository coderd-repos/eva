<template>
  <TableLayout :permissions="['system:role:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="100px" inline>
      <el-form-item label="角色编码" prop="code">
        <el-input v-model="searchForm.code" v-trim placeholder="请输入角色编码" @keypress.enter.native="search"/>
      </el-form-item>
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="searchForm.name" v-trim placeholder="请输入角色名称" @keypress.enter.native="search"/>
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
        :default-sort = "{prop: 'createTime', order: 'descending'}"
        stripe
        @selection-change="handleSelectionChange"
        @sort-change="handleSortChange"
      >
        <el-table-column type="selection" fixed="left" width="55"></el-table-column>
        <el-table-column prop="code" label="角色编码" fixed="left" min-width="100px"></el-table-column>
        <el-table-column prop="name" label="角色名称" fixed="left" min-width="100px"></el-table-column>
        <el-table-column prop="remark" label="角色备注" min-width="120px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px" sortable="custom" sort-by="role.CREATE_TIME"></el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="140px"></el-table-column>
        <el-table-column
          v-if="containPermissions(['system:role:update', 'system:role:createRolePermission', 'system:role:createRoleMenu', 'system:role:delete'])"
          label="操作"
          min-width="270"
          fixed="right"
        >
          <template v-if="isAdmin || (row.code !== adminCode && userInfo.roles.findIndex(code => code === row.code) === -1)" slot-scope="{row}">
            <el-button type="text" @click="$refs.operaRoleWindow.open('编辑角色', row)" icon="el-icon-edit" v-permissions="['system:role:update']">编辑</el-button>
            <el-button type="text" @click="$refs.permissionConfigWindow.open(row)" v-permissions="['system:role:createRolePermission']">配置权限</el-button>
            <el-button type="text" @click="$refs.menuConfigWindow.open(row)" icon="el-icon-menu" v-permissions="['system:role:createRoleMenu']">授权菜单</el-button>
            <el-button v-if="!row.fixed" type="text" @click="deleteById(row)" icon="el-icon-delete" v-permissions="['system:role:delete']">删除</el-button>
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
    <OperaRoleWindow ref="operaRoleWindow" @success="handlePageChange(tableData.pagination.pageIndex)"/>
    <!-- 配置权限 -->
    <PermissionConfigWindow ref="permissionConfigWindow" @success="handlePageChange(tableData.pagination.pageIndex)"/>
    <!-- 授权菜单 -->
    <MenuConfigWindow ref="menuConfigWindow" @success="handlePageChange(tableData.pagination.pageIndex)"/>
  </TableLayout>
</template>

<script>
import Pagination from '@/components/common/Pagination'
import TableLayout from '@/layouts/TableLayout'
import BaseTable from '@/components/base/BaseTable'
import OperaRoleWindow from '@/components/system/role/OperaRoleWindow'
import PermissionConfigWindow from '@/components/system/role/PermissionConfigWindow'
import MenuConfigWindow from '@/components/system/role/MenuConfigWindow'
export default {
  name: 'SystemRole',
  extends: BaseTable,
  components: { MenuConfigWindow, PermissionConfigWindow, OperaRoleWindow, TableLayout, Pagination },
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
      module: '角色',
      api: '/system/role',
      sorts: [{
        property: 'role.CREATE_TIME',
        direction: 'DESC'
      }]
    })
    this.search()
  }
}
</script>
