<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="业务模块" prop="businessCode" required>
        <el-input v-model="form.businessCode" placeholder="请输入业务模块" v-trim/>
      </el-form-item>
      <el-form-item label="角色ID" prop="roleId" required>
        <el-input v-model="form.roleId" placeholder="请输入角色ID" v-trim/>
      </el-form-item>
      <el-form-item label="权限类型（0全部，1自定义，2仅用户所属，3用户所属及其子数据）" prop="type" required>
        <el-input v-model="form.type" placeholder="请输入权限类型（0全部，1自定义，2仅用户所属，3用户所属及其子数据）" v-trim/>
      </el-form-item>
      <el-form-item label="是否禁用" prop="disabled" required>
        <el-input v-model="form.disabled" placeholder="请输入是否禁用" v-trim/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" placeholder="请输入备注" v-trim/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import BaseOpera from '@/components/base/BaseOpera'
import GlobalWindow from '@/components/common/GlobalWindow'
export default {
  name: 'OperaDataPermissionWindow',
  extends: BaseOpera,
  components: { GlobalWindow },
  data () {
    return {
      // 表单数据
      form: {
        id: null,
        businessCode: '',
        roleId: '',
        type: '',
        disabled: '',
        remark: ''
      },
      // 验证规则
      rules: {
        businessCode: [
          { required: true, message: '请输入业务模块' }
        ],
        roleId: [
          { required: true, message: '请输入角色ID' }
        ],
        type: [
          { required: true, message: '请输入权限类型（0全部，1自定义，2仅用户所属，3用户所属及其子数据）' }
        ],
        disabled: [
          { required: true, message: '请输入是否禁用' }
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
