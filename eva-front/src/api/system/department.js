import request from '@/utils/request'

// 查询
export function fetchTree () {
  return request.post('/system/department/tree')
}

// 查询部门用户
export function fetchUserList (data) {
  return request.post('/system/department/users', data)
}

// 新建
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
