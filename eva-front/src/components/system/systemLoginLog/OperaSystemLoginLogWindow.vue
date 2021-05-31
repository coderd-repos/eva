<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="登录用户ID" prop="userId">
        <el-input v-model="operaTableData.form.userId" placeholder="请输入登录用户ID" v-trim/>
      </el-form-item>
      <el-form-item label="登录用户名" prop="loginUsername" required>
        <el-input v-model="operaTableData.form.loginUsername" placeholder="请输入登录用户名" v-trim/>
      </el-form-item>
      <el-form-item label="登录IP" prop="ip">
        <el-input v-model="operaTableData.form.ip" placeholder="请输入登录IP" v-trim/>
      </el-form-item>
      <el-form-item label="登录地址" prop="location">
        <el-input v-model="operaTableData.form.location" placeholder="请输入登录地址" v-trim/>
      </el-form-item>
      <el-form-item label="客户端" prop="clientInfo">
        <el-input v-model="operaTableData.form.clientInfo" placeholder="请输入客户端" v-trim/>
      </el-form-item>
      <el-form-item label="操作系统" prop="osInfo">
        <el-input v-model="operaTableData.form.osInfo" placeholder="请输入操作系统" v-trim/>
      </el-form-item>
      <el-form-item label="登录平台" prop="platform">
        <el-input v-model="operaTableData.form.platform" placeholder="请输入登录平台" v-trim/>
      </el-form-item>
      <el-form-item label="系统版本" prop="systemVersion">
        <el-input v-model="operaTableData.form.systemVersion" placeholder="请输入系统版本" v-trim/>
      </el-form-item>
      <el-form-item label="服务器IP" prop="serverIp">
        <el-input v-model="operaTableData.form.serverIp" placeholder="请输入服务器IP" v-trim/>
      </el-form-item>
      <el-form-item label="是否登录成功" prop="success">
        <el-input v-model="operaTableData.form.success" placeholder="请输入是否登录成功" v-trim/>
      </el-form-item>
      <el-form-item label="失败原因" prop="reason">
        <el-input v-model="operaTableData.form.reason" placeholder="请输入失败原因" v-trim/>
      </el-form-item>
      <el-form-item label="登录时间" prop="loginTime" required>
        <el-date-picker v-model="operaTableData.form.loginTime" value-format="yyyy-MM-dd" placeholder="请输入登录时间"></el-date-picker>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import BaseOpera from '@/components/base/BaseOpera'
import GlobalWindow from '@/components/common/GlobalWindow'
export default {
  name: 'OperaSystemLoginLogWindow',
  extends: BaseOpera,
  components: { GlobalWindow },
  data () {
    return {
      // 表单数据
      form: {
        // 表单数据
        form: {
          id: null,
          userId: '',
          loginUsername: '',
          ip: '',
          location: '',
          clientInfo: '',
          osInfo: '',
          platform: '',
          systemVersion: '',
          serverIp: '',
          success: '',
          reason: '',
          loginTime: ''
        }
      },
      // 验证规则
      rules: {
        loginUsername: [
          { required: true, message: '请输入登录用户名' }
        ],
        loginTime: [
          { required: true, message: '请选择登录时间' }
        ]
      }
    }
  },
  created () {
    this.config({
      api: '/system/systemLoginLog',
      'field.id': 'id'
    })
  }
}
</script>