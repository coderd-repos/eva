<template>
  <div class="value">
    <i class="el-icon-loading" v-if="data == null"></i>
    <slot v-else>{{getValue()}}{{suffix}}</slot>
  </div>
</template>

<script>
export default {
  name: 'Value',
  props: {
    data: {
      type: Object
    },
    prop: {
      type: String
    },
    suffix: {
      type: String
    },
    handler: {
      type: Function
    }
  },
  methods: {
    getValue () {
      if (this.data == null) {
        return ''
      }
      if (this.prop == null) {
        return this.data
      }
      const props = this.prop.split('.')
      let i = 0
      let value = this.data
      while (i < props.length) {
        value = value[props[i]]
        i++
      }
      if (this.handler == null) {
        return value
      }
      return this.handler(value)
    }
  }
}
</script>

<style scoped lang="scss">
.value {
  word-break: break-all;
  .el-icon-loading {
    font-size: 16px;
    color: #999;
    position: relative;
    top: 1px;
  }
}
</style>
