<template>
  <el-cascader :props="props" @change="$emit('change')" :clearable="clearable" @input="$emit('input', $event[$event.length - 1])"></el-cascader>
</template>

<script>
import { fetchByParentId } from '../../api/system/location'

export default {
  name: 'LocationSelect',
  props: {
    value: {},
    level: {
      default: 3
    },
    clearable: {
      default: false
    }
  },
  data () {
    const vm = this
    return {
      props: {
        lazy: true,
        lazyLoad (node, resolve) {
          const { level } = node
          fetchByParentId(level === 0 ? -1 : node.value)
            .then(data => {
              resolve(data.map(item => {
                return {
                  label: item.name,
                  value: item.id,
                  leaf: level >= vm.level - 1
                }
              }))
            })
            .catch(e => {
              vm.$tip.apiFailed(e)
            })
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
