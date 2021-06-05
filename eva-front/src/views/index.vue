<template>
  <div class="home">
    <div class="wrap">
      <h2>欢迎使用伊娃后台管理系统</h2>
      <p>您使用的此套开源项目模版没有任何版权问题，可学习可商用，请放心使用！</p>
      <p>伊娃致力于打造简洁、合理、高效的开发体验，为此我们将不断升级，感谢您的支持！</p>
      <div class="start-up"><a href="http://cg.lazy-monkey.com/template/group/308/default" target="_blank">立即免费定制代码</a></div>
      <img src="@/assets/images/qq.jpeg">
      <p>你可以扫码加入群聊以获得技术支持</p>
      <div class="award">
        <h4>买台好点儿的服务器吧</h4>
        <img src="@/assets/images/alipay.jpeg">
        <img src="@/assets/images/wxpay.jpeg">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Workbench',
  data () {
    return {}
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/style/variables.scss";
.home {
  text-align: center;
  color: #777;
  .wrap {
    margin-top: 80px;
    img {
      width: 240px;
    }
  }
  h2 {
    font-size: 32px;
    color: #555;
    margin-bottom: 20px;
  }
  p {
    line-height: 24px;
    margin: 0;
  }
  .start-up {
    margin-top: 8px;
  }
  em,a {
    font-style: normal;
    font-weight: bold;
    margin: 0 3px;
    color: $primary-color;
  }
  .award {
    position: absolute;
    right: 20px;
    bottom: 60px;
    display: flex;
    flex-direction: column;
    text-align: left;
    padding: 12px;
    border: 1px solid #eee;
    box-shadow: -1px 1px 10px #ccc;
    h4 {
      font-weight: bold;
      margin-bottom: 8px;
    }
    img {
      width: 160px;
      margin-bottom: 20px;
      opacity: 0.6;
      transition: opacity ease .3s;
      &:hover {
        opacity: 1;
      }
    }
  }
}
</style>
