<script>
export default {
  name: 'BaseOpera',
  data () {
    return {
      title: '',
      visible: false,
      isWorking: false,
      // 主键名称
      idKey: null,
      // 接口
      api: null
    }
  },
  methods: {
    // 配置
    config (extParams = {}) {
      if (extParams.api == null) {
        throw new Error('config缺少api参数')
      }
      const params = {
        idKey: 'id'
      }
      Object.assign(params, extParams)
      this.idKey = params.idKey
      this.api = require(`@/api${params.api}`)
    },
    /**
     * @title 窗口标题
     * @target 编辑的对象
     */
    open (title, target) {
      this.title = title
      this.visible = true
      // 新建
      if (target == null) {
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.form[this.idKey] = null
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
        this.api.create(this.form)
          .then(() => {
            this.visible = false
            this.$tip.success('新建成功')
            this.$emit('success')
          })
          .catch(e => {
            this.$tip.error(e.message)
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
        this.api.updateById(this.form)
          .then(() => {
            this.visible = false
            this.$tip.success('修改成功')
            this.$emit('success')
          })
          .catch(e => {
            this.$tip.error(e.message)
          })
          .finally(() => {
            this.isWorking = false
          })
      })
    }
  }
}
</script>
