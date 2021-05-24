<template>
  <vue-tree-select
    :class="{inline}"
    :placeholder="placeholder"
    :value="value"
    :options="data"
    :clearable="clearable"
    @input="$emit('input', $event)"
  />
</template>

<script>
import VueTreeSelect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  name: 'TreeSelect',
  props: {
    inline: {
      default: false
    },
    value: {},
    placeholder: {
      default: '请选择'
    },
    // 是否可清空
    clearable: {
      default: false
    },
    data: {
      type: Array,
      required: true
    }
  },
  components: { VueTreeSelect }
}
</script>

<style scoped lang="scss">
.inline {
  width: 178px;
}
.vue-treeselect {
  line-height: 30px;
  /deep/ .vue-treeselect__control {
    height: 32px;
    .vue-treeselect__single-value {
      line-height: 30px;
    }
  }
}
</style>
