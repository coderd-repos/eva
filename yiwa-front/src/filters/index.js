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
  }
}
