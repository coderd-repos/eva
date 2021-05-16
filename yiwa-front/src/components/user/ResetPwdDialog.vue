<template>
  <GlobalWindow
    :visible="visible"
    :confirm-working="isWorking"
    width="576px"
    title="重置密码"
    @confirm="confirm"
    @close="close"
  >
    <p class="tip" v-if="user != null">为用户 <em>{{user.realname}}</em> 重置密码</p>
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="新密码" prop="password" required>
        <el-input v-model="form.password" type="password" placeholder="请输入新密码"></el-input>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import GlobalWindow from '../common/GlobalWindow'
import { resetPwd } from '../../api/system/systemUser'
export default {
  name: 'ResetPwdDialog',
  components: { GlobalWindow },
  props: {
    // 是否展示Dialog
    visible: {
      type: Boolean,
      required: true
    },
    // 用户
    user: {
      type: Object
    }
  },
  data () {
    return {
      isWorking: false,
      form: {
        password: ''
      },
      rules: {
        password: [
          { required: true, message: '请输入密码' }
        ]
      }
    }
  },
  methods: {
    // 重置字段
    resetFields () {
      this.$refs.form.resetFields()
    },
    // 确认重置密码
    confirm () {
      if (this.isWorking) {
        return
      }
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        this.isWorking = true
        resetPwd({
          id: this.user.id,
          password: this.form.password
        })
          .then(() => {
            this.$message.success('密码重置成功')
            this.close()
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking = false
          })
      })
    },
    // 关闭
    close () {
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style scoped lang="scss">
@import "../../assets/style/variables.scss";
// 角色配置
.global-dialog {
  .tip {
    margin-bottom: 12px;
    em {
      font-style: normal;
      color: $primary-color;
      font-weight: bold;
    }
  }
}
</style>
