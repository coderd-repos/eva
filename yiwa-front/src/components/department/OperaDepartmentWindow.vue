<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item v-if="form.id == null || form.parentId != null" label="上级部门" prop="parentId" required>
        <DepartmentSelect placeholder="请选择上级部门" v-model="form.parentId" :exclude-id="excludeDeptId" :inline="false" :default-first="true"/>
      </el-form-item>
      <el-form-item label="部门编码" prop="code" required>
        <el-input v-model="form.code" placeholder="请输入部门编码" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="部门名称" prop="name" required>
        <el-input v-model="form.name" placeholder="请输入部门名称" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入联系电话" v-trim maxlength="11"/>
      </el-form-item>
      <el-form-item label="部门邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入部门邮箱" v-trim maxlength="200"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import GlobalWindow from '../common/GlobalWindow'
import { checkMobile, checkEmail } from '../../utils/form'
import { create, updateById } from '../../api/system/department'
import DepartmentSelect from '../common/DepartmentSelect'
export default {
  name: 'OperaDepartmentWindow',
  components: { DepartmentSelect, GlobalWindow },
  data () {
    return {
      title: '新建系统权限',
      visible: false,
      isWorking: false,
      // 需排除选择的部门ID
      excludeDeptId: null,
      // 表单数据
      form: {
        id: null,
        parentId: null,
        code: '',
        name: '',
        phone: '',
        email: ''
      },
      // 验证规则
      rules: {
        code: [
          { required: true, message: '请输入部门编码' }
        ],
        name: [
          { required: true, message: '请输入部门名称' }
        ],
        phone: [
          { validator: checkMobile }
        ],
        email: [
          { validator: checkEmail }
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
    open (title, target, parent) {
      this.title = title
      this.visible = true
      // 新建
      if (target == null) {
        this.excludeDeptId = null
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.form.id = null
          this.form.parentId = parent == null ? null : parent.id
        })
        return
      }
      // 编辑
      this.$nextTick(() => {
        this.excludeDeptId = target.id
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
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用新建接口
        this.isWorking = true
        create(this.form)
          .then(() => {
            this.visible = false
            this.$message.success('新建成功')
            this.$emit('success')
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
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return
        }
        // 调用新建接口
        this.isWorking = true
        updateById(this.form)
          .then(() => {
            this.visible = false
            this.$message.success('修改成功')
            this.$emit('success')
          })
          .catch(e => {
            this.$message.error(e.message)
          })
          .finally(() => {
            this.isWorking = false
          })
      })
    }
  }
}
</script>
