<template>
  <GlobalWindow
    :title="dictName + '数据管理'"
    width="78%"
    :visible.sync="visible"
    :with-footer="false"
  >
    <TableLayout :with-breadcrumb="false">
      <!-- 表格和分页 -->
      <template v-slot:table-wrap>
        <ul class="toolbar">
          <li><el-button type="primary" @click="$refs.operaDictDataWindow.open('新建字典数据', searchForm.dictId)" icon="el-icon-plus">新建</el-button></li>
          <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete">删除</el-button></li>
        </ul>
        <el-table
          v-loading="isWorking.search"
          :data="tableData.list"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="label" label="数据标签" min-width="100px"></el-table-column>
          <el-table-column prop="code" label="数据值" min-width="100px"></el-table-column>
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
              <el-button type="text" @click="$refs.operaDictDataWindow.open('编辑字典数据', dictId, row)" icon="el-icon-edit">编辑</el-button>
              <el-button type="text" @click="deleteById(row)" icon="el-icon-delete">删除</el-button>
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
      <OperaDictDataWindow ref="operaDictDataWindow" @success="handlePageChange(tableData.pagination.pageIndex)"/>
    </TableLayout>
  </GlobalWindow>
</template>

<script>
import BaseTable from '@/components/base/BaseTable'
import Pagination from '@/components/common/Pagination'
import GlobalWindow from '@/components/common/GlobalWindow'
import TableLayout from '@/layouts/TableLayout'
import OperaDictDataWindow from './OperaDictDataWindow'
export default {
  name: 'DictDataManagerWindow',
  extends: BaseTable,
  components: { OperaDictDataWindow, TableLayout, GlobalWindow, Pagination },
  data () {
    return {
      visible: false,
      searchForm: {
        // 字典ID
        dictId: null
      },
      // 字典名称
      dictName: ''
    }
  },
  methods: {
    // 打开数据管理
    open (dictId, dictName) {
      this.searchForm.dictId = dictId
      this.dictName = dictName
      this.visible = true
      this.search()
    }
  },
  created () {
    this.config({
      api: '/system/dictData',
      'field.main': 'label'
    })
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
