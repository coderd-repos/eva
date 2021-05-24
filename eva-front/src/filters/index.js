export default {
  install (Vue) {
    // 性别
    Vue.filter('sex', (value) => {
      if (value === '1') {
        return '男'
      }
      if (value === '0') {
        return '女'
      }
      return '未知'
    })
    // 启用禁用
    Vue.filter('disabledText', (value) => {
      if (value) {
        return '停用'
      }
      return '启用'
    })
  }
}
