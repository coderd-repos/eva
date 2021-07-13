<template>
  <GlobalWindow
    class="position-user-window"
    width="80%"
    :title="departmentName + '人员列表'"
    :visible.sync="visible"
    :with-footer="false"
  >
    <TableLayout :with-breadcrumb="false">
      <!-- 搜索表单 -->
      <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="80px" inline>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="searchForm.username" placeholder="请输入用户名" v-trim @keypress.enter.native="search"/>
        </el-form-item>
        <el-form-item label="姓名" prop="realname">
          <el-input v-model="searchForm.realname" placeholder="请输入姓名" v-trim @keypress.enter.native="search"/>
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile">
          <el-input v-model="searchForm.mobile" placeholder="请输入手机号码" v-trim @keypress.enter.native="search"/>
        </el-form-item>
        <section>
          <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
          <el-button @click="reset">重置</el-button>
        </section>
      </el-form>
      <div slot="space" class="toolbar">
        <el-switch v-model="onlyCurrentDept" @change="search" :disabled="isWorking.search"/>
        <label>仅查看当前部门人员</label>
      </div>
      <template v-slot:table-wrap>
        <el-table
          v-loading="isWorking.search"
          :data="tableData.list"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column prop="avatar" label="头像" width="80px" class-name="table-column-avatar" fixed="left">
            <template slot-scope="{row}">
              <img :src="row.avatar == null ? '/static/avatar/man.png' : row.avatar">
            </template>
          </el-table-column>
          <el-table-column prop="realname" label="姓名" min-width="100px" fixed="left"></el-table-column>
          <el-table-column prop="username" label="用户名" min-width="100px"></el-table-column>
          <el-table-column prop="empNo" label="工号" min-width="80px"></el-table-column>
          <el-table-column prop="department" label="部门" min-width="120px">
            <template slot-scope="{row}">{{row.department == null ? '' : row.department.name}}</template>
          </el-table-column>
                    <el-table-column prop="position" label="岗位" min-width="120px" class-name="table-column-strings">
            <template slot-scope="{row}">
              <ul>
                <li v-for="position in row.positions" :key="position.id">{{position.name}}</li>
              </ul>
            </template>
          </el-table-column>
          <el-table-column prop="sex" label="性别" min-width="80px">
            <template slot-scope="{row}">
              {{row.sex | sex}}
            </template>
          </el-table-column>
          <el-table-column prop="mobile" label="手机号码" min-width="100px"></el-table-column>
          <el-table-column prop="email" label="邮箱" min-width="180px"></el-table-column>
          <el-table-column prop="birthday" label="生日" min-width="100px"></el-table-column>
          <el-table-column prop="birthday" label="角色" min-width="160px" class-name="table-column-role">
            <template slot-scope="{row}">
              <ul>
                <li v-for="role in row.roles" :key="role.id">{{role.name}}</li>
              </ul>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
          :pagination="tableData.pagination"
        ></pagination>
      </template>
    </TableLayout>
  </GlobalWindow>
</template>

<script>
import TableLayout from '@/layouts/TableLayout'
import BaseTable from '@/components/base/BaseTable'
import GlobalWindow from '@/components/common/GlobalWindow'
import Pagination from '@/components/common/Pagination'
import { fetchUserList } from '@/api/system/department'
export default {
  name: 'DepartmentUserWindow',
  extends: BaseTable,
  components: { Pagination, GlobalWindow, TableLayout },
  data () {
    return {
      departmentId: null,
      departmentName: '',
      visible: false,
      // 仅查看当前部门
      onlyCurrentDept: true,
      // 搜索表单
      searchForm: {
        departmentId: null,
        username: '',
        realname: '',
        mobile: ''
      }
    }
  },
  methods: {
    // 打开查看人员窗口
    open (departmentId, departmentName) {
      this.departmentId = departmentId
      this.departmentName = departmentName
      this.searchForm.departmentId = departmentId
      this.visible = true
      this.search()
    },
    // 处理分页
    handlePageChange (pageIndex) {
      // 仅查看当前部门处理
      this.searchForm.strictDeptId = null
      this.searchForm.rootDeptId = this.searchForm.departmentId
      if (this.onlyCurrentDept) {
        this.searchForm.strictDeptId = this.searchForm.departmentId
        this.searchForm.rootDeptId = null
      }
      this.tableData.pagination.pageIndex = pageIndex
      this.isWorking.search = true
      fetchUserList({
        page: pageIndex,
        capacity: this.tableData.pagination.pageSize,
        model: this.searchForm
      })
        .then(data => {
          this.tableData.list = data.records
          this.tableData.pagination.total = data.total
        })
        .catch(e => {
          this.$tip.apiFailed(e)
        })
        .finally(() => {
          this.isWorking.search = false
        })
    }
  }
}
</script>

<style scoped lang="scss">
.position-user-window {
  /deep/ .table-search-form {
    padding: 0;
  }
  /deep/ .window__body {
    background: #f7f7f7;
    .table-content {
      padding: 0;
      .table-wrap {
        padding: 0;
      }
    }
  }
  .toolbar {
    margin-top: 10px;
    padding: 6px 12px;
    background: #fff;
    font-size: 13px;
    label {
      margin-left: 8px;
      vertical-align: middle;
      color: #999;
    }
  }
  // 列表头像处理
  .table-column-avatar {
    img {
      width: 48px;
    }
  }
  // 列表角色处理
  .table-column-role {
    ul {
      li {
        display: inline-block;
        background: #eee;
        border-radius: 3px;
        padding: 0 3px;
        margin-right: 3px;
      }
    }
  }
}
</style>
