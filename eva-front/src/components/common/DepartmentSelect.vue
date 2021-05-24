<template>
  <TreeSelect
    :placeholder="placeholder"
    :value="value"
    :data="data"
    :clearable="clearable"
    :inline="inline"
    @input="$emit('input', $event)"
  />
</template>

<script>
import TreeSelect from './TreeSelect'
import { fetchTree } from '../../api/system/department'
export default {
  name: 'DepartmentSelect',
  components: { TreeSelect },
  props: {
    value: {},
    inline: {
      default: true
    },
    placeholder: {
      default: '请选择部门'
    },
    // 是否可清空
    clearable: {
      default: false
    },
    // 当没有选中时是否默认选中第一条
    defaultFirst: {
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
    // 获取所有部门
    fetchData () {
      fetchTree()
        .then(records => {
          this.data = []
          this.__fillData(this.data, records)
          if (this.defaultFirst && this.value == null) {
            this.$emit('input', this.data[0].id)
          }
        })
        .catch(e => {
          this.$message.error(e.message)
        })
    },
    // 填充部门树
    __fillData (list, pool) {
      for (const dept of pool) {
        if (dept.id === this.excludeId) {
          continue
        }
        const deptNode = {
          id: dept.id,
          label: dept.name
        }
        list.push(deptNode)
        if (dept.children != null && dept.children.length > 0) {
          deptNode.children = []
          this.__fillData(deptNode.children, dept.children)
          if (deptNode.children.length === 0) {
            deptNode.children = undefined
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
