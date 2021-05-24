import vPermissions from './v-permissions'
import vRoles from './v-roles'
import vTrim from './v-trim'
export default {
  install (Vue) {
    // 角色控制指令
    Vue.directive('roles', vRoles)
    // 权限控制指令
    Vue.directive('permissions', vPermissions)
    // 自动去空指令
    Vue.directive('trim', vTrim)
  }
}
