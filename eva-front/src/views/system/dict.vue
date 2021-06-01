<template>
  <TableLayout :permissions="['system:dict:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="100px" inline>
      <el-form-item label="字典编码" prop="code">
        <el-input v-model="searchForm.code" v-trim placeholder="请输入字典编码" @keypress.enter.native="search"/>
      </el-form-item>
      <el-form-item label="字典名称" prop="name">
        <el-input v-model="searchForm.name" v-trim placeholder="请输入字典名称" @keypress.enter.native="search"/>
      </el-form-item>
      <section>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </section>
    </el-form>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:dict:create', 'system:dict:delete']">
        <li><el-button type="primary" @click="$refs.operaDictWindow.open('新建字典')" icon="el-icon-plus" v-permissions="['system:dict:create']">新建</el-button></li>
        <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete" v-permissions="['system:dict:delete']">删除</el-button></li>
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
        <el-table-column prop="code" label="字典编码" fixed="left" min-width="100px"></el-table-column>
        <el-table-column prop="name" label="字典名称" fixed="left" min-width="100px"></el-table-column>
        <el-table-column prop="remark" label="备注" min-width="100px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px" sortable="custom" sort-by="dict.CREATE_TIME"></el-table-column>
        <el-table-column prop="updateUser" label="更新人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" min-width="140px"></el-table-column>
        <el-table-column
          v-if="containPermissions(['system:dict:update', 'system:dict:delete'])"
          label="操作"
          min-width="210"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button type="text" @click="$refs.operaDictWindow.open('编辑字典', row)" icon="el-icon-edit" v-permissions="['system:dict:update']">编辑</el-button>
            <el-button type="text" @click="$refs.dictDataManagerWindow.open(row.id, row.name)" icon="el-icon-edit" v-permissions="['system:dict:update']">数据管理</el-button>
            <el-button type="text" @click="deleteById(row)" icon="el-icon-delete" v-permissions="['system:dict:delete']">删除</el-button>
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
    <OperaDictWindow ref="operaDictWindow" @success="handlePageChange(tableData.pagination.pageIndex)"/>
    <!-- 数据管理 -->
    <DictDataManagerWindow ref="dictDataManagerWindow"/>
  </TableLayout>
</template>

<script>
import Pagination from '@/components/common/Pagination'
import TableLayout from '@/layouts/TableLayout'
import BaseTable from '@/components/base/BaseTable'
import OperaDictWindow from '@/components/system/dict/OperaDictWindow'
import DictDataManagerWindow from '@/components/system/dict/DictDataManagerWindow'
export default {
  name: 'SystemDict',
  extends: BaseTable,
  components: { DictDataManagerWindow, OperaDictWindow, TableLayout, Pagination },
  data () {
    return {
      // 搜索
      searchForm: {
        code: '',
        name: ''
      }
    }
  },
  created () {
    this.config({
      module: '字典',
      api: '/system/dict',
      sorts: [{
        property: 'dict.CREATE_TIME',
        direction: 'DESC'
      }]
    })
    this.search()
  }
}
</script>
