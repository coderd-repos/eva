<template>
  <div class="home">
    <div class="wrap">
      <h2>欢迎来带伊娃极速开发框架</h2>
      <p>您使用的此套开源框架没有任何版权问题，可学习可商用，请放心使用！</p>
      <p>伊娃致力于打造简洁、合理、高效的开发体验，为此我们将不断升级，感谢您的支持！</p>
      <p style="margin-top: 20px;">项目创立不久，但已跟目前主流的开源框架不相上下，甚至不少部分已超越。</p>
      <p>我们有绝对能力在不久后成为极速开发框架的不二选择。欢迎star。
        <a href="https://gitee.com/coderd-repos/eva" target="_blank">GITEE</a>
        <a href="https://github.com/coderd-repos/eva" target="_blank">GITHUB</a>
      </p>
      <p style="margin-top: 12px;color: #333;">提示：star破 100 出移动端版</p>
      <p style="margin-top: 12px;color: #333;">提示：star破 1000 出微服务版</p>
      <div class="guide">
        <a href="http://eva.adjust-rd.com" target="_blank">前往官网</a>
        <a href="http://eva.adjust-rd.com/server/start.html" target="_blank">开始使用</a>
      </div>
      <img src="@/assets/images/qq.png">
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
  .guide {
    margin: 30px 0 40px 0;
    display: flex;
    justify-content: center;
    a {
      margin-right: 12px;
      padding: 12px 40px;
      border-radius: 30px;
      background: $primary-color;
      color: #fff;
      text-decoration: none;
    }
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
