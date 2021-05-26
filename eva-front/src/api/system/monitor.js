import request from '@/utils/request'

// 获取系统信息
export function getSystemInfo () {
  return request.get('/system/monitor/getSystemInfo')
}
