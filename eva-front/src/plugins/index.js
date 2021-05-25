import message from './message'
import messagebox from './messagebox'
export default {
  install (Vue) {
    // 提醒对象
    Vue.prototype.$tip = message
    // 提示框对象
    Vue.prototype.$dialog = messagebox
  }
}
