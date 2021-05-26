import request from '@/utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/dict/page', data)
}

// 新建
export function create (data) {
  return request.post('/system/dict/create', data)
}

// 修改
export function updateById (data) {
  return request.post('/system/dict/updateById', data)
}

// 删除
export function deleteById (id) {
  return request.get(`/system/dict/delete/${id}`)
}

// 批量删除
export function deleteByIdInBatch (ids) {
  return request.get('/system/dict/delete/batch', {
    params: {
      ids
    }
  })
}
