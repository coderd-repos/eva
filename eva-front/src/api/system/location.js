import request from '../../utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/location/page', data, {
    trim: true
  })
}

// 根据父节点查询
export function fetchByParentId (parentId) {
  return request.cache(`location_${parentId}_children`).get(`/system/location/children/${parentId}`)
}

// 创建
export function create (data) {
  return request.post('/system/location/create', data)
}

// 修改
export function updateById (data) {
  return request.post('/system/location/updateById', data)
}

// 修改状态
export function updateStatus (data) {
  return request.post('/system/location/updateStatus', data)
}
