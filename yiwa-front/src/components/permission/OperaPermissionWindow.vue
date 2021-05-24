<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="权限编码" prop="code" required>
        <el-input v-model="form.code" placeholder="请输入权限编码" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="权限名称" prop="name" required>
        <el-input v-model="form.name" placeholder="请输入权限名称" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="权限备注" prop="remark">
        <el-input v-model="form.remark" placeholder="请输入权限备注" type="textarea" :rows="3" v-trim maxlength="500"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import BaseOpera from '../base/BaseOpera'
import GlobalWindow from '../common/GlobalWindow'
export default {
  name: 'OperaPermissionWindow',
  extends: BaseOpera,
  components: { GlobalWindow },
  data () {
    return {
      // 表单数据
      form: {
        id: null,
        code: '',
        name: '',
        remark: ''
      },
      // 验证规则
      rules: {
        code: [
          { required: true, message: '请输入权限编码' }
        ],
        name: [
          { required: true, message: '请输入权限名称' }
        ]
      }
    }
  },
  created () {
    this.config({
      api: '/system/permission'
    })
  }
}
</script>
