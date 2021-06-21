import request from '../../utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/loginLog/page', data, {
    trim: true
  })
}

// 导出Excel
export function exportExcel (data) {
  return request.post('/system/loginLog/exportExcel', data, {
    download: true,
    trim: true
  })
}
