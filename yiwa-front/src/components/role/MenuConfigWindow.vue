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
import GlobalWindow from '../common/GlobalWindow'
import { createRoleMenu } from '../../api/system/role'
import { fetchList as fetchMenuList } from '../../api/system/menu'
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
          // 重置disabled为false，避免无法选中
          this.menus.map(r => {
            r.disabled = false
          })
          // 找出叶节点
          role.menus = role.menus.filter(menu => role.menus.findIndex(m => m.parentId === menu.id) === -1)
          // 初始化选中
          this.selectedIds = role.menus.map(r => r.id)
          this.visible = true
        })
        .catch(e => {
          this.$message.error(e.message)
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
          this.$message.success('菜单授权成功')
          this.visible = false
          this.$emit('success')
        })
        .catch(e => {
          this.$message.error(e.message)
        })
        .finally(() => {
          this.isWorking = false
        })
    }
  }
}
</script>

<style scoped lang="scss">
  @import "../../assets/style/variables.scss";
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
