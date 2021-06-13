<template>
  <el-select
    class="data-perm-type-select"
    :class="{select__block: !inline}"
    :value="value"
    :placeholder="placeholder"
    :clearable="clearable"
    :disabled="disabled"
    @change="$emit('change', $event)"
    @input="$emit('input', $event)"
  >
    <el-option v-for="type in filterTypes" :key="type.code" :value="type.code" :label="type.remark"/>
  </el-select>
</template>

<script>
import { fetchTypes } from '@/api/system/dataPermission'
export default {
  name: 'DataPermTypeSelect',
  props: {
    value: {},
    // 模块名称
    module: {},
    placeholder: {
      default: '请选择权限类型'
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
      types: []
    }
  },
  computed: {
    filterTypes () {
      if (this.module == null || this.module === '') {
        return []
      }
      const types = []
      for (const type of this.types) {
        if (type.modules.length === 0 || type.modules.indexOf(this.module) !== -1) {
          types.push(type)
        }
      }
      return types
    }
  },
  created () {
    fetchTypes()
      .cache()
      .then(data => {
        this.types = data
      })
  }
}
</script>
<style lang="scss" scoped>
  .select__block {
    display: block;
  }
</style>
