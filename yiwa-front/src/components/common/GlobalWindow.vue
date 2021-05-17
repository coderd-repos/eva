<template>
  <el-drawer
    class="global-window"
    title="title"
    :visible="visible"
    :with-header="true"
    :size="width"
    :close-on-press-escape="false"
    :wrapper-closable="false"
    :append-to-body="true"
    @close="close"
  >
    <div slot="title" class="window__header">
      <span class="header__btn-back" @click="close"><i class="el-icon-arrow-left"></i></span>{{title}}
    </div>
    <div class="window__body">
      <slot></slot>
    </div>
    <div v-if="withFooter" class="window__footer">
      <el-button @click="confirm" :loading="confirmWorking" type="primary">确定</el-button>
      <el-button @click="close">取消</el-button>
    </div>
  </el-drawer>
</template>

<script>
export default {
  name: 'GlobalWindow',
  props: {
    width: {
      type: String,
      default: '36%'
    },
    // 是否包含底部操作
    withFooter: {
      type: Boolean,
      default: true
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
    confirm () {
      this.$emit('confirm')
    },
    close () {
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style scoped lang="scss">
@import "../../assets/style/variables.scss";
// 输入框高度
$input-height: 32px;
.global-window {
  // 头部标题
  /deep/ .el-drawer__header {
    padding: 0 10px 0 0;
    line-height: 40px;
    border-bottom: 1px solid #eee;
    // 返回按钮
    .header__btn-back {
      display: inline-block;
      width: 30px;
      background: $primary-color;
      color: #fff;
      text-align: center;
      margin-right: 12px;
      border-right: 1px solid #eee;
    }
    .el-drawer__close-btn:focus {
      outline: none;
    }
  }
  // 主体
  /deep/ .el-drawer__body {
    display: flex;
    flex-direction: column;
    position: absolute;
    top: 40px;
    bottom: 0;
    width: 100%;
    // 内容
    .window__body {
      height: 100%;
      overflow-y: auto;
      padding: 12px 16px;
      // 标签
      .el-form-item__label {
        float: none;
      }
    }
    // 尾部
    .window__footer {
      user-select: none;
      border-top: 1px solid #eee;
      height: 60px;
      line-height: 60px;
      text-align: center;
    }
  }
}
</style>
