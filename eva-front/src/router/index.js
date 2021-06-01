import Vue from 'vue'
import VueRouter from 'vue-router'
import AppLayout from '@/layouts/AppLayout'
import { getUserInfo } from '@/api/system/common'
const Login = () => import('@/views/login')
const Workbench = () => import('@/views/workbench')
const NoPermissions = () => import('@/views/no-permissions')

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'hash',
  routes: [
    {
      name: 'login',
      path: '/login',
      component: Login
    },
    {
      name: 'no-permissions',
      path: '/no-permissions',
      component: NoPermissions
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
  // 无权访问页面可直接访问
  if (to.name === 'no-permissions') {
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
