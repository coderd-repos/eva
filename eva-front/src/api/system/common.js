import request from '@/utils/request'

// 获取图片验证码
export function getCaptcha () {
  return request.get('/common/captcha')
}

// 根据密码登录
export function loginByPassword (data) {
  return request.post('/system/login', data)
}

// 登出
export function logout (data) {
  return request.post('/system/logout', data)
}

// 修改密码
export function updatePwd (data) {
  return request.post('/system/updatePwd', data)
}

// 获取已登录的用户信息
export function getUserInfo () {
  return request.get('/system/getUserInfo')
}
