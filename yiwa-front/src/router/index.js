import Vue from 'vue'
import Router from 'vue-router'
import AppLayout from '../layouts/AppLayout'
import { getUserInfo } from '../assets/api/system/common'
Vue.use(Router)

const Login = () => import('../views/login')
const Workbench = () => import('../views/workbench')

const router = new Router({
  routes: [
    {
      name: 'login',
      path: '/login',
      component: Login
    },
    {
      name: 'index',
      path: '/',
      redirect: 'workbench'
    },
    {
      name: 'layout',
      path: '',
      component: AppLayout,
      children: [
        {
          name: 'workbench',
          path: '/workbench',
          meta: {
            title: '工作台'
          },
          component: Workbench
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  let userInfo = router.app.$options.store.state.userInfo
  if (userInfo != null) {
    next()
    return
  }
  // 登录页面处理
  if (to.name === 'login') {
    next()
    return
  }
  // 非登录页面，验证用户是否登录
  getUserInfo()
    .then(userInfo => {
      // 已登录，存储userInfo
      router.app.$store.commit('setUserInfo', userInfo)
      next()
    })
    .catch(() => {
      // 未登录，跳转至登录页
      next({ name: 'login' })
    })
})

export default router
