<template>
  <GlobalWindow
    class="menu-config-dialog"
    :visible.sync="visible"
    :confirm-working="isWorking"
    width="576px"
    title="授权菜单"
    @confirm="confirm"
  >
    <p class="tip" v-if="role != null">为角色 <em>{{role.name}}</em> 配置可访问的菜单</p>
    <el-tree
      ref="menuTree"
      :data="menus"
      show-checkbox
      node-key="id"
      default-expand-all
      :default-checked-keys="selectedIds"
      :expand-on-click-node="false"
      :check-on-click-node="true"
      :props="{children: 'children',label: 'name'}">
    </el-tree>
  </GlobalWindow>
</template>

<script>
import GlobalWindow from '@/components/common/GlobalWindow'
import { createRoleMenu } from '@/api/system/role'
import { fetchTree as fetchMenuList } from '@/api/system/menu'
export default {
  name: 'MenuConfigWindow',
  components: { GlobalWindow },
  data () {
    return {
      visible: false,
      isWorking: false,
      // 角色
      role: null,
      // 所有菜单
      menus: [],
      // 已选中的菜单
      selectedIds: []
    }
  },
  methods: {
    /**
     * @role 角色对象
     */
    open (role) {
      fetchMenuList({})
        .then(records => {
          this.role = role
          this.menus = records
          // 如果为固定角色，则固定菜单不可更改
          this.__resetDisabled(this.menus, this.role)
          // 找出叶节点
          role.menus = role.menus.filter(menu => role.menus.findIndex(m => m.parentId === menu.id) === -1)
          // 初始化选中
          this.selectedIds = role.menus.map(r => r.id)
          this.visible = true
        })
        .catch(e => {
          this.$tip.apiFailed(e)
        })
    },
    // 确认选择菜单
    confirm () {
      const selectedMenus = this.$refs.menuTree.getCheckedNodes(false, true)
      this.isWorking = true
      createRoleMenu({
        roleId: this.role.id,
        menuIds: selectedMenus.map(menu => menu.id)
      })
        .then(() => {
          this.$tip.apiSuccess('菜单授权成功')
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
    // 重置disabled
    __resetDisabled (menus, role) {
      if (menus == null || menus.length === 0) {
        return
      }
      for (const menu of menus) {
        menu.disabled = false
        if (role.fixed && menu.fixed) {
          menu.disabled = true
        }
        this.__resetDisabled(menu.children, role)
      }
    }
  }
}
</script>

<style scoped lang="scss">
  @import "@/assets/style/variables.scss";
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
