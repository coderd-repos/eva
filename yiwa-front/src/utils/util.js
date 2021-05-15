// 去空
export function trim (data) {
  if (data == null) {
    return null
  }
  if (typeof data === 'string') {
    return data.trim()
  }
  if (data instanceof Array) {
    for (const item of data) {
      trim(item)
    }
  }
  if (typeof data === 'object') {
    for (const key in data) {
      data[key] = trim(data[key])
    }
  }
  return data
}
