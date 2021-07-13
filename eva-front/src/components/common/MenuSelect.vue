<template>
  <TreeSelect
    :placeholder="placeholder"
    :value="value"
    :data="data"
    :append-to-body="appendToBody"
    :clearable="clearable"
    :inline="inline"
    @input="$emit('input', $event)"
  />
</template>

<script>
import TreeSelect from './TreeSelect'
import { fetchTree } from '@/api/system/menu'
export default {
  name: 'MenuSelect',
  components: { TreeSelect },
  props: {
    value: {},
    inline: {
      default: true
    },
    placeholder: {
      default: '请选择菜单'
    },
    // 是否可清空
    clearable: {
      default: false
    },
    appendToBody: {
      default: false
    },
    // 需被排除的部门ID
    excludeId: {}
  },
  data () {
    return {
      data: []
    }
  },
  watch: {
    excludeId () {
      this.fetchData()
    }
  },
  methods: {
    // 获取所有菜单
    fetchData () {
      fetchTree()
        .then(records => {
          this.data = []
          this.__fillData(this.data, records)
        })
        .catch(e => {
          this.$tip.apiFailed(e)
        })
    },
    // 填充菜单树
    __fillData (list, pool) {
      for (const menu of pool) {
        if (menu.id === this.excludeId) {
          continue
        }
        const menuNode = {
          id: menu.id,
          label: menu.name
        }
        list.push(menuNode)
        if (menu.children != null && menu.children.length > 0) {
          menuNode.children = []
          this.__fillData(menuNode.children, menu.children)
          if (menuNode.children.length === 0) {
            menuNode.children = undefined
          }
        }
      }
    }
  },
  created () {
    this.fetchData()
  }
}
</script>
