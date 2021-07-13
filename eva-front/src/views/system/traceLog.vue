<template>
  <TableLayout :permissions="['system:traceLog:query']">
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
      <el-form-item label="状态" prop="status">
        <el-select v-model="searchForm.status" clearable @change="search">
          <el-option value="-1" label="未处理"/>
          <el-option value="0" label="失败"/>
          <el-option value="1" label="成功"/>
        </el-select>
      </el-form-item>
      <el-form-item label="异常等级" prop="exceptionLevel">
        <el-select v-model="searchForm.exceptionLevel" clearable @change="search">
          <el-option value="10" label="高"/>
          <el-option value="5" label="中"/>
          <el-option value="0" label="低"/>
        </el-select>
      </el-form-item>
      <el-form-item label="操作时间范围">
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
    <div slot="space" class="status-bar">
      <label class="status-normal">正常</label>
      <label class="status-warn">警告异常(需排查)</label>
      <label class="status-danger">系统异常(需修复)</label>
    </div>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <el-table
        v-loading="isWorking.search"
        :data="tableData.list"
        stripe
        :default-sort="{prop: 'operaTime', order: 'descending'}"
        :row-class-name="tableRowClassName"
        @sort-change="handleSortChange"
      >
        <el-table-column prop="operaModule" label="业务模块" min-width="100px"></el-table-column>
        <el-table-column prop="operaRemark" label="操作说明" min-width="100px"></el-table-column>
        <el-table-column prop="requestMethod" label="请求方式" min-width="80px"></el-table-column>
        <el-table-column prop="requestUri" label="请求地址" min-width="200px"></el-table-column>
        <el-table-column prop="status" label="状态" min-width="80px">
          <template slot-scope="{row}">
            {{row.status | statusText}}
          </template>
        </el-table-column>
        <el-table-column prop="requestParams" label="请求参数" min-width="80px">
          <template slot-scope="{row}">
            <ColumnDetail v-if="row.requestParams != null" :content="row.requestParams" :limit="0"/>
          </template>
        </el-table-column>
        <el-table-column prop="requestResult" label="请求结果" min-width="80px">
          <template slot-scope="{row}">
            <ColumnDetail v-if="row.requestResult != null" :content="row.requestResult"/>
          </template>
        </el-table-column>
        <el-table-column prop="exceptionLevel" label="异常等级" sortable="custom" sort-by="EXCEPTION_LEVEL" min-width="100px">
          <template slot-scope="{row}">
            {{row.exceptionLevel | exceptionLevelText}}
          </template>
        </el-table-column>
        <el-table-column prop="exceptionStack" label="异常信息" min-width="170px">
          <template slot-scope="{row}">
            <ColumnDetail v-if="row.exceptionStack != null" :content="row.exceptionStack" :button-type="getExceptionButtonType(row.exceptionLevel)"/>
          </template>
        </el-table-column>
        <el-table-column prop="operaSpendTime" label="请求耗时(ms)" sortable="custom" sort-by="OPERA_SPEND_TIME" min-width="120px"></el-table-column>
        <el-table-column prop="userRealname" label="操作人" min-width="100px"></el-table-column>
        <el-table-column prop="operaTime" label="操作时间" sortable="custom" sort-by="OPERA_TIME" min-width="140px"></el-table-column>
        <el-table-column prop="platform" label="操作平台" min-width="100px"></el-table-column>
        <el-table-column prop="systemVersion" label="系统版本" min-width="80px"></el-table-column>
        <el-table-column prop="serverIp" label="处理服务器IP" min-width="100px"></el-table-column>
        <el-table-column prop="ip" label="用户IP" min-width="100px"></el-table-column>
        <el-table-column prop="clientInfo" label="用户客户端" min-width="200px"></el-table-column>
        <el-table-column prop="osInfo" label="用户操作系统" min-width="100px"></el-table-column>
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
import Pagination from '@/components/common/Pagination'
import TableLayout from '@/layouts/TableLayout'
import BaseTable from '@/components/base/BaseTable'
import ColumnDetail from '../../components/common/ColumnDetail'

export default {
  name: 'SystemTraceLog',
  extends: BaseTable,
  components: { ColumnDetail, TableLayout, Pagination },
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
        exceptionLevel: null,
        startTime: null,
        endTime: null
      }
    }
  },
  filters: {
    // 状态
    statusText (value) {
      if (value === 1) {
        return '成功'
      }
      if (value === 0) {
        return '失败'
      }
      return '未处理'
    },
    // 异常等级
    exceptionLevelText (value) {
      if (value == null) {
        return ''
      }
      if (value === 0) {
        return '低'
      }
      if (value === 5) {
        return '中'
      }
      if (value === 10) {
        return '高'
      }
      return '未知'
    }
  },
  methods: {
    // 搜索框重置
    reset () {
      this.$refs.searchForm.resetFields()
      this.searchDateRange = []
      this.searchForm.startTime = null
      this.searchForm.endTime = null
      this.search()
    },
    // 标记行class
    tableRowClassName ({ row }) {
      if (row.exceptionLevel === 5 || row.status === -1) {
        return 'warning-log'
      } else if (row.exceptionLevel === 10) {
        return 'danger-log'
      }
      return ''
    },
    // 获取异常查看按钮类型
    getExceptionButtonType (level) {
      if (level === 5) {
        return 'warning'
      }
      if (level === 10) {
        return 'danger'
      }
      return null
    },
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

<style scoped lang="scss">
// 状态栏
.status-bar {
  padding: 0 16px;
  [class^=status-] {
    font-size: 13px;
    margin-right: 12px;
    line-height: 40px;
    &::before {
      position: relative;
      top: 2px;
      display: inline-block;
      content: '';
      width: 12px;
      height: 12px;
      border: 1px solid #ccc;
      background: #fff;
      margin-right: 6px;
    }
  }
  .status-warn::before {
    background-color: oldlace;
    border-color: orange;
  }
  .status-danger::before {
    background-color: #fdf0f0;
    border-color: indianred;
  }
}
/deep/ .table-content {
  margin-top: 0;
}
// 警告级日志
/deep/ .warning-log td {
  background-color: oldlace !important;
}
// 危险级日志
/deep/ .danger-log td {
  background-color: #fdf0f0 !important;
}
</style>
