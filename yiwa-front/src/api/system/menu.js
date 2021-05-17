import request from '../../utils/request'

// 查询
export function fetchList (data) {
  return request.post('/system/menu/list', data)
}

// 新建
export function create (data) {
  return request.post('/system/menu/create', data)
}

// 修改
export function updateById (data) {
  return request.post('/system/menu/updateById', data)
}

// 删除
export function deleteById (id) {
  return request.get(`/system/menu/delete/${id}`)
}

// 批量删除
export function deleteByIdInBatch (ids) {
  return request.get('/system/menu/delete/batch', {
    params: {
      ids
    }
  })
}

// 查询菜单树
export function fetchMenuTree () {
  return request.get('/system/menu/tree')
}

// 排序
export function sort (data) {
  return request.post('/system/menu/sort', data)
}
