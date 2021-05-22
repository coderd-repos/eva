<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="上级岗位" prop="parentId">
        <PositionSelect placeholder="请选择上级岗位" v-model="form.parentId" :exclude-id="excludePositionId" clearable/>
      </el-form-item>
      <el-form-item label="岗位编码" prop="code" required>
        <el-input v-model="form.code" v-trim maxlength="50" placeholder="请输入岗位编码"/>
      </el-form-item>
      <el-form-item label="岗位名称" prop="name" required>
        <el-input v-model="form.name" v-trim maxlength="50" placeholder="请输入岗位名称"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import GlobalWindow from '../common/GlobalWindow'
import PositionSelect from '../common/PositionSelect'
import { create, updateById } from '../../api/system/position'
export default {
  name: 'OperaPositionWindow',
  components: { PositionSelect, GlobalWindow },
  data () {
    return {
      title: '',
      visible: false,
      isWorking: false,
      // 需排除选择的岗位ID
      excludePositionId: null,
      // 表单数据
      form: {
        id: null,
        parentId: null,
        code: '',
        name: ''
      },
      // 验证规则
      rules: {
        code: [
          { required: true, message: '请输入岗位编码' }
        ],
        name: [
          { required: true, message: '请输入岗位名称' }
        ]
      }
    }
  },
  methods: {
    /**
     * @title 窗口标题
     * @target 编辑的岗位对象
     * @parent 新建时的上级岗位对象
     * @positionList 岗位列表
     */
    open (title, target, parent) {
      this.title = title
      this.visible = true
      // 新建
      if (target == null) {
        this.excludePositionId = null
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.form.id = null
          this.form.parentId = parent == null ? null : parent.id
        })
        return
      }
      // 编辑
      this.$nextTick(() => {
        this.excludePositionId = target.id
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
