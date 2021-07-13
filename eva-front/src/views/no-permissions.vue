<template>
  <NotAllow>
    <div class="content">
      <img src="../assets/images/not-allow.png">
      <h2>无权访问</h2>
      <p>如您需要访问该系统，请联系系统管理员</p>
      <el-button @click="logout" type="primary">退出系统</el-button>
    </div>
  </NotAllow>
</template>

<script>
import NotAllow from '../components/common/NotAllow'
import { mapMutations } from 'vuex'
import { logout } from '@/api/system/common'
export default {
  name: 'NoPermissions',
  components: { NotAllow },
  methods: {
    ...mapMutations(['setUserInfo']),
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

<style scoped>

</style>
