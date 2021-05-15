import Vue from 'vue'
import VueRouter from 'vue-router'
import AppLayout from '../layouts/AppLayout'
import { getUserInfo } from '../api/system/common'
const Login = () => import('../views/login')
const Workbench = () => import('../views/workbench')

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
  console.log('to.name', to.name)
  const userInfo = router.app.$options.store.state.userInfo
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
    .catch(e => {
      // 未登录，跳转至登录页
      console.log('未登录')
      next({ name: 'login' })
    })
})

export default router
