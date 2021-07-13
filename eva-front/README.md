框架：Eva v2.0
Eva官网：http://eva.adjust-rd.com

# 项目目录说明
- src/assets：静态资源目录
- src/components：自定义组件目录
- src/plugins：插件目录
- src/router：路由配置目录
- src/store：vuex store目录
- src/utils：工具目录
- src/views：页面目录

# 技术栈
- 路由：Vue-Router-3.2.0
- 预编译：SASS-4.12.0
- HTTP库：axios-0.21.1
- UI库：element-ui-2.3.6
- 代码规范检查：eslint-6.7.2

# 代码检查
代码检查规则配置在package.json的eslintConfig对象中

# 路由
路由配置在src/router/index.js中

# 接口代理和调用
接口代理路径配置在vue.config.js文件中，接口调用工具封装在src/utils/request.js中，调用示例如下
```javascript
import request from '@/utils/request'

request.post('/api/myinterface', {
  param1: 'param value'
})
    .then(data => {
      console.log('请求成功，接口返回', data)
    })
    .catch(e => {
      console.log('调用失败', e)
    })
```
