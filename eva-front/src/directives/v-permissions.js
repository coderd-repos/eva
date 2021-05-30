export default {
  inserted: function (el, binding, vnode) {
    // 获取用户信息
    const userInfo = vnode.context.$store.state.userInfo
    if (userInfo == null) {
      el.parentNode && el.parentNode.removeChild(el)
      return
    }
    // 获取配置权限
    const configPermissions = binding.value
    if (configPermissions == null) {
      return
    }
    if (!(configPermissions instanceof Array)) {
      throw new Error('v-permissions的值必须为一个数组')
    }
    // 验证权限
    if (configPermissions.findIndex(code => userInfo.permissions.findIndex(p => p === code) > -1) === -1) {
      el.parentNode && el.parentNode.removeChild(el)
    }
  }
}
