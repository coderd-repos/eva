<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="上级岗位" prop="parentId">
        <PositionSelect v-if="visible" v-model="form.parentId" placeholder="请选择上级岗位" :exclude-id="excludePositionId" clearable :inline="false"/>
      </el-form-item>
      <el-form-item label="岗位编码" prop="code" required>
        <el-input v-model="form.code" placeholder="请输入岗位编码" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="岗位名称" prop="name" required>
        <el-input v-model="form.name" placeholder="请输入岗位名称" v-trim maxlength="50"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import BaseOpera from '@/components/base/BaseOpera'
import GlobalWindow from '@/components/common/GlobalWindow'
import PositionSelect from '@/components/common/PositionSelect'
export default {
  name: 'OperaPositionWindow',
  extends: BaseOpera,
  components: { PositionSelect, GlobalWindow },
  data () {
    return {
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
    }
  },
  created () {
    this.config({
      api: '/system/position'
    })
  }
}
</script>
