import request from '@/utils/request'

// 查询列表树
export function fetchTree (data) {
  return request.post('/system/position/tree', data)
}

// 查询列表
export function fetchList (data) {
  return request.post('/system/position/list', data)
}

// 新建
export function create (data) {
  return request.post('/system/position/create', data)
}

// 修改
export function updateById (data) {
  return request.post('/system/position/updateById', data)
}

// 删除
export function deleteById (id) {
  return request.get(`/system/position/delete/${id}`)
}

// 批量删除
export function deleteByIdInBatch (ids) {
  return request.get('/system/position/delete/batch', {
    params: {
      ids
    }
  })
}
