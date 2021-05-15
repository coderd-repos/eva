import axios from 'axios'
import { trim } from './util'
axios.defaults.headers.common['Content-Type'] = 'application/json;charset=UTF-8'
const axiosInstance = axios.create({
  baseURL: '/api',
  // 请求超时时间
  timeout: 300000
})

// 添加请求拦截器
axiosInstance.interceptors.request.use(function (config) {
  // 参数去空格
  if (config.trim === true) {
    if (config.data != null) {
      config.data = trim(config.data)
    }
    if (config.params != null) {
      config.params = trim(config.params)
    }
  }
  return config
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error)
})

// 添加响应拦截器
axiosInstance.interceptors.response.use(response => {
  console.log('response.data', response.data)
  // 请求失败
  if (response.status !== 200) {
    return Promise.reject(response.data)
  }
  // 未登录
  if (response.data.code === 401) {
    window.location.href = '/#/login'
    return
  }
  // 业务失败
  if (response.data.code !== 200) {
    return Promise.reject(response.data)
  }
  return response.data.data
}, function (error) {
  // 对响应错误做点什么
  return Promise.reject(error)
})

export default axiosInstance
