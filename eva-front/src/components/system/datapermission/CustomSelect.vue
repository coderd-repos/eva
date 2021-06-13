<template>
  <component :is="component" :value="values" :inline="false" @input="handleInput" multiple/>
</template>

<script>
export default {
  name: 'CustomSelect',
  props: {
    value: {},
    businessCode: {
      type: String,
      required: true
    }
  },
  computed: {
    // vuetreeselect值类型匹配（解决编辑时无法删除已有值的BUG）
    values () {
      if (this.businessCode === 'DEPARTMENT' || this.businessCode === 'POSITION') {
        const values = []
        for (const id of this.value) {
          values.push(parseInt(id))
        }
        return values
      }
      return this.value
    },
    component () {
      // 部门选择器
      if (this.businessCode === 'DEPARTMENT') {
        return () => import('@/components/common/DepartmentSelect')
      }
      // 岗位选择器
      if (this.businessCode === 'POSITION') {
        return () => import('@/components/common/PositionSelect')
      }
      return null
    }
  },
  methods: {
    handleInput (value) {
      this.$emit('input', value)
      this.$emit('change', value)
    }
  }
}
</script>

<style scoped>

</style>
