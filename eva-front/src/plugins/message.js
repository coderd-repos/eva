import { Message } from 'element-ui'

export default {
  ...Message,
  // 接口调用成功
  apiSuccess (message) {
    Message.success(message)
  },
  // 接口调用失败
  apiFailed (err) {
    // 下载接口返回的是ArrayBuffer，此时需要解析为JSON并提示错误消息。（下载接口出现业务失败的情况，例如无权限等）
    if (err instanceof ArrayBuffer) {
      const blob = new Blob([err])
      const fileReader = new FileReader()
      fileReader.readAsText(blob, 'utf-8')
      fileReader.onload = function () {
        Message.error(JSON.parse(fileReader.result).message)
      }
      return
    }
    Message.error(err.message)
  }
}
