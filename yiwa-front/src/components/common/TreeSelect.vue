<template>
  <vue-tree-select :placeholder="placeholder" :value="value" :options="data" @input="$emit('input', $event)" :clearable="clearable"/>
</template>

<script>
import VueTreeSelect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  name: 'TreeSelect',
  props: {
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

<style scoped>

</style>
