// 验证手机号码
export function checkMobile (rule, value, callback) {
  if (value == null || value.trim() === '') {
    callback()
    return
  }
  if (!/^1\d{10}$/.test(value)) {
    callback(new Error('手机号码格式不正确'))
    return
  }
  callback()
}

// 验证邮箱
export function checkEmail (rule, value, callback) {
  if (value == null || value.trim() === '') {
    callback()
    return
  }
  if (!/^\S+@\S+\.\S+$/.test(value)) {
    callback(new Error('邮箱格式不正确'))
    return
  }
  callback()
}
