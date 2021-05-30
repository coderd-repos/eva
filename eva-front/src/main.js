import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import './assets/style/element-variables.scss'
import VueClipboard from 'vue-clipboard2'
import directives from './directives'
import filters from './filters'
import plugins from './plugins'
import { mapState, mapMutations } from 'vuex'
import { fetchMenuTree } from './api/system/menu'

Vue.config.productionTip = false
Vue.use(ElementUI, {
  size: 'small'
})
Vue.use(VueClipboard)
Vue.use(directives)
Vue.use(filters)
Vue.use(plugins)

new Vue({
  data: {
    loading: false
  },
  router,
  store,
  computed: {
    ...mapState(['userInfo'])
  },
  watch: {
    async userInfo () {
      if (this.userInfo == null) {
        return
      }
      await this.initRoutes()
    }
  },
  methods: {
    ...mapMutations(['switchCollapseMenu']),
    // 初始化本地配置
    initLocalConfig () {
      // 菜单状态配置
      const menuStatus = window.localStorage.getItem('MENU_STATUS')
      if (menuStatus != null) {
        this.switchCollapseMenu(menuStatus === 'true')
      }
    },
    // 初始化路由
    async initRoutes () {
      if (this.loading) {
        return
      }
      this.loading = true
      // 重置菜单
      this.$store.commit('resetMenus')
      // 获取菜单
      const storeMenus = this.$store.state.menuData.list
      await fetchMenuTree()
        .then(menus => {
          // 添加菜单
          storeMenus.push.apply(storeMenus, menus)
          // 添加路由
          this.__addRouters(menus)
          // 404重定向到workbench
          router.addRoute({
            path: '*',
            redirect: '/workbench'
          })
        })
        .catch(e => {
          throw e
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 新建路由
    __addRouters (routes, parents = []) {
      if (routes == null || routes.length === 0) {
        return
      }
      const rs = router.getRoutes()
      for (const route of routes) {
        const parentsDump = JSON.parse(JSON.stringify(parents))
        parentsDump.push(route)
        if (route.url == null || route.url === '') {
          this.__addRouters(route.children, parentsDump)
          continue
        }
        if (rs.findIndex(r => r.path === route.url) > -1) {
          this.__addRouters(route.children, parentsDump)
          continue
        }
        router.addRoute('layout', {
          path: route.url,
          name: route.label,
          meta: {
            title: route.label,
            paths: [...parents.map(p => p.label), route.label]
          },
          component: () => import('@/views' + route.url)
        })
        this.__addRouters(route.children, parentsDump)
      }
    }
  },
  async created () {
    await this.initRoutes()
      .catch(() => {})
  },
  mounted () {
    this.initLocalConfig()
  },
  render: h => h(App)
}).$mount('#app')
