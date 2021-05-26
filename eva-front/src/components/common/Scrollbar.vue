<template>
  <vue-scroll :ops="options">
    <slot></slot>
  </vue-scroll>
</template>

<script>
import VueScroll from 'vuescroll'
export default {
  name: 'Scrollbar',
  components: { VueScroll },
  data () {
    return {
      options: {
        bar: {
          background: 'rgba(20,20,20,.3)'
        }
      }
    }
  }
}
</script>
