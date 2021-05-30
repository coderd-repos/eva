import request from '../../utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/loginLog/page', data, {
    trim: true
  })
}
