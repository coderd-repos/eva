import Vue from 'vue'
import VueRouter from 'vue-router'
import AppLayout from '@/layouts/AppLayout'
import { getUserInfo } from '@/api/system/common'
const Login = () => import('@/views/login')
const ErrorNoPermissions = () => import('@/views/no-permissions')
const Error404 = () => import('@/views/not-found')

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  routes: [
    // 登录
    {
      name: 'login',
      path: '/login',
      component: Login
    },
    // 无权限
    {
      name: 'no-permissions',
      path: '/no-permissions',
      component: ErrorNoPermissions
    },
    // 404
    {
      name: 'not-found',
      path: '/not-found',
      component: Error404
    },
    // 内容页（动态加载）
    {
      name: 'layout',
      path: '',
      component: AppLayout,
      children: []
    }
  ]
})
router.beforeEach((to, from, next) => {
  // 无权访问&404页面可直接访问
  if (to.name === 'no-permissions' || to.name === 'not-found') {
    next()
    return
  }
  const userInfo = router.app.$options.store.state.userInfo
  if (userInfo != null) {
    // 如果用户不存在权限
    if (userInfo.permissions.length === 0) {
      next({ name: 'no-permissions' })
      return
    }
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
      // 如果用户不存在权限
      if (userInfo.permissions.length === 0) {
        next({ name: 'no-permissions' })
        return
      }
      // 已登录，存储userInfo
      router.app.$store.commit('setUserInfo', userInfo)
      next()
    })
    .catch(e => {
      // 未登录，跳转至登录页
      next({ name: 'login' })
    })
})

export default router
