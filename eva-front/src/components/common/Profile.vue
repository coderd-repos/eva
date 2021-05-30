<template>
  <div v-if="containRoles(roles) && containPermissions(permissions)">
    <slot></slot>
  </div>
  <div v-else class="not-allow-wrap">
    <slot name="not-allow"><NotAllow/></slot>
  </div>
</template>

<script>
import BasePage from '@/components/base/BasePage'
import NotAllow from './NotAllow'
export default {
  name: 'Profile',
  components: { NotAllow },
  extends: BasePage,
  props: {
    permissions: {
      type: Array
    },
    roles: {
      type: Array
    }
  }
}
</script>

<style scoped lang="scss">
.not-allow-wrap {
  height: 100%;
  padding: 10px 16px;
  box-sizing: border-box;
}
</style>
