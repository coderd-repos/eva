import request from '../../utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/department/page', data, {
    trim: true
  })
}

// 创建
export function create (data) {
  return request.post('/system/department/create', data)
}

// 修改
export function updateById (data) {
  return request.post('/system/department/updateById', data)
}

// 删除
export function deleteById (id) {
  return request.get(`/system/department/delete/${id}`)
}

// 批量删除
export function deleteByIdInBatch (ids) {
  return request.get('/system/department/delete/batch', {
    params: {
      ids
    }
  })
}
