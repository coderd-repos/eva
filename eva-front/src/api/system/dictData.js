import request from '@/utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/dictData/page', data)
}

// 新建
export function create (data) {
  return request.post('/system/dictData/create', data)
}

// 修改
export function updateById (data) {
  return request.post('/system/dictData/updateById', data)
}

// 删除
export function deleteById (id) {
  return request.get(`/system/dictData/delete/${id}`)
}

// 批量删除
export function deleteByIdInBatch (ids) {
  return request.get('/system/dictData/delete/batch', {
    params: {
      ids
    }
  })
}
