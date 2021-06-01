<template>
  <TableLayout :permissions="['system:department:query']">
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:department:create', 'system:department:delete']">
        <li><el-button type="primary" @click="$refs.operaDepartmentWindow.open('新建部门')" icon="el-icon-plus" v-permissions="['system:department:create']">新建</el-button></li>
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
        <el-table-column type="selection" fixed="left" width="55"></el-table-column>
        <el-table-column prop="name" label="部门名称" fixed="left" min-width="200px"></el-table-column>
        <el-table-column prop="code" label="部门编码" fixed="left" min-width="100px"></el-table-column>
        <el-table-column prop="userCount" label="部门人数" min-width="100px"></el-table-column>
        <el-table-column prop="phone" label="联系电话" min-width="100px"></el-table-column>
        <el-table-column prop="email" label="部门邮箱" min-width="180px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px"></el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="140px"></el-table-column>
        <el-table-column
          v-if="containPermissions(['system:department:update', 'system:department:create', 'system:department:delete', 'system:department:queryUsers'])"
          label="操作"
          min-width="310"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button type="text" @click="$refs.operaDepartmentWindow.open('编辑部门', row)" icon="el-icon-edit" v-permissions="['system:department:update']">编辑</el-button>
            <el-button type="text" @click="$refs.operaDepartmentWindow.open('新建下级部门', null, row)" icon="el-icon-edit" v-permissions="['system:department:create']">新建下级部门</el-button>
            <el-button type="text" @click="$refs.departmentUserWindow.open(row.id, row.name)" icon="el-icon-user-solid" v-permissions="['system:department:queryUsers']">查看人员</el-button>
            <el-button type="text" @click="deleteById(row)" icon="el-icon-delete" v-permissions="['system:department:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <!-- 新建/修改 -->
    <OperaDepartmentWindow ref="operaDepartmentWindow" @success="handlePageChange(tableData.pagination.pageIndex)"/>
    <!-- 查看人员 -->
    <DepartmentUserWindow ref="departmentUserWindow"/>
  </TableLayout>
</template>

<script>
import TableLayout from '@/layouts/TableLayout'
import { fetchTree } from '@/api/system/department'
import BaseTable from '@/components/base/BaseTable'
import OperaDepartmentWindow from '@/components/system/department/OperaDepartmentWindow'
import DepartmentUserWindow from '@/components/system/department/DepartmentUserWindow'
export default {
  name: 'SystemDepartment',
  extends: BaseTable,
  components: { DepartmentUserWindow, OperaDepartmentWindow, TableLayout },
  data () {
    return {
      // 搜索
      searchForm: {
        name: ''
      }
    }
  },
  methods: {
    // 查询数据
    handlePageChange () {
      this.tableData.list.splice(0, this.tableData.list.length)
      this.isWorking.search = true
      fetchTree()
        .then(records => {
          this.tableData.list = records
        })
        .catch(e => {
          this.$tip.apiFailed(e)
        })
        .finally(() => {
          this.isWorking.search = false
        })
    }
  },
  created () {
    this.config({
      module: '部门',
      api: '/system/department'
    })
    this.search()
  }
}
</script>
<style lang="scss" scoped>
.table-layout {
  /deep/ .table-content {
    margin-top: 0;
  }
}
</style>
