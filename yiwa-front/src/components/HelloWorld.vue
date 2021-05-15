<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>
      欢迎使用懒猴子CG构建Vue2.0框架
    </p>
    <el-button>ElementUI按钮</el-button>
    <ul class="techs">
      <li>
        <label>版本</label>
        <p>3.2.0</p>
      </li>
      <li>
        <label>Vue-Router</label>
        <p>4.0.0</p>
      </li>
      <li>
        <label>路由模式</label>
        <p>Hash</p>
      </li>
      <li>
        <label>预编译</label>
        <p>SASS-4.12.0</p>
      </li>
      <li>
        <label>Http库</label>
        <p>axios-0.21.1</p>
      </li>
      <li>
        <label>UI库</label>
        <p>element-ui-2.3.6</p>
      </li>
      <li>
        <label>代码规范检查</label>
        <p>eslint-6.7.2</p>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.techs {
  width: 400px;
  margin: 20px auto 0 auto;
  list-style: none;
  text-align: initial;
  border: 1px dashed #eee;
  li {
    display: flex;
    padding: 16px 0;
    label {
      width: 100px;
      flex-shrink: 0;
      text-align: right;
    }
    p {
      width: 100%;
      margin: 0;
      padding: 0 12px;
    }
  }
}
</style>
