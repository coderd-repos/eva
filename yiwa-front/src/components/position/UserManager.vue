<template>
  <GlobalWindow
    width="80%"
    title="岗位人员管理"
    :visible.sync="visible.userManage"
    :confirm-working="isWorking.userManage"
    @confirm="confirm"
  >
    <TableLayout>
      <!-- 搜索表单 -->
      <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="80px" inline>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="searchForm.username" placeholder="请输入用户名" @keypress.enter.native="search"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="realname">
          <el-input v-model="searchForm.realname" placeholder="请输入姓名" @keypress.enter.native="search"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile">
          <el-input v-model="searchForm.mobile" placeholder="请输入手机号码" @keypress.enter.native="search"></el-input>
        </el-form-item>
        <section>
          <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
          <el-button @click="reset">重置</el-button>
        </section>
      </el-form>
      <template v-slot:table-wrap>
        <el-table
            v-loading="isWorking.userSearch"
            :data="userManageData.list"
            stripe
            @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="avatar" label="头像" width="80px" class-name="table-column-avatar" fixed="left">
            <template slot-scope="{row}">
              <img :src="row.avatar == null ? '/static/avatar/man.png' : row.avatar">
            </template>
          </el-table-column>
          <el-table-column prop="realname" label="姓名" min-width="100px" fixed="left"></el-table-column>
          <el-table-column prop="username" label="用户名" min-width="100px"></el-table-column>
          <el-table-column prop="empNo" label="工号" min-width="80px"></el-table-column>
          <el-table-column prop="sex" label="性别" min-width="80px">
            <template slot-scope="{row}">
              {{row.sex | sex}}
            </template>
          </el-table-column>
          <el-table-column prop="email" label="邮箱" min-width="160px"></el-table-column>
          <el-table-column prop="mobile" label="手机号码" min-width="100px"></el-table-column>
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
        >
        </pagination>
      </template>
    </TableLayout>
  </GlobalWindow>
</template>

<script>
import TableLayout from '../../layouts/TableLayout'
export default {
  name: 'UserManager',
  components: { TableLayout }
}
</script>

<style scoped>

</style>
