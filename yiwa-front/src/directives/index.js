export default {
  install (Vue) {
    // 权限指令
    Vue.directive('permissions', {
      inserted: function (el, binding, vnode) {
        // 获取用户信息
        const userInfo = vnode.context.$store.state.userInfo
        if (userInfo == null) {
          el.parentNode && el.parentNode.removeChild(el)
          return
        }
        // 获取配置权限
        const configPermissions = binding.value
        if (!(configPermissions instanceof Array)) {
          throw new Error('v-permissions必须为一个数组')
        }
        // 验证权限
        if (configPermissions.findIndex(code => userInfo.permissions.findIndex(p => p.code === code) > -1) === -1) {
          el.parentNode && el.parentNode.removeChild(el)
        }
      }
    })
    // 自动去空指令
    Vue.directive('trim', {
      inserted: function (el) {
        let input = el
        let classes = input.getAttribute('class')
        if (classes != null) {
          classes = classes.split(' ')
        }
        // 输入框：<el-input/>
        if (classes.indexOf('el-input') > -1) {
          input = input.querySelector('input')
        }
        // 多行输入框：<el-input type="textarea"/>
        if (classes.indexOf('el-textarea') > -1) {
          input = input.querySelector('textarea')
        }
        // 失去焦点时去掉两侧空格
        input.addEventListener('blur', (e) => {
          e.target.value = e.target.value.trim()
          input.dispatchEvent(new Event('input'))
        })
      }
    })
  }
}
