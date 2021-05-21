<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking.create"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="数据标签" prop="label" required>
        <el-input v-model="form.label" v-trim maxlength="50" placeholder="请输入数据标签"/>
      </el-form-item>
      <el-form-item label="数据值" prop="code" required>
        <el-input v-model="form.code" v-trim maxlength="50" placeholder="请输入数据值"/>
      </el-form-item>
      <el-form-item label="状态" prop="code" required>
        <el-switch v-model="form.disabled" :active-value="false" :inactive-value="true"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import GlobalWindow from '../common/GlobalWindow'
import { create, updateById } from '../../api/system/dictData'
export default {
  name: 'OperaDictDataWindow',
  components: { GlobalWindow },
  data () {
    return {
      visible: false,
      isWorking: false,
      title: '',
      // 表单数据
      form: {
        id: null,
        dictId: null,
        code: '',
        label: '',
        disabled: false
      },
      // 验证规则
      rules: {
        label: [
          { required: true, message: '请输入数据标签' }
        ],
        code: [
          { required: true, message: '请输入数据值' }
        ]
      }
    }
  },
  methods: {
    /**
     * @title 窗口标题
     * @dict 所属字典ID
     * @target 编辑的字典数据对象
     */
    open (title, dictId, target) {
      this.title = title
      this.visible = true
      // 新建
      if (target == null) {
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.form.dictId = dictId
          this.form.id = null
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

<style scoped>

</style>
