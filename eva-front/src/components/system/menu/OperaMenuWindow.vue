<template>
  <GlobalWindow
    class="handle-table-dialog"
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <p class="tip" v-if="form.parent != null && form.id == null">为 <em>{{parentName}}</em> 新建子菜单</p>
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="上级菜单" prop="parentId">
        <MenuSelect v-if="visible" v-model="form.parentId" placeholder="请选择上级菜单" :exclude-id="excludeMenuId" clearable :inline="false"/>
      </el-form-item>
      <el-form-item label="菜单名称" prop="name" required>
        <el-input v-model="form.name" placeholder="请输入菜单名称" v-trim maxlength="50"/>
      </el-form-item>
      <el-form-item label="访问路径" prop="path">
        <el-input v-model="form.path" placeholder="请输入访问路径" v-trim maxlength="200"/>
      </el-form-item>
      <el-form-item label="图标" prop="icon" class="form-item-icon">
        <el-radio-group v-model="form.icon">
          <el-radio :label="icon" v-for="icon in icons" :key="icon">
            <i :class="{[icon]: true}"></i>
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" v-model="form.remark" placeholder="请输入菜单备注" v-trim :rows="3" maxlength="500"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import BaseOpera from '@/components/base/BaseOpera'
import GlobalWindow from '@/components/common/GlobalWindow'
import MenuSelect from '@/components/common/MenuSelect'
import icons from '@/utils/icons'
export default {
  name: 'OperaMenuWindow',
  extends: BaseOpera,
  components: { MenuSelect, GlobalWindow },
  data () {
    return {
      icons,
      // 上级菜单名称
      parentName: '',
      // 需排除选择的菜单ID
      excludeMenuId: null,
      // 表单数据
      form: {
        id: null,
        parentId: null,
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
        this.excludeMenuId = null
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.form.id = null
          this.form.parentId = parent == null ? null : parent.id
          this.parentName = parent == null ? null : parent.name
        })
        return
      }
      // 编辑
      this.$nextTick(() => {
        this.excludeMenuId = target.id
        for (const key in this.form) {
          this.form[key] = target[key]
        }
      })
    }
  },
  created () {
    this.config({
      api: '/system/menu'
    })
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/style/variables";
$icon-background-color: $primary-color;
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
      background: $icon-background-color;
      padding: 4px;
      .el-radio {
        margin-right: 0;
        color: #fff;
        padding: 6px;
        &.is-checked {
          background: $icon-background-color - 30;
          border-radius: 10px;
        }
        .el-radio__input.is-checked + .el-radio__label {
          color: #fff;
        }
      }
      .el-radio__input {
        display: none;
      }
      .el-radio__label {
        padding-left: 0;
        // element-ui图标
        i {
          font-size: 30px;
        }
        // 自定义图标
        [class^="eva-icon-"], [class*=" eva-icon-"] {
          width: 30px;
          height: 30px;
          background-size: 25px;
          vertical-align: bottom;
        }
      }
      .el-radio--small {
        height: auto;
        padding: 8px;
        margin-left: 0;
      }
    }
  }
}
</style>
