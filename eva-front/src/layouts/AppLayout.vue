<template>
  <el-container class="app-layout">
    <el-aside :class="{collapse:menuData.collapse}">
      <Menu/>
    </el-aside>
    <el-main>
      <header>
        <AppHeader/>
      </header>
      <main>
        <transition name="fade">
          <router-view></router-view>
        </transition>
      </main>
    </el-main>
  </el-container>
</template>

<script>
import { mapState } from 'vuex'
import Header from '@/components/common/Header'
import Menu from '@/components/common/Menu'
export default {
  name: 'DefaultLayout',
  components: { AppHeader: Header, Menu },
  computed: {
    ...mapState(['menuData'])
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/style/variables.scss";
.el-container {
  background: #F7F8F9;
  height: 100%;
  display: flex;
  overflow: hidden;
  // 左边菜单
  .el-aside {
    width: $menu-width !important;
    flex-shrink: 0;
    height: 100%;
    overflow-y: auto;
    background: $primary-color;
    color: #fff;
    transition: width ease .3s;
    &.collapse {
      width: 64px !important;
    }
  }
  // 右边内容
  .el-main {
    width: 100%;
    height: 100%;
    padding: 0;
    position: relative;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    & > header {
      height: $header-height;
      flex-shrink: 0;
    }
    & > main {
      height: 100%;
      overflow-y: auto;
    }
  }
}
// 页面过渡动画
.fade-enter-active, .fade-leave-active {
  transition: all .5s;
  opacity: 1;
  position: absolute;
  width: 100%;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  transform: translateY(200px);
  opacity: 0;
  transition: all .5s;
  position: absolute;
}
</style>
