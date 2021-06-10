<template>
  <el-cascader
    :props="props"
    :placeholder="placeholder"
    :value="value"
    :clearable="clearable"
    @change="$emit('change')"
    @input="handleInput"
  ></el-cascader>
</template>

<script>
import { fetchByParentId } from '../../api/system/location'

export default {
  name: 'LocationSelect',
  props: {
    value: {},
    placeholder: {
      default: '请选择地区'
    },
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
  },
  methods: {
    handleInput (values) {
      this.$emit('update:province-id', values[0])
      this.$emit('update:city-id', values[1])
      this.$emit('update:area-id', values[2])
    }
  }
}
</script>

<style scoped>

</style>
