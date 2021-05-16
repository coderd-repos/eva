<template>
  <!-- 添加/修改 -->
  <GlobalWindow
    :title="title"
    :visible="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
    @close="close"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="用户名" prop="username" required>
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="realname" required>
        <el-input v-model="form.realname"></el-input>
      </el-form-item>
      <el-form-item label="工号" prop="empNo">
        <el-input v-model="form.empNo"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="sex" required>
        <el-radio-group v-model="form.sex">
          <el-radio label="1">男</el-radio>
          <el-radio label="0">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="头像" prop="avatar" required>
        <el-radio-group v-model="form.avatar" class="form-item-avatar">
          <el-radio label="/avatar/man.png" border><img src="/avatar/man.png" alt=""></el-radio>
          <el-radio label="/avatar/woman.png" border><img src="/avatar/woman.png" alt=""></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="form.id == null" label="密码" prop="password" required>
        <el-input type="password" v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="手机号码" prop="mobile">
        <el-input v-model="form.mobile" maxlength="11"></el-input>
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-date-picker v-model="form.birthday"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import { checkMobile, checkEmail } from '../../utils/form'
import {
  create,
  updateById
} from '../../api/system/systemUser'
import GlobalWindow from '../common/GlobalWindow'

export default {
  name: 'OperaUserWindow',
  components: { GlobalWindow },
  props: {
    // 标题
    title: {
      type: String,
      default: ''
    },
    // 是否展示Dialog
    visible: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {
      isWorking: false,
      // 表单数据
      form: {
        id: null,
        username: '', // 用户名
        realname: '', // 姓名
        empNo: '', // 工号
        avatar: '/static/avatar/man.png', // 头像
        password: '', // 密码
        mobile: '', // 手机号码
        email: '', // 邮箱
        sex: '1', // 性别
        birthday: '' // 生日
      },
      // 验证规则
      rules: {
        username: [
          { required: true, message: '请输入用户名' }
        ],
        realname: [
          { required: true, message: '请输入姓名' }
        ],
        password: [
          { required: true, message: '请输入密码' }
        ],
        avatar: [
          { required: true, message: '请选择用户头像' }
        ],
        sex: [
          { required: true, message: '请选择用户性别' }
        ],
        mobile: [
          { validator: checkMobile }
        ],
        email: [
          { validator: checkEmail }
        ]
      }
    }
  },
  methods: {
    // 重置字段
    resetFields () {
      this.$refs.form.resetFields()
    },
    // 初始化字段值
    initFields (row) {
      Object.assign(this.form, row)
    },
    // 确认创建/修改
    confirm () {
      if (this.form.id == null) {
        this.__confirmCreate()
        return
      }
      this.__confirmEdit()
    },
    close () {
      this.$emit('update:visible', false)
    },
    // 确认添加
    __confirmCreate () {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用添加接口
        this.isWorking = true
        create(this.form)
          .then(() => {
            this.close()
            this.$message.success('创建成功')
            this.$emit('edit-success')
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking = false
          })
      })
    },
    // 确认修改
    __confirmEdit () {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用添加接口
        this.isWorking = true
        updateById(this.form)
          .then(() => {
            this.close()
            this.$message.success('修改成功')
            this.$emit('create-success')
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking = false
          })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.global-dialog {
  >>> .el-radio-group {
    width: 100%;
  }
  >>> .el-date-editor {
    width: 100%;
  }
  // 表单头像处理
  .form-item-avatar {
    >>> .el-radio.is-bordered {
      height: auto;
      padding: 6px;
      margin: 0 10px 0 0;
      .el-radio__input {
        display: none;
      }
      .el-radio__label {
        padding: 0;
        width: 48px;
        display: block;
        img {
          width: 100%;
        }
      }
    }
  }
}
</style>
