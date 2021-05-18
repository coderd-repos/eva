<template>
  <GlobalWindow
    :visible.sync="visible"
    :confirm-working="isWorking"
    width="576px"
    title="配置用户角色"
    @confirm="confirm"
  >
    <p class="tip" v-if="user != null">为用户 <em>{{user.realname}}</em> 配置角色</p>
    <el-transfer
      v-model="selectedIds"
      :titles="['未授权角色', '已授权角色']"
      :props="{
        key: 'id',
        label: 'name'
      }"
      :data="roles">
    </el-transfer>
  </GlobalWindow>
</template>

<script>
import GlobalWindow from '../common/GlobalWindow'
import { createUserRole } from '../../api/system/user'
import { fetchAll as fetchAllRoles } from '../../api/system/role'
export default {
  name: 'RoleConfigWindow',
  components: { GlobalWindow },
  data () {
    return {
      visible: false,
      isWorking: false,
      // 用户
      user: null,
      // 角色列表
      roles: null,
      // 已选中的角色ID
      selectedIds: []
    }
  },
  watch: {
    user () {
      this.selectedIds = this.user.roles.map(r => r.id)
    }
  },
  methods: {
    open (user) {
      fetchAllRoles()
        .then(records => {
          this.roles = records
          this.user = user
          this.visible = true
        })
        .catch(e => {
          this.$message.error(e.message)
        })
    },
    // 确认选择角色
    confirm () {
      if (this.isWorking) {
        return
      }
      this.isWorking = true
      createUserRole({
        userId: this.user.id,
        roleIds: this.selectedIds
      })
        .then(() => {
          this.$message.success('角色配置成功')
          this.visible = false
          this.$emit('success')
        })
        .catch(e => {
          this.$message.error(e.message)
        })
        .finally(() => {
          this.isWorking = false
        })
    },
    // 关闭
    close () {
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style scoped lang="scss">
@import "../../assets/style/variables.scss";
// 角色配置
.global-window {
  .tip {
    margin-bottom: 12px;
    em {
      font-style: normal;
      color: $primary-color;
      font-weight: bold;
    }
  }
}
</style>
