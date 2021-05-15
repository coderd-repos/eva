<template>
  <router-view/>
</template>

<script>
import {mapState, mapMutations} from 'vuex'
import {fetchMenuTree} from './api/system/systemMenu'
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
