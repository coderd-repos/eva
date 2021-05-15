import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const state = {
  // 登录用户信息
  userInfo: null,
  // 菜单
  menuData: {
    list: [],
    collapse: false
  }
}

const mutations = {
  // 切换菜单状态
  switchCollapseMenu (state, value) {
    if (value != null) {
      state.menuData.collapse = value
    } else {
      state.menuData.collapse = !state.menuData.collapse
    }
    window.localStorage.setItem('MENU_STATUS', state.menuData.collapse)
  },
  // 设置已登录的用户信息
  setUserInfo: (state, data) => {
    state.userInfo = data
  },
  // 重置菜单
  resetMenus: (state) => {
    state.menuData.list = [
      {
        label: '工作台', // 菜单名称
        index: 'workbench', // 菜单唯一标识
        icon: 'el-icon-s-data', // 图标，从https://element.eleme.cn/#/zh-CN/component/icon获取
        url: '/workbench' // 菜单路径
      }
    ]
  }
}
const actions = {}
const getters = {}
export default new Vuex.Store({
  modules: {},
  state,
  mutations,
  actions,
  getters
})
