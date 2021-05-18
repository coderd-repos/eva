<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item v-if="form.id == null || form.parentId != null" label="上级部门" prop="parentId" required>
        <TreeSelect v-model="form.parentId" :data="parentDepartmentList"/>
      </el-form-item>
      <el-form-item label="部门名称" prop="name" required>
        <el-input v-model="form.name" placeholder="请输入部门名称"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系电话"></el-input>
      </el-form-item>
      <el-form-item label="部门邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入部门邮箱"></el-input>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import GlobalWindow from '../common/GlobalWindow'
import TreeSelect from '../common/TreeSelect'
import { create, updateById } from '../../api/system/department'
export default {
  name: 'OperaDepartmentWindow',
  components: { TreeSelect, GlobalWindow },
  data () {
    return {
      title: '新建系统权限',
      visible: false,
      isWorking: false,
      // 父部门数据
      parentDepartmentList: [],
      // 表单数据
      form: {
        id: null,
        parentId: null,
        name: '',
        phone: '',
        email: ''
      },
      // 验证规则
      rules: {
        name: [
          { required: true, message: '请输入部门名称' }
        ]
      }
    }
  },
  methods: {
    /**
     * @title 窗口标题
     * @target 编辑的部门对象
     * @parent 新建时的上级部门对象
     * @departmentList 部门列表
     */
    open (title, target, parent, departmentList) {
      this.title = title
      this.visible = true
      // 填充上级部门数据
      this.parentDepartmentList = []
      this.__fillParentDepartmentList(this.parentDepartmentList, departmentList, target == null ? null : target.id)
      // 新建
      if (target == null) {
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.form.id = null
          this.form.parentId = parent == null ? departmentList[0].id : parent.id
        })
        return
      }
      // 编辑
      this.$nextTick(() => {
        for (const key in this.form) {
          this.form[key] = target[key]
        }
      })
    },
    // 确认新建/修改
    confirm () {
      if (this.form.id == null || this.form.id === '') {
        this.__confirmCreate()
        return
      }
      this.__confirmEdit()
    },
    // 确定新建
    __confirmCreate () {
      this.$refs.operaTableDataForm.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用新建接口
        this.isWorking = true
        create(this.form)
          .then(() => {
            this.visible = false
            this.$message.success('新建成功')
            this.$emit('create-success')
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking = false
          })
      })
    },
    // 确认修改
    __confirmEdit () {
      this.$refs.operaTableDataForm.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用新建接口
        this.isWorking = true
        updateById(this.form)
          .then(() => {
            this.visible = false
            this.$message.success('修改成功')
            this.$emit('edit-success')
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking = false
          })
      })
    },
    // 获取上级部门列表
    __fillParentDepartmentList (list, pool, excludeId) {
      for (const dept of pool) {
        if (dept.id === excludeId) {
          continue
        }
        const deptNode = {
          id: dept.id,
          label: dept.name
        }
        list.push(deptNode)
        if (dept.children != null && dept.children.length > 0) {
          deptNode.children = []
          this.__fillParentDepartmentList(deptNode.children, dept.children, excludeId)
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
