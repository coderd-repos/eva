import consts from './consts'
import message from './message'
import messagebox from './messagebox'
import cache from './cache'
export default {
  install (Vue) {
    // 常量
    Vue.prototype.$consts = consts
    // 提醒对象
    Vue.prototype.$tip = message
    // 提示框对象
    Vue.prototype.$dialog = messagebox
    // 缓存对象
    Vue.prototype.$cache = cache
  }
}
