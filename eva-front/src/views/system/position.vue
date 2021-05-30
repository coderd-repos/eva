<template>
  <TableLayout :permissions="['system:position:query']">
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:position:create', 'system:position:delete']">
        <li><el-button type="primary" @click="$refs.operaPositionWindow.open('新建岗位')" icon="el-icon-plus" v-permissions="['system:position:create']">新建</el-button></li>
        <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete" v-permissions="['system:position:delete']">删除</el-button></li>
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
        <el-table-column type="selection" width="55" fixed="left"></el-table-column>
        <el-table-column prop="name" label="岗位名称" fixed="left" min-width="200px"></el-table-column>
        <el-table-column prop="code" label="岗位编码" fixed="left" min-width="100px"></el-table-column>
        <el-table-column prop="userCount" label="岗位人数" min-width="100px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px"></el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="140px"></el-table-column>
        <el-table-column
          v-if="containPermissions(['system:position:update', 'system:position:query', 'system:position:delete'])"
          label="操作"
          min-width="310"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button type="text" @click="$refs.operaPositionWindow.open('编辑岗位', row)" icon="el-icon-edit" v-permissions="['system:position:update']">编辑</el-button>
            <el-button type="text" @click="$refs.operaPositionWindow.open('新增下级岗位', null, row)" icon="el-icon-edit" v-permissions="['system:position:update']">新增下级岗位</el-button>
            <el-button type="text" @click="$refs.positionUserWindow.open(row.id, row.name)" icon="el-icon-user-solid" v-permissions="['system:position:queryUsers']">查看人员</el-button>
            <el-button type="text" @click="deleteById(row)" icon="el-icon-delete" v-permissions="['system:position:delete']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
    <!-- 新建/修改 -->
    <OperaPositionWindow ref="operaPositionWindow" @success="handlePageChange"/>
    <!-- 人员管理 -->
    <PositionUserWindow ref="positionUserWindow"/>
  </TableLayout>
</template>

<script>
import TableLayout from '@/layouts/TableLayout'
import BaseTable from '@/components/base/BaseTable'
import OperaPositionWindow from '@/components/system/position/OperaPositionWindow'
import PositionUserWindow from '@/components/system/position/PositionUserWindow'
import { fetchTree } from '@/api/system/position'
export default {
  name: 'SystemPosition',
  extends: BaseTable,
  components: { PositionUserWindow, OperaPositionWindow, TableLayout },
  methods: {
    // 查询数据
    handlePageChange () {
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
      module: '岗位',
      api: '/system/position'
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
