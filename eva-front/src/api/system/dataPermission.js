import request from '../../utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/dataPermission/page', data, {
    trim: true
  })
}

// 查询数据权限模块
export function fetchModules () {
  return request.cache('DATA_PERMISSION_MODULES').get('/system/dataPermission/modules')
}

// 查询数据权限类型
export function fetchTypes () {
  return request.cache('DATA_PERMISSION_TYPES').get('/system/dataPermission/types')
}

// 创建
export function create (data) {
  return request.post('/system/dataPermission/create', data)
}

// 修改
export function updateById (data) {
  return request.post('/system/dataPermission/updateById', data)
}

// 修改状态
export function updateStatus (data) {
  return request.post('/system/dataPermission/updateStatus', data)
}

// 删除
export function deleteById (id) {
  return request.get(`/system/dataPermission/delete/${id}`)
}

// 批量删除
export function deleteByIdInBatch (ids) {
  return request.get('/system/dataPermission/delete/batch', {
    params: {
      ids
    }
  })
}
