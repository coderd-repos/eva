import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import directives from './directives'
import filters from './filters'
import './assets/style/element-variables.scss'
import { fetchMenuTree } from './api/system/menu'
import { mapState, mapMutations } from 'vuex'

Vue.use(ElementUI, {
  size: 'small'
})

Vue.config.productionTip = false
Vue.use(directives)
Vue.use(filters)

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
          // 添加404重定向到workbench
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
    // 添加路由
    __addRouters (routes, parents = []) {
      if (routes == null || routes.length === 0) {
        return
      }
      const rs = router.getRoutes()
      for (const route of routes) {
        if (rs.findIndex(r => r.name === route.name) > -1) {
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
        const parentsDump = JSON.parse(JSON.stringify(parents))
        parentsDump.push(route)
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
