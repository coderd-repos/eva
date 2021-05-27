<template>
  <div class="main-header">
    <div class="header">
      <h2>
        <i class="el-icon-s-unfold" v-if="menuData.collapse" @click="switchCollapseMenu(null)"></i>
        <i class="el-icon-s-fold" v-else @click="switchCollapseMenu(null)"></i>
        {{title}}
      </h2>
      <div class="user">
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            <img v-if="userInfo != null" :src="userInfo.avatar == null ? '@/assets/images/avatar/man.png' : userInfo.avatar" alt="">{{userInfo | displayName}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="changePwd">修改密码</el-dropdown-item>
            <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <!-- 修改密码 -->
    <GlobalWindow
      title="修改密码"
      :visible.sync="visible.changePwd"
      @confirm="confirmChangePwd"
      @close="visible.changePwd = false"
    >
      <el-form :model="changePwdData.form" ref="changePwdDataForm" :rules="changePwdData.rules">
        <el-form-item label="原始密码" prop="oldPwd" required>
          <el-input v-model="changePwdData.form.oldPwd" type="password" placeholder="请输入原始密码" maxlength="30" show-password></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPwd" required>
          <el-input v-model="changePwdData.form.newPwd" type="password" placeholder="请输入新密码" maxlength="30" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPwd" required>
          <el-input v-model="changePwdData.form.confirmPwd" type="password" placeholder="请再次输入新密码" maxlength="30" show-password></el-input>
        </el-form-item>
      </el-form>
    </GlobalWindow>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import GlobalWindow from './GlobalWindow'
import { logout, updatePwd } from '@/api/system/common'
export default {
  name: 'Header',
  components: { GlobalWindow },
  data () {
    return {
      visible: {
        // 修改密码
        changePwd: false
      },
      isWorking: {
        // 修改密码
        changePwd: false
      },
      username: 'bob', // 用户名
      // 修改密码弹框
      changePwdData: {
        form: {
          oldPwd: '',
          newPwd: '',
          confirmPwd: ''
        },
        rules: {
          oldPwd: [
            { required: true, message: '请输入原始密码' }
          ],
          newPwd: [
            { required: true, message: '请输入新密码' }
          ],
          confirmPwd: [
            { required: true, message: '请再次输入新密码' }
          ]
        }
      }
    }
  },
  computed: {
    ...mapState(['menuData', 'userInfo']),
    title () {
      return this.$route.meta.title
    }
  },
  filters: {
    // 展示名称
    displayName (userInfo) {
      if (userInfo == null) {
        return ''
      }
      if (userInfo.realname != null && userInfo.realname.trim().length > 0) {
        return userInfo.realname
      }
      return userInfo.username
    }
  },
  methods: {
    ...mapMutations(['setUserInfo', 'switchCollapseMenu']),
    // 修改密码
    changePwd () {
      this.visible.changePwd = true
      this.$nextTick(() => {
        this.$refs.changePwdDataForm.resetFields()
      })
    },
    // 确定修改密码
    confirmChangePwd () {
      if (this.isWorking.changePwd) {
        return
      }
      this.$refs.changePwdDataForm.validate((valid) => {
        if (!valid) {
          return
        }
        // 验证两次密码输入是否一致
        if (this.changePwdData.form.newPwd !== this.changePwdData.form.confirmPwd) {
          this.$tip.warning('两次密码输入不一致')
          return
        }
        // 执行修改
        this.isWorking.changePwd = true
        updatePwd({
          oldPwd: this.changePwdData.form.oldPwd,
          newPwd: this.changePwdData.form.newPwd
        })
          .then(() => {
            this.$tip.apiSuccess('修改成功')
            this.visible.changePwd = false
          })
          .catch(e => {
            this.$tip.apiFailed(e)
          })
          .finally(() => {
            this.isWorking.changePwd = false
          })
      })
    },
    // 退出登录
    logout () {
      logout()
        .then(() => {
          this.$router.push({ name: 'login' })
          this.setUserInfo(null)
        })
        .catch(e => {
          this.$tip.apiFailed(e)
        })
    }
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/style/variables.scss";
.header {
  overflow: hidden;
  padding: 0 25px;
  background: #fff;
  height: 100%;
  display: flex;
  h2 {
    width: 50%;
    flex-shrink: 0;
    line-height: $header-height;
    font-size: 19px;
    font-weight: 600;
    color: #606263;
    display: inline;
    & > i {
      font-size: 20px;
      margin-right: 12px;
    }
  }
  .user {
    width: 50%;
    flex-shrink: 0;
    text-align: right;
    .el-dropdown {
      top: 2px;
    }
    img {
      width: 32px;
      position: relative;
      top: 10px;
      margin-right: 10px;
    }
  }
}
// 下拉菜单框
.el-dropdown-menu {
  width: 140px;
  .el-dropdown-menu__item:hover {
    background: #E3EDFB;
    color: $primary-color;
  }
}
</style>
