<template>
  <GlobalWindow
    class="handle-table-dialog"
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <p class="tip" v-if="form.parent != null && form.id == null">为 <em>{{form.parent.name}}</em> 新建子菜单</p>
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="菜单名称" prop="name" required>
        <el-input v-model="form.name" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="访问路径" prop="path">
        <el-input v-model="form.path" v-trim maxlength="200"/>
      </el-form-item>
      <el-form-item label="图标" prop="icon" class="form-item-icon">
        <el-radio-group v-model="form.icon">
          <el-radio :label="icon" border v-for="icon in icons" :key="icon">
            <i :class="{[icon]: true}"></i>
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" v-model="form.remark" v-trim :rows="3" maxlength="500"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import icons from '../../utils/icons'
import GlobalWindow from '../common/GlobalWindow'
import { create, updateById } from '../../api/system/menu'
export default {
  name: 'OperaMenuWindow',
  components: { GlobalWindow },
  data () {
    return {
      icons,
      title: '',
      visible: false,
      isWorking: false,
      // 表单数据
      form: {
        id: null,
        parent: null,
        name: '',
        path: '',
        icon: '',
        remark: ''
      },
      // 验证规则
      rules: {
        name: [
          { required: true, message: '请输入菜单名称' }
        ]
      }
    }
  },
  methods: {
    /**
     * @title: 窗口标题
     * @target: 编辑的菜单对象
     * @parent: 新建时的上级菜单
     */
    open (title, target, parent) {
      this.title = title
      this.visible = true
      // 新建，menu为空时表示新建菜单
      if (target == null) {
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.form.id = null
          this.form.parent = parent
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
      if (this.form.id == null) {
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
        create({
          ...this.form,
          parentId: this.form.parent == null ? null : this.form.parent.id
        })
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

<style scoped lang="scss">
@import "../../assets/style/variables";
.global-window {
  .tip {
    margin-bottom: 12px;
    em {
      font-style: normal;
      color: $primary-color;
      font-weight: bold;
    }
  }
  // 图标
  /deep/ .form-item-icon {
    .el-form-item__content {
      height: 193px;
      overflow-y: auto;
    }
    .el-radio-group {
      .el-radio {
        margin-right: 0;
      }
      .el-radio__input {
        display: none;
      }
      .el-radio__label {
        padding-left: 0;
        i {
          font-size: 30px;
        }
        &:hover i{
          color: $primary-color;
        }
      }
      .el-radio--small.is-bordered {
        height: auto;
        padding: 8px;
        margin-left: 0;
      }
    }
  }
}
</style>
