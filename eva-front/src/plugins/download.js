import fileDownload from 'js-file-download'
import message from './message'

export default function (response) {
  // 当下载接口没有成功返回流时返回的是JSON字符串，此时对json进行解析并提示错误。
  if (response.headers['content-type'] === 'application/json') {
    const blob = new Blob([response.data])
    const fileReader = new FileReader()
    fileReader.readAsText(blob, 'utf-8')
    fileReader.onload = function () {
      message.apiFailed(JSON.parse(fileReader.result))
    }
    return
  }
  // 下载接口在响应头eva-download-filename中存放文件名称
  fileDownload(response.data, decodeURI(response.headers['eva-download-filename']))
}
