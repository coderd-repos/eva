<template>
  <div class="workbench">
    <div class="wrap">
      <h2>欢迎使用懒猴子CG企业数据管理系统模版</h2>
      <p>您使用的此套项目模版由<em>桂阳阿茄思特网络技术有限公司</em>提供，模版没有任何版权问题，请放心使用！</p>
      <p>我们强烈推荐您使用懒猴子CG极速开发平台，她将帮助您更高效的进行产品研发。</p>
      <a href="http://cg.lazy-monkey.com/template/group/60/default" target="_blank">点击在懒猴子CG中查看此套模版详情</a>
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

<style scoped lang="less">
@import "../assets/style/variable.scss";
.workbench {
  height: 100%;
  text-align: center;
  color: #777;
  .wrap {
    margin-top: 15%;
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
    color: @primary-color;
  }
}
</style>
