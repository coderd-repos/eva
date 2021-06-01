import axios from 'axios'
import Cookies from 'js-cookie'
import pkg from '../../package'
import { trim } from './util'
axios.defaults.headers.common['Content-Type'] = 'application/json;charset=UTF-8'
const axiosInstance = axios.create({
  baseURL: process.env.VUE_APP_API_PREFIX,
  // 请求超时时间
  timeout: 300000
})

// 新建请求拦截器
axiosInstance.interceptors.request.use(config => {
  // 参数去空格
  if (config.trim === true) {
    if (config.data != null) {
      config.data = trim(config.data)
    }
    if (config.params != null) {
      config.params = trim(config.params)
    }
  }
  // 设置操作平台
  config.headers['eva-platform'] = `pc-${pkg.version}`
  // 设置认证头
  const authToken = Cookies.get('eva-auth-token')
  if (authToken != null) {
    config.headers['eva-auth-token'] = Cookies.get('eva-auth-token')
  }
  return config
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error)
})

// 新建响应拦截器
axiosInstance.interceptors.response.use((response) => {
  // 请求失败
  if (response.status !== 200) {
    return Promise.reject(response.data)
  }
  // 未登录
  if (response.data.code === 401) {
    window.location.href = '/#/login'
    return Promise.reject(response.data)
  }
  // 业务失败
  if (!response.data.success) {
    return Promise.reject(response.data)
  }
  return response.data.data
}, function (error) {
  // 对响应错误做点什么
  return Promise.reject(error)
})

export default axiosInstance
