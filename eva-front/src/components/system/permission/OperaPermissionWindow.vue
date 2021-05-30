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
import BaseOpera from '@/components/base/BaseOpera'
import GlobalWindow from '@/components/common/GlobalWindow'
export default {
  name: 'OperaPermissionWindow',
  extends: BaseOpera,
  components: { GlobalWindow },
  data () {
    return {
      // 原权限码
      originPermissionCode: '',
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
  methods: {
    open (title, target) {
      this.title = title
      this.visible = true
      // 新建
      if (target == null) {
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.form[this.configData['field.id']] = null
        })
        return
      }
      // 编辑
      this.$nextTick(() => {
        this.originPermissionCode = target.code
        for (const key in this.form) {
          this.form[key] = target[key]
        }
      })
    },
    confirm () {
      if (this.form.id == null || this.form.id === '') {
        this.__confirmCreate()
        return
      }
      if (this.originPermissionCode === this.form.code) {
        this.__confirmEdit()
        return
      }
      // 修改了权限编码
      this.$dialog.confirm('检测到您修改了权限编码，权限编码修改后前后端均可能需要调整代码，确认修改吗？', '提示', {
        confirmButtonText: '确认修改',
        type: 'warning'
      })
        .then(() => {
          this.__confirmEdit()
        })
    }
  },
  created () {
    this.config({
      api: '/system/permission'
    })
  }
}
</script>
