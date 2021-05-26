<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking.create"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="数据标签" prop="label" required>
        <el-input v-model="form.label" placeholder="请输入数据标签" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="数据值" prop="code" required>
        <el-input v-model="form.code" placeholder="请输入数据值" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="状态" prop="disabled" required class="form-item-status">
        <el-switch v-model="form.disabled" :active-value="false" :inactive-value="true"/>
        <span class="status-text">{{form.disabled | disabledText}}</span>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import BaseOpera from '@/components/base/BaseOpera'
import GlobalWindow from '@/components/common/GlobalWindow'
export default {
  name: 'OperaDictDataWindow',
  extends: BaseOpera,
  components: { GlobalWindow },
  data () {
    return {
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
          this.form.id = null
          this.form.dictId = dictId
        })
        return
      }
      // 编辑
      this.$nextTick(() => {
        for (const key in this.form) {
          this.form[key] = target[key]
        }
      })
    }
  },
  created () {
    this.config({
      api: '/system/dictData'
    })
  }
}
</script>

<style scoped lang="scss">
.form-item-status {
  .status-text {
    color: #999;
    margin-left: 6px;
    font-size: 13px;
    vertical-align: middle;
  }
}
</style>
