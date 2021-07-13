<!-- 组件详情请参阅官方文档：https://www.vue-treeselect.cn/ -->
<template>
  <vue-tree-select
    :class="{inline}"
    :placeholder="placeholder"
    :value="value"
    :options="data"
    :clearable="clearable"
    :flat="flat"
    :append-to-body="appendToBody"
    :multiple="multiple"
    no-children-text="无记录"
    no-options-text="无记录"
    no-results-text="未匹配到数据"
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
    multiple: {
      default: false
    },
    flat: {
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
    },
    appendToBody: {
      default: false
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
