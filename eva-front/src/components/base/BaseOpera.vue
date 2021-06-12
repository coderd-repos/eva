<script>
export default {
  name: 'BaseOpera',
  data () {
    return {
      title: '',
      visible: false,
      isWorking: false,
      // 接口
      api: null,
      // 配置数据
      configData: {
        'field.id': 'id'
      }
    }
  },
  methods: {
    // 配置
    config (extParams = {}) {
      if (extParams == null) {
        throw new Error('Parameter can not be null of method \'config\' .')
      }
      if (extParams.api == null) {
        throw new Error('Missing config option \'api\'.')
      }
      this.api = require('@/api' + extParams.api)
      extParams['field.id'] && (this.configData['field.id'] = extParams['field.id'])
    },
    /**
     * 打开窗口
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
          this.form[this.configData['field.id']] = null
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
    // 确认新建
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
            this.$tip.apiSuccess('新建成功')
            this.$emit('success')
          })
          .catch(e => {
            this.$tip.apiFailed(e)
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
            this.$tip.apiSuccess('修改成功')
            this.$emit('success')
          })
          .catch(e => {
            this.$tip.apiFailed(e)
          })
          .finally(() => {
            this.isWorking = false
          })
      })
    }
  }
}
</script>
