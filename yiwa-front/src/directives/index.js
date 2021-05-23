import vPermissions from './v-permissions'
import vTrim from './v-trim'
export default {
  install (Vue) {
    // 权限指令
    Vue.directive('permissions', vPermissions)
    // 自动去空指令
    Vue.directive('trim', vTrim)
  }
}
