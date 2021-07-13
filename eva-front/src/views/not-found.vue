<template>
  <div class="error-404">
    <div>
      <div class="left">
        <img src="../assets/images/404.png">
        <h1>找不到网页</h1>
        <p></p>
        <div class="opera">
          <el-button @click="$router.back()">返回</el-button>
          <el-button type="primary" @click="$router.push('/index')">前往首页</el-button>
        </div>
      </div>
      <div class="right">
        <h2>你可以尝试以下操作</h2>
        <p>1. 如果是手动更改的网页地址，请检查网页地址是否正确</p>
        <p>2. 点击【返回】按钮刷新页面后重试</p>
        <p>3. 联系系统管理员</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NotFound'
}
</script>

<style scoped lang="scss">
.error-404 {
  height: 100%;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  & > div {
    margin-top: -100px;
    display: flex;
  }
  .left {
    h1 {
      font-size: 26px;
    }
    .opera {
      margin-top: 30px;
    }
  }
  .right {
    text-align: left;
    margin-top: 16px;
    margin-left: 40px;
    padding: 30px 50px;
    border-radius: 16px;
    background: url("../assets/images/404-tip.png") no-repeat;
    background-size: 100% 100%;
    box-sizing: border-box;
    h2 {
      font-size: 16px;
      margin-bottom: 16px;
    }
    p {
      margin: 0;
      padding: 0;
      font-size: 14px;
      line-height: 26px;
    }
  }
}
</style>
