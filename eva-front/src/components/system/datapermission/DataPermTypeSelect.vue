<template>
  <el-select
    class="data-perm-type-select"
    :class="{select__block: !inline}"
    :value="value"
    :placeholder="placeholder"
    :clearable="clearable"
    @change="$emit('change', $event)"
    @input="$emit('input', $event)"
  >
    <el-option v-for="type in types" :key="type.code" :value="type.code" :label="type.remark"/>
  </el-select>
</template>

<script>
import { fetchTypes } from '@/api/system/dataPermission'
export default {
  name: 'DataPermTypeSelect',
  props: {
    value: {},
    placeholder: {
      default: '请选择权限类型'
    },
    inline: {
      default: true
    },
    clearable: {
      default: false
    }
  },
  data () {
    return {
      types: []
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
