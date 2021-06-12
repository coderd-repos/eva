<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="业务模块" prop="businessCode" required>
        <DataPermModuleSelect v-model="form.businessCode" :inline="false"/>
      </el-form-item>
      <el-form-item label="角色" prop="roleId" required>
        <RoleSelect v-model="form.roleId" :inline="false"/>
      </el-form-item>
      <el-form-item label="权限类型" prop="type" required>
        <DataPermTypeSelect v-model="form.type" :inline="false"/>
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
export default {
  name: 'OperaDataPermissionWindow',
  extends: BaseOpera,
  components: { RoleSelect, DataPermTypeSelect, DataPermModuleSelect, GlobalWindow },
  data () {
    return {
      // 表单数据
      form: {
        id: null,
        businessCode: '',
        roleId: '',
        type: '',
        remark: ''
      },
      // 验证规则
      rules: {
        businessCode: [
          { required: true, message: '请选择业务模块' }
        ],
        roleId: [
          { required: true, message: '请选择角色ID' }
        ],
        type: [
          { required: true, message: '请选择权限类型' }
        ]
      }
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
