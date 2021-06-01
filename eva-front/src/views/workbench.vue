<template>
  <div class="workbench">
    <div class="wrap">
      <h2>欢迎使用伊娃后台管理系统(Beta/公测中)</h2>
      <p>您使用的此套开源项目模版没有任何版权问题，可学习可商用，请放心使用！</p>
      <p>伊娃致力于打造简洁、合理、高效的开发体验，为此我们将不断升级，感谢您的支持！</p>
      <img src="@/assets/images/qq.jpeg">
      <p>你可以扫码加入群聊以获得技术支持</p>
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
.workbench {
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
    line-height: 30px;
  }
  em,a {
    font-style: normal;
    font-weight: bold;
    margin: 0 3px;
    color: $primary-color;
  }
}
</style>
