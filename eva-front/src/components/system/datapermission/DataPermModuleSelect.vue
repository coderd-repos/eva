<template>
  <el-select
    class="data-perm-module-select"
    :class="{select__block: !inline}"
    :value="value"
    :placeholder="placeholder"
    :clearable="clearable"
    :disabled="disabled"
    @change="$emit('change', $event)"
    @input="$emit('input', $event)"
  >
    <el-option v-for="module in modules" :key="module.businessCode" :value="module.businessCode" :label="module.moduleName"/>
  </el-select>
</template>

<script>
import { fetchModules } from '@/api/system/dataPermission'
export default {
  name: 'DataPermModuleSelect',
  props: {
    value: {},
    placeholder: {
      default: '请选择权限模块'
    },
    inline: {
      default: true
    },
    disabled: {},
    clearable: {
      default: false
    }
  },
  data () {
    return {
      modules: []
    }
  },
  created () {
    fetchModules()
      .cache()
      .then(data => {
        this.modules = data
      })
  }
}
</script>
<style lang="scss" scoped>
  .select__block {
    display: block;
  }
</style>
