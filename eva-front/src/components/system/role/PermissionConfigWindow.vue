<template>
  <GlobalWindow
    :visible.sync="visible"
    :confirm-working="isWorking"
    width="582px"
    title="配置角色权限"
    @confirm="confirm"
  >
    <p class="tip" v-if="role != null">为角色 <em>{{role.name}}</em> 配置权限</p>
    <p class="tip-warn"><i class="el-icon-warning"></i>提醒：权限配置后需重新登录后生效</p>
    <el-transfer
      ref="permissionTransfer"
      v-model="selectedIds"
      filterable
      :filter-method="filterPermissions"
      :titles="['未授权权限', '已授权权限']"
      :props="{
        key: 'id',
        label: 'name'
      }"
      :data="permissions">
    </el-transfer>
  </GlobalWindow>
</template>

<script>
import GlobalWindow from '@/components/common/GlobalWindow'
import { createRolePermission } from '@/api/system/role'
import { fetchAll } from '@/api/system/permission'
export default {
  name: 'PermissionConfigWindow',
  components: { GlobalWindow },
  data () {
    return {
      visible: false,
      isWorking: false,
      // 角色对象
      role: null,
      // 权限列表
      permissions: [],
      // 已选中的权限ID
      selectedIds: []
    }
  },
  methods: {
    /**
     * @role 角色对象
     */
    open (role) {
      if (this.$refs.permissionTransfer) {
        this.$refs.permissionTransfer.clearQuery('left')
        this.$refs.permissionTransfer.clearQuery('right')
      }
      fetchAll()
        .then(records => {
          this.role = role
          this.permissions = records
          // 如果为固定角色，则固定权限不可更改
          if (this.role.fixed) {
            for (const perm of this.permissions) {
              if (perm.fixed) {
                perm.disabled = true
              }
            }
          }
          this.selectedIds = role.permissions.map(r => r.id)
          this.visible = true
        })
        .catch(e => {
          this.$tip.apiFailed(e)
        })
    },
    // 确认选择权限
    confirm () {
      this.isWorking = true
      createRolePermission({
        roleId: this.role.id,
        permissionIds: this.selectedIds
      })
        .then(() => {
          this.$tip.apiSuccess('权限配置成功，用户重新登录后生效')
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
    // 搜索权限
    filterPermissions (query, item) {
      const lowerCaseQuery = query.toLowerCase()
      return item.code.toLowerCase().indexOf(lowerCaseQuery) > -1 || item.name.toLowerCase().indexOf(lowerCaseQuery) > -1
    }
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/style/variables.scss";
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
