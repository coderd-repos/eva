<template>
  <TableLayout v-permissions="['system:traceLog:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="100px" inline>
      <el-form-item label="用户姓名" prop="userRealname">
        <el-input v-model="searchForm.userRealname" placeholder="请输入固化用户姓名" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="业务模块" prop="operaModule">
        <el-input v-model="searchForm.operaModule" placeholder="请输入业务模块" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="请求地址" prop="requestUri">
        <el-input v-model="searchForm.requestUri" placeholder="请输入请求地址" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="处理状态" prop="status">
        <el-select v-model="searchForm.status" clearable @change="search">
          <el-option value="-1" label="未处理"/>
          <el-option value="0" label="失败"/>
          <el-option value="1" label="成功"/>
        </el-select>
      </el-form-item>
      <el-form-item label="操作时间范围" prop="operaModule">
        <el-date-picker
          v-model="searchDateRange"
          type="datetimerange"
          range-separator="至"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          @change="handleSearchTimeChange"
        >
        </el-date-picker>
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
        :default-sort = "{prop: 'operaTime', order: 'descending'}"
        @selection-change="handleSelectionChange"
        @sort-change="handleSortChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="operaModule" label="业务模块" min-width="100px"></el-table-column>
        <el-table-column prop="operaRemark" label="操作说明" min-width="100px"></el-table-column>
        <el-table-column prop="userRealname" label="操作人" min-width="100px"></el-table-column>
        <el-table-column prop="operaTime" label="操作时间" sortable="custom" sort-by="OPERA_TIME" min-width="140px"></el-table-column>
        <el-table-column prop="requestMethod" label="请求方式" min-width="80px"></el-table-column>
        <el-table-column prop="requestUri" label="请求地址" min-width="200px"></el-table-column>
        <el-table-column prop="requestParams" label="请求参数" min-width="80px">
          <template slot-scope="{row}">
            <el-popconfirm
              v-if="row.requestParams != null"
              :title="row.requestParams"
              :hide-icon="true"
              confirm-button-text='复制'
              cancel-button-text='关闭'
            >
              <el-button slot="reference">查看</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
        <el-table-column prop="requestResult" label="请求结果" min-width="100px">
          <template slot-scope="{row}">
            <el-popconfirm
                v-if="row.requestResult != null"
                :title="row.requestResult"
                :hide-icon="true"
                confirm-button-text='复制'
                cancel-button-text='关闭'
            >
              <el-button slot="reference">查看</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
        <el-table-column prop="operaSpendTime" label="处理耗时(ms)" sortable="custom" sort-by="OPERA_SPEND_TIME" min-width="120px"></el-table-column>
        <el-table-column prop="status" label="处理状态" min-width="80px">
          <template slot-scope="{row}">
            {{row.status | statusText}}
          </template>
        </el-table-column>
        <el-table-column prop="exceptionStack" label="异常信息" min-width="100px"></el-table-column>
        <el-table-column prop="serviceVersion" label="接口版本" min-width="80px"></el-table-column>
        <el-table-column prop="platform" label="操作平台" min-width="100px"></el-table-column>
        <el-table-column prop="ip" label="用户IP" min-width="100px"></el-table-column>
        <el-table-column prop="serverIp" label="服务器IP" min-width="100px"></el-table-column>
        <el-table-column prop="clientInfo" label="客户端" min-width="200px"></el-table-column>
        <el-table-column prop="systemInfo" label="操作系统" min-width="100px"></el-table-column>
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
import Pagination from '@/components/common/Pagination'
import TableLayout from '@/layouts/TableLayout'
import BaseTable from '@/components/base/BaseTable'
export default {
  name: 'SystemTraceLog',
  extends: BaseTable,
  components: { TableLayout, Pagination },
  data () {
    return {
      // 搜索时间反胃
      searchDateRange: [],
      // 搜索
      searchForm: {
        userRealname: '',
        operaModule: '',
        requestUri: '',
        status: null,
        startTime: null,
        endTime: null
      }
    }
  },
  filters: {
    statusText (value) {
      if (value === 1) {
        return '成功'
      }
      if (value === 0) {
        return '失败'
      }
      return '未处理'
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
      api: '/system/traceLog',
      sorts: [{
        property: 'OPERA_TIME',
        direction: 'DESC'
      }]
    })
    this.search()
  }
}
</script>
