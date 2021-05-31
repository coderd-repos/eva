import request from '../../utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/systemLoginLog/page', data, {
    trim: true
  })
}
