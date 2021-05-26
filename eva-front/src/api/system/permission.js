import request from '@/utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/permission/page', data)
}

// 查询所有
export function fetchAll () {
  return request.get('/system/permission/all')
}

// 新建
export function create (data) {
  return request.post('/system/permission/create', data, {
    trim: true
  })
}

// 修改
export function updateById (data) {
  return request.post('/system/permission/updateById', data, {
    trim: true
  })
}

// 删除
export function deleteById (id) {
  return request.get(`/system/permission/delete/${id}`)
}

// 批量删除
export function deleteByIdInBatch (ids) {
  return request.get('/system/permission/delete/batch', {
    params: {
      ids
    }
  })
}
