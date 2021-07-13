import fileDownload from 'js-file-download'
import message from './message'

export default function (response, decode = true, mime = 'application/octet-stream') {
  // 当下载接口没有成功返回流并且接口返回的是JSON时需要对响应流进行解析并提示错误。（处理下载接口出现未知异常的情况）
  if (response.headers['content-type'] === 'application/json') {
    const blob = new Blob([response.data])
    const fileReader = new FileReader()
    fileReader.readAsText(blob, 'utf-8')
    fileReader.onload = function () {
      message.apiFailed(JSON.parse(fileReader.result))
    }
    return
  }
  if (response.headers['content-length'] === '0') {
    message.error('无法下载文件，可能因为数据处理错误导致文件大小为0B')
    return
  }
  // 下载接口在响应头eva-download-filename中存放文件名称，接口的返回的文件名称需采用url encode的方式进行编码
  fileDownload(response.data, decode ? decodeURI(response.headers['eva-download-filename']) : response.headers['eva-download-filename'], mime)
}
