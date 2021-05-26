<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="角色编码" prop="code" required>
        <el-input v-model="form.code" placeholder="请输入角色编码" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="角色名称" prop="name" required>
        <el-input v-model="form.name" placeholder="请输入角色名称" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="角色备注" prop="remark">
        <el-input v-model="form.remark" type="textarea" placeholder="请输入角色备注" :rows="3" v-trim maxlength="500"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import BaseOpera from '@/components/base/BaseOpera'
import GlobalWindow from '@/components/common/GlobalWindow'
export default {
  name: 'OperaRoleWindow',
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
          { required: true, message: '请输入角色编码' }
        ],
        name: [
          { required: true, message: '请输入角色名称' }
        ]
      }
    }
  },
  created () {
    this.config({
      api: '/system/role'
    })
  }
}
</script>
