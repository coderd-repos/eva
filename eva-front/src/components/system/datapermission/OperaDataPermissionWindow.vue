<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="业务模块" prop="businessCode" required>
        <DataPermModuleSelect v-model="form.businessCode" :disabled="form.id != null" :inline="false" @change="handleBusinessChange"/>
      </el-form-item>
      <el-form-item label="角色" prop="roleId" required>
        <RoleSelect v-model="form.roleId" :disabled="form.id != null" :inline="false"/>
      </el-form-item>
      <el-form-item label="权限类型" prop="type" required>
        <DataPermTypeSelect v-model="form.type" :module="form.businessCode" :inline="false" @change="handleTypeChange"/>
      </el-form-item>
      <el-form-item v-show="showCustomData" label="自定义数据" prop="customData">
        <CustomSelect v-if="visible" v-model="customData" :business-code="form.businessCode" @change="handleCustomDataChange"/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" v-trim :rows="3" maxlength="500"/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import BaseOpera from '@/components/base/BaseOpera'
import GlobalWindow from '@/components/common/GlobalWindow'
import RoleSelect from '@/components/system/role/RoleSelect'
import DataPermModuleSelect from './DataPermModuleSelect'
import DataPermTypeSelect from './DataPermTypeSelect'
import CustomSelect from './CustomSelect'
export default {
  name: 'OperaDataPermissionWindow',
  extends: BaseOpera,
  components: { CustomSelect, RoleSelect, DataPermTypeSelect, DataPermModuleSelect, GlobalWindow },
  data () {
    return {
      // 自定义数据
      customData: [],
      // 展示自定义数据标识
      showCustomData: false,
      // 表单数据
      form: {
        id: null,
        businessCode: '',
        roleId: '',
        type: '',
        remark: '',
        customData: ''
      },
      // 验证规则
      rules: {
        businessCode: [
          { required: true, message: '请选择业务模块' }
        ],
        roleId: [
          { required: true, message: '请选择角色' }
        ],
        type: [
          { required: true, message: '请选择权限类型' }
        ]
      }
    }
  },
  methods: {
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
          this.customData = []
          this.showCustomData = false
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
        this.customData = this.form.customData == null || this.form.customData === '' ? [] : this.form.customData.split(',')
        this.handleTypeChange()
      })
    },
    // 业务模块切换
    handleBusinessChange () {
      this.form.customData = ''
      this.customData = []
      this.handleTypeChange()
    },
    // 权限类型切换
    handleTypeChange () {
      if ((this.form.type === 11 || this.form.type === 21) && this.form.businessCode != null && this.form.businessCode !== '') {
        this.showCustomData = true
      } else {
        this.showCustomData = false
      }
    },
    // 自定义数据变化
    handleCustomDataChange (values) {
      this.form.customData = values.join(',')
    }
  },
  created () {
    this.config({
      api: '/system/dataPermission',
      'field.id': 'id'
    })
  }
}
</script>
