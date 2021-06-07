<template>
  <GlobalWindow
    :visible.sync="visible"
    :confirm-working="isWorking"
    width="582px"
    title="配置用户角色"
    @confirm="confirm"
  >
    <p class="tip" v-if="user != null">为用户 <em>{{user.realname}}</em> 配置角色</p>
    <p class="tip-warn"><i class="el-icon-warning"></i>提醒：角色配置后需重新登录后生效</p>
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
import BasePage from '@/components/base/BasePage'
import GlobalWindow from '@/components/common/GlobalWindow'
import { createUserRole } from '@/api/system/user'
import { fetchAll as fetchAllRoles } from '@/api/system/role'
export default {
  name: 'RoleConfigWindow',
  extends: BasePage,
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
  methods: {
    open (user) {
      fetchAllRoles()
        .then(records => {
          this.roles = records
          this.user = user
          // 如果为固定用户，则固定角色不可更改
          if (this.user.fixed) {
            for (const role of this.roles) {
              if (role.fixed) {
                role.disabled = true
              }
            }
          }
          // 如果当前用户为非超级管理员用户，则不允许授权超级管理员角色
          if (!this.isAdmin) {
            for (const role of this.roles) {
              if (role.code === this.adminCode) {
                role.disabled = true
              }
            }
          }
          this.selectedIds = this.user.roles.map(r => r.id)
          this.visible = true
        })
        .catch(e => {
          this.$tip.apiFailed(e)
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
          this.$tip.apiSuccess('角色配置成功，用户重新登录后生效')
          this.visible = false
          this.$emit('success')
        })
        .catch(e => {
          this.$tip.apiFailed(e)
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
@import "@/assets/style/variables.scss";
// 角色配置
.global-window {
  .tip {
    em {
      font-style: normal;
      color: $primary-color;
      font-weight: bold;
    }
  }
  .tip-warn {
    margin: 4px 0 12px 0;
    font-size: 12px;
    color: #999;
    i {
      color: orange;
      margin-right: 4px;
      font-size: 14px;
      position: relative;
      top: 1px;
    }
  }
}
</style>
