import { Message } from 'element-ui'

export default {
  ...Message,
  // 接口调用成功
  apiSuccess (message) {
    Message.success(message)
  },
  // 接口调用失败
  apiFailed (err) {
    Message.error(err.message)
  }
}
