<template>
  <span v-if="content.length <= limit">{{content}}</span>
  <el-popover
    v-else
    v-model="visible"
    popper-class="eva-column-detail-popover"
    trigger="click"
  >
    <div class="eva-column-detail">
      <pre class="eva-column-detail__main">{{formattedContent}}</pre>
      <div class="eva-column-detail__action">
        <el-button size="mini" @click="cancel">关闭</el-button>
        <el-button
          size="mini"
          type="primary"
          v-clipboard:copy="formattedContent"
          v-clipboard:success="copySuccess"
          v-clipboard:error="copyFailed"
          @click="confirm"
        >{{ confirmButtonText }}</el-button>
      </div>
    </div>
    <el-button slot="reference" :type="buttonType">查看</el-button>
  </el-popover>
</template>

<script>
export default {
  name: 'ColumnDetail',
  props: {
    // 按钮类型
    buttonType: {
      type: String
    },
    // 内容
    content: {
      type: String,
      default: ''
    },
    // 限制，大于限制时展示查看按钮
    limit: {
      type: Number,
      default: 12
    },
    // 自动识别数据类型并格式化
    analyse: {
      type: Boolean,
      default: true
    },
    // 是否允许复制
    allowCopy: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      visible: false
    }
  },
  computed: {
    // 确认按钮文案
    confirmButtonText () {
      return this.allowCopy ? '复制' : '确定'
    },
    // 格式化后的内容
    formattedContent () {
      let content = this.content
      if (this.analyse) {
        try {
          content = JSON.stringify(JSON.parse(this.content), null, 2)
        } catch (e) {
        }
      }
      return content
    }
  },
  methods: {
    // 点击确认
    confirm () {
      this.visible = false
      this.$emit('confirm')
    },
    // 点击取消
    cancel () {
      this.visible = false
      this.$emit('cancel')
    },
    // 复制成功
    copySuccess () {
      this.$tip.success('复制成功')
    },
    // 复制失败
    copyFailed () {
      this.$tip.error('复制失败')
    }
  }
}
</script>

<style lang="scss">
.eva-column-detail-popover {
  max-width: 80%;
}
</style>
<style scoped lang="scss">
.eva-column-detail {
  .eva-column-detail__main {
    max-height: 500px;
    overflow: auto;
  }
  .eva-column-detail__action {
    text-align: right;
  }
}
</style>
