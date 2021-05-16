<template>
  <vue-tree-select :value="value" :options="data" @input="$emit('input', $event)"/>
</template>

<script>
import VueTreeSelect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  name: 'TreeSelect',
  props: {
    value: {},
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
