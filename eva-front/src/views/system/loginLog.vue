<template>
  <TableLayout :permissions="['system:loginLog:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="100px" inline>
      <el-form-item label="登录用户名" prop="loginUsername">
        <el-input v-model="searchForm.loginUsername" placeholder="请输入登录用户名" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="登录IP" prop="ip">
        <el-input v-model="searchForm.ip" placeholder="请输入登录IP" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="服务器IP" prop="serverIp">
        <el-input v-model="searchForm.serverIp" placeholder="请输入服务器IP" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="是否登录成功" prop="success">
        <el-select v-model="searchForm.success" placeholder="请选择是否登录状态" clearable @change="search">
          <el-option value="true" label="登录成功"/>
          <el-option value="false" label="登录失败"/>
        </el-select>
      </el-form-item>
      <el-form-item label="登录时间" prop="loginTime">
        <el-date-picker
          v-model="searchDateRange"
          type="datetimerange"
          range-separator="至"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          @change="handleSearchTimeChange"
        ></el-date-picker>
      </el-form-item>
      <section>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button type="primary" :loading="isWorking.export" @click="exportExcel">导出</el-button>
        <el-button @click="reset">重置</el-button>
      </section>
    </el-form>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <el-table
        v-loading="isWorking.search"
        :data="tableData.list"
        stripe
        :default-sort="{prop: 'loginTime', order: 'descending'}"
        @sort-change="handleSortChange"
      >
        <el-table-column prop="loginUsername" label="登录用户名" min-width="100px"></el-table-column>
        <el-table-column prop="ip" label="登录IP" min-width="120px"></el-table-column>
        <el-table-column prop="location" label="登录地址" min-width="160px"></el-table-column>
        <el-table-column prop="clientInfo" label="客户端" min-width="160px"></el-table-column>
        <el-table-column prop="osInfo" label="操作系统" min-width="100px"></el-table-column>
        <el-table-column prop="platform" label="登录平台" min-width="100px"></el-table-column>
        <el-table-column prop="loginTime" label="登录时间" min-width="160px" sortable="custom" sort-by="LOGIN_TIME"></el-table-column>
        <el-table-column prop="systemVersion" label="系统版本" min-width="100px"></el-table-column>
        <el-table-column prop="serverIp" label="服务器IP" min-width="120px"></el-table-column>
        <el-table-column prop="success" label="状态" min-width="100px">
          <template slot-scope="{row}">
            {{row.success | statusText}}
          </template>
        </el-table-column>
        <el-table-column prop="reason" label="失败原因" min-width="160px"></el-table-column>
      </el-table>
      <pagination
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
          :pagination="tableData.pagination"
      ></pagination>
    </template>
  </TableLayout>
</template>

<script>
import BaseTable from '@/components/base/BaseTable'
import TableLayout from '@/layouts/TableLayout'
import Pagination from '@/components/common/Pagination'

export default {
  name: 'SystemLoginLog',
  extends: BaseTable,
  components: { TableLayout, Pagination },
  data () {
    return {
      // 搜索时间范围
      searchDateRange: [],
      // 搜索
      searchForm: {
        loginUsername: '',
        ip: '',
        serverIp: '',
        success: '',
        startTime: null,
        endTime: null
      }
    }
  },
  filters: {
    // 登录状态
    statusText (value) {
      if (value) {
        return '登录成功'
      }
      return '登录失败'
    }
  },
  methods: {
    // 时间搜索范围变化
    handleSearchTimeChange (value) {
      this.searchForm.startTime = null
      this.searchForm.endTime = null
      if (value != null) {
        this.searchForm.startTime = value[0]
        this.searchForm.endTime = value[1]
      }
      this.search()
    }
  },
  created () {
    this.config({
      module: '登录日志',
      api: '/system/loginLog',
      'field.id': 'id',
      'field.main': 'id',
      sorts: [{
        property: 'LOGIN_TIME',
        direction: 'DESC'
      }]
    })
    this.search()
  }
}
</script>
