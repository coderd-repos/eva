<template>
  <div class="wrap">
    <div class="introduce">
      <h2>基于懒猴子CG的</h2>
      <h3>企业级数据管理系统模版</h3>
      <p>一套企业级数据管理系统模版，结合懒猴子CG极速开发平台可快速生成页面和接口</p>
    </div>
    <div class="login">
      <h1>系统登录&nbsp;/&nbsp;LOGIN IN</h1>
      <div class="info-input">
        <el-input placeholder="请输入您的账号" v-model="username"></el-input>
        <el-input placeholder="请输入您的密码" v-model="password" @keypress.enter.native="login" type="password" show-password></el-input>
      </div>
      <el-button :loading="loading" @click="login">登&nbsp;&nbsp;录</el-button>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import { loginByPassword } from '../assets/api/system/common'

export default {
  name: 'Login',
  data () {
    return {
      username: '', // 账号输入框
      password: '', // 密码
      loading: false // 登录加载中
    }
  },
  methods: {
    ...mapMutations(['setUserInfo']),
    // 登录
    login () {
      if (this.loading) {
        return
      }
      if (!this.__check()) {
        return
      }
      this.loading = true
      loginByPassword({
        username: this.username,
        password: this.password
      })
        .then(data => {
          this.setUserInfo(data)
          this.$nextTick(() => {
            this.$router.push('/workbench')
          })
        })
        .catch(e => {
          this.$message.error(e.message)
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 登录前验证
    __check () {
      if (this.username.trim() === '') {
        this.$message.error('账号不能为空')
        return false
      }
      if (this.password.trim() === '') {
        this.$message.error('密码不能为空')
        return false
      }
      return true
    }
  }
}
</script>

<style scoped lang="less">
@import "../assets/style/variable";
.wrap {
  display: flex;
  width: 100%;
  height: 100vh;
  background-image: url("../../static/login.jpg");
  background-repeat: no-repeat;
  background-size: auto 180%;
  background-clip: content-box;
  background-position: center;
  // 左边介绍
  .introduce {
    padding-left: 10%;
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    color: #fff;
    background: rgba(0, 0, 0, 0.4);
    display: flex;
    flex-direction: column;
    justify-content: center;
    h2 {
      font-size:34px;
      font-style: italic;
      font-weight: 900;
      margin-top: 50px;
    }
    h3 {
      font-size: 49px;
      font-weight: 300;
      margin: 25px 0;
    }
  }
  // 右边登录
  .login {
    height: 100%;
    width: 45%;
    max-width: 640px;
    flex-shrink: 0;
    text-align: center;
    background: #fff;
    padding: 0 8%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    box-sizing: border-box;
    h1 {
      font-size: 28px;
      font-weight: 500;
    }
    .info-input {
      margin-top: 32px;
      margin-bottom: 62px;
      /deep/ .el-input {
        margin-top: 38px;
        .el-input__inner {
          height: 50px;
          background: #F9F9F9;
          border: 1px solid transparent;
          &:focus {
            border: 1px solid @primary-color;
          }
        }
      }
    }
    .el-button {
      height: 50px;
      width: 100%;
      color: #fff;
      font-size: 16px;
      background: linear-gradient(130deg,@primary-color + 20 0%,@primary-color - 20 100%);
    }
  }
}
</style>
