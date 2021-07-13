import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const state = {
  // 登录用户信息
  userInfo: null,
  // 首页
  homePage: null,
  // 菜单
  menuData: {
    // 菜单列表
    list: [],
    // 是否收起
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
  // 设置首页路由信息
  setHomePage (state, homePage) {
    state.homePage = homePage
  },
  // 重置菜单
  resetMenus: (state) => {
    state.menuData.list = []
  }
}
const actions = {}
const getters = {}
export default new Vuex.Store({
  state,
  mutations,
  actions,
  getters
})
