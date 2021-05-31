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
        <el-input v-model="searchForm.success" placeholder="请输入是否登录成功" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="登录时间" prop="loginTime">
        <el-date-picker v-model="searchForm.loginTime" value-format="yyyy-MM-dd" placeholder="请输入登录时间" @change="search"/>
      </el-form-item>
      <section>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </section>
    </el-form>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <el-table
          v-loading="isWorking.search"
          :data="tableData.list"
          stripe
      >
        <el-table-column prop="loginUsername" label="登录用户名" min-width="100px"></el-table-column>
        <el-table-column prop="ip" label="登录IP" min-width="100px"></el-table-column>
        <el-table-column prop="location" label="登录地址" min-width="100px"></el-table-column>
        <el-table-column prop="clientInfo" label="客户端" min-width="100px"></el-table-column>
        <el-table-column prop="osInfo" label="操作系统" min-width="100px"></el-table-column>
        <el-table-column prop="platform" label="登录平台" min-width="100px"></el-table-column>
        <el-table-column prop="systemVersion" label="系统版本" min-width="100px"></el-table-column>
        <el-table-column prop="serverIp" label="服务器IP" min-width="100px"></el-table-column>
        <el-table-column prop="success" label="是否登录成功" min-width="100px"></el-table-column>
        <el-table-column prop="reason" label="失败原因" min-width="100px"></el-table-column>
        <el-table-column prop="loginTime" label="登录时间" min-width="100px"></el-table-column>
      </el-table>
      <pagination
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
          :pagination="tableData.pagination"
      >
      </pagination>
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
      // 搜索
      searchForm: {
        loginUsername: '',
        ip: '',
        serverIp: '',
        success: '',
        loginTime: ''
      }
    }
  },
  created () {
    this.config({
      module: '登录日志',
      api: '/system/loginLog',
      'field.id': 'id',
      'field.main': 'id'
    })
    this.search()
  }
}
</script>
