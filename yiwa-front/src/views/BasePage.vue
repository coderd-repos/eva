<script>
import { mapState } from 'vuex'
export default {
  name: 'BasePage',
  computed: {
    ...mapState(['userInfo'])
  },
  methods: {
    // 是否包含指定权限
    containPermissions (permissions) {
      if (this.userInfo == null) {
        return false
      }
      if (this.userInfo.permissions == null || this.userInfo.permissions.length === 0) {
        return false
      }
      for (const code of permissions) {
        if (this.userInfo.permissions.findIndex(p => p.code === code) > -1) {
          return true
        }
      }
      return false
    }
  }
}
</script>
