<template>
  <el-cascader
    v-if="visible"
    :props="props"
    :placeholder="placeholder"
    v-model="value"
    :clearable="clearable"
    @change="$emit('change')"
    @input="handleInput"
  ></el-cascader>
</template>

<script>
import { fetchByParentId } from '@/api/system/location'

export default {
  name: 'LocationSelect',
  props: {
    placeholder: {
      default: '请选择地区'
    },
    level: {
      default: 3
    },
    clearable: {
      default: false
    },
    // 省
    provinceId: {},
    // 市
    cityId: {},
    // 区
    areaId: {}
  },
  data () {
    const vm = this
    return {
      // 是否展示，用于重新初始化cascader
      visible: true,
      // 已选值
      value: [],
      // 组件配置
      props: {
        lazy: true,
        lazyLoad (node, resolve) {
          const { level } = node
          fetchByParentId(level === 0 ? -1 : node.value)
            .cache()
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
  watch: {
    provinceId (newValue) {
      this.value[0] = newValue
      if (this.level === 1) {
        if (newValue == null) {
          this.value = []
        }
        this.__rebuild()
      }
    },
    cityId (newValue) {
      if (this.level >= 2) {
        this.value[1] = newValue
      }
      if (this.level === 2) {
        if (newValue == null) {
          this.value = []
        }
        this.__rebuild()
      }
    },
    areaId (newValue) {
      if (this.level >= 3) {
        this.value[2] = newValue
      }
      if (this.level === 3) {
        if (newValue == null) {
          this.value = []
        }
        this.__rebuild()
      }
    }
  },
  methods: {
    handleInput (values) {
      this.$emit('update:province-id', values[0])
      this.$emit('update:city-id', values[1])
      this.$emit('update:area-id', values[2])
    },
    // 重新初始化cascader
    __rebuild () {
      this.visible = false
      this.$nextTick(() => {
        this.visible = true
      })
    }
  }
}
</script>
