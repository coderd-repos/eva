export default {
  inserted: function (el, binding, vnode) {
    // 获取用户信息
    const userInfo = vnode.context.$store.state.userInfo
    if (userInfo == null) {
      el.parentNode && el.parentNode.removeChild(el)
      return
    }
    // 获取配置角色
    const configRoles = binding.value
    if (configRoles == null) {
      return
    }
    if (!(configRoles instanceof Array)) {
      throw new Error('v-roles的值必须为一个数组')
    }
    // 验证权限
    if (configRoles.findIndex(code => userInfo.roles.findIndex(r => r === code) > -1) === -1) {
      el.parentNode && el.parentNode.removeChild(el)
    }
  }
}
