<template>
  <el-dialog
    class="global-dialog"
    :title="title"
    status-icon
    :visible="visible"
    :width="width"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    @close="close"
  >
    <div class="body">
      <slot></slot>
    </div>
    <div slot="footer" class="dialog__footer">
      <el-button @click="$emit('confirm')" :loading="confirmWorking" type="primary">确定</el-button>
      <el-button @click="close">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'GlobalDialog',
  props: {
    width: {
      type: String,
      default: '36%'
    },
    // 确认按钮loading状态
    confirmWorking: {
      type: Boolean,
      default: false
    },
    // 标题
    title: {
      type: String,
      default: ''
    },
    // 是否展示Dialog
    visible: {
      type: Boolean,
      required: true
    }
  },
  methods: {
    close () {
      this.$emit('update:visible', false)
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/style/variable";
// 输入框高度
@input-height: 32px;
.global-dialog {
  // 头部
  /deep/ .el-dialog__header {
    border-bottom: 1px solid #eee;
  }
  // 内容部分
  /deep/ .el-dialog__body {
    // 表单
    .el-form-item {
      margin-bottom: 18px;
      &:last-of-type {
        margin-bottom: 0;
      }
    }
  }
  // 尾部按钮
  .el-dialog__footer {
    .el-button {
      height: @input-height;
      width: 76px;
      padding: 0;
    }
  }
}
</style>
