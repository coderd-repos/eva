<script>
import { mapState } from 'vuex'
export default {
  name: 'BasePage',
  data () {
    return {
      // 超级管理员角色code
      adminCode: 'admin'
    }
  },
  computed: {
    ...mapState(['userInfo']),
    // 是否为超级管理员
    isAdmin () {
      return this.userInfo.roles.findIndex(code => code === this.adminCode) > -1
    }
  },
  methods: {
    // 是否包含指定角色
    containRoles (roles) {
      if (roles == null) {
        return true
      }
      if (this.userInfo == null) {
        return false
      }
      if (this.userInfo.roles == null || this.userInfo.roles.length === 0) {
        return false
      }
      for (const code of roles) {
        if (this.userInfo.roles.findIndex(r => r === code) > -1) {
          return true
        }
      }
      return false
    },
    // 是否包含指定权限
    containPermissions (permissions) {
      if (permissions == null) {
        return true
      }
      if (this.userInfo == null) {
        return false
      }
      if (this.userInfo.permissions == null || this.userInfo.permissions.length === 0) {
        return false
      }
      for (const code of permissions) {
        if (this.userInfo.permissions.findIndex(p => p === code) > -1) {
          return true
        }
      }
      return false
    }
  }
}
</script>
