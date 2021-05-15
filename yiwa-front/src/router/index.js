import Vue from 'vue'
import VueRouter from 'vue-router'
import AppLayout from '../layouts/AppLayout'
const Login = () => import('../views/login')
const Workbench = () => import('../views/workbench')

Vue.use(VueRouter)

const routes = [
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

const router = new VueRouter({
  mode: 'hash',
  routes
})

export default router
