<template>
  <el-menu-item v-if="menu.children == null || menu.children.length == 0" :key="menu.index" :index="menu.index">
    <i :class="menu.icon"></i>
    <span slot="title">{{menu.label}}</span>
  </el-menu-item>
  <el-submenu v-else :index="menu.index">
    <template slot="title">
      <i :class="menu.icon"></i>
      <span slot="title">{{menu.label}}</span>
    </template>
    <MenuItems v-for="child in menu.children" :menu="child" :key="child.index"/>
  </el-submenu>
</template>

<script>
export default {
  name: 'MenuItems',
  props: {
    menu: {
      type: Object,
      required: true
    }
  }
}
</script>
