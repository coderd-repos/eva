<template>
  <div class="home">
    <div class="wrap">
      <h2>这是一套可定制化的极速开发框架</h2>
      <h3>可定制化技术栈，可定制化功能模块！</h3>
      <h2>提供极度舒适的"特殊服务"</h2>
      <h3>文档 & 视频教程全免费，无版权，可学习，可商用！</h3>
      <h3>内置模块发现BUG手摸手给你修</h3>
      <h3>内存式缓存 -> Redis高可用随时升级</h3>
      <h3>强大的可视化 + 命令行代码生成</h3>
      <h3>你若挠头，我便掉发！7*16小时为你解疑答惑</h3>
      <h3></h3>
      <div class="star">
        为了项目可持续发展，项目仓库已变更，欢迎star
        <a href="https://gitee.com/coderd-repos/eva" target="_blank">GITEE</a>
        <a href="https://github.com/coderd-repos/eva" target="_blank">GITHUB</a>
      </div>
      <div class="guide">
        <a href="http://eva.adjust-rd.com" target="_blank">前往官网</a>
        <a href="http://cg.lazy-monkey.com/template/group/308/default" target="_blank">开始定制</a>
      </div>
      <p>抓紧加群，一起唠嗑！</p>
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
    padding-top: 20px;
    img {
      width: 240px;
    }
  }
  h2 {
    font-size: 32px;
    color: #555;
    margin-top: 20px;
    margin-bottom: 12px;
  }
  h3 {
    color: #555;
    line-height: 30px;
    font-size: 20px;
  }
  p {
    line-height: 24px;
    margin: 0;
  }
  .star {
    margin-top: 12px;
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
      opacity: 0.8;
      transition: opacity ease .3s;
      &:hover {
        opacity: 1;
      }
    }
  }
}
</style>
