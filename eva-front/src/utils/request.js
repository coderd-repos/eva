import axios from 'axios'
import Cookies from 'js-cookie'
import pkg from '../../package'
import { trim } from './util'
import cache from '../plugins/cache'

axios.defaults.headers.common['Content-Type'] = 'application/json;charset=UTF-8'
const axiosInstance = axios.create({
  baseURL: process.env.VUE_APP_API_PREFIX,
  // 请求超时时间
  timeout: 60000
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
  // 导出处理
  if (config.download === true) {
    config.responseType = 'blob'
  }
  // 设置操作平台
  config.headers['eva-platform'] = `pc-${pkg.version}`
  // 设置认证头
  const authToken = Cookies.get('eva-auth-token')
  if (authToken != null) {
    config.headers['eva-auth-token'] = authToken
  }
  return config
}, function (error) {
  return Promise.reject(error)
})

// 新建响应拦截器
axiosInstance.interceptors.response.use((response) => {
  // 请求失败
  if (response.status !== 200) {
    return Promise.reject(new Error('服务器繁忙，请稍后再试'))
  }
  // 下载接口处理
  if (response.headers['eva-opera-type'] === 'download') {
    return Promise.resolve(response)
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
  if (error.code == null) {
    return Promise.reject(new Error('服务器繁忙，请稍后再试'))
  }
  if (error.code === 'ECONNABORTED' && error.message.indexOf('timeout') !== -1) {
    return Promise.reject(new Error('服务器响应超时，请稍后再试'))
  }
  return Promise.reject(error)
})

// 缓存请求结果
const buildCachePromise = (cacheKey, method, args, cacheImpl) => {
  return {
    __cacheImpl: cache[cacheImpl],
    __arguments: args,
    __result_promise: null,
    // 开启缓存
    cache () {
      const data = this.__cacheImpl.getJSON(cacheKey)
      if (data != null) {
        this.__result_promise = Promise.resolve(data)
      }
      if (this.__result_promise != null) {
        return this.__result_promise
      }
      return this
    },
    then () {
      return this.__access('then', arguments)
    },
    catch () {
      return this.__access('catch', arguments)
    },
    finally () {
      return this.__access('finally', arguments)
    },
    __access (methodName, args) {
      if (this.__result_promise != null) {
        return this.__result_promise
      }
      this.__result_promise = axiosInstance[method].apply(axiosInstance, this.__arguments)
      this.__result_promise.then(data => {
        this.__cacheImpl.setJSON(cacheKey, data)
        return data
      })
      return this.__result_promise[methodName].apply(this.__result_promise, args)
    }
  }
}
const methods = ['get', 'post', 'delete', 'put', 'head', 'options', 'patch', 'request']
axiosInstance.cache = function (cacheKey, isLocal = false) {
  if (cacheKey == null) {
    throw Error('Request cache key can not be null.')
  }
  const cacheAxiosInstance = {}
  for (const method of methods) {
    cacheAxiosInstance[method] = function () {
      return buildCachePromise(cacheKey, method, arguments, isLocal ? 'local' : 'session')
    }
  }
  return cacheAxiosInstance
}

export default axiosInstance
