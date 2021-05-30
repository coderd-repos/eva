<template>
  <div class="not-allow">
    <slot>
      <div class="content">
        <img src="../../assets/images/not-allow.png">
        <h2>无权访问</h2>
        <p>如您需要访问该页面，请联系系统管理员</p>
      </div>
    </slot>
  </div>
</template>

<script>
export default {
  name: 'NotAllow'
}
</script>

<style scoped lang="scss">
.not-allow {
  height: 100%;
  background: #fff;
  box-sizing: border-box;
  padding-top: 160px;
  .content {
    height: 200px;
    text-align: center;
    h2 {
      font-size: 18px;
      font-weight: normal;
      margin-top: 8px;
    }
    p {
      font-size: 13px;
      color: #999;
      margin: 6px 0;
    }
  }
}
</style>
