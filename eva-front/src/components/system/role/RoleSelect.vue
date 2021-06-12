<template>
  <el-select
    class="role-select"
    :class="{select__block: !inline}"
    :value="value"
    :placeholder="placeholder"
    :clearable="clearable"
    :disabled="disabled"
    @change="$emit('change', $event)"
    @input="$emit('input', $event)"
  >
    <el-option v-for="role in roles" :key="role.id" :value="role.id" :label="role.name"/>
  </el-select>
</template>

<script>
import { fetchAll } from '@/api/system/role'

export default {
  name: 'RoleSelect',
  props: {
    value: {},
    placeholder: {
      default: '请选择角色'
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
      roles: []
    }
  },
  created () {
    fetchAll()
      .then(data => {
        this.roles = data
      })
  }
}
</script>
<style lang="scss" scoped>
  .select__block {
    display: block;
  }
</style>
