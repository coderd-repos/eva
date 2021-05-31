import request from '@/utils/request'

// 查询
export function fetchTree (data) {
  return request.post('/system/menu/treeList', data)
}

// 新建
export function create (data) {
  return request.post('/system/menu/create', data)
}

// 修改
export function updateById (data) {
  return request.post('/system/menu/updateById', data)
}

// 修改状态
export function updateStatus (data) {
  return request.post('/system/menu/updateStatus', data)
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
  return request.get('/system/menu/treeNodes')
}

// 排序
export function sort (data) {
  return request.post('/system/menu/updateSort', data)
}
