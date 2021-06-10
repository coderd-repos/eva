<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="地区名称" prop="name" required>
        <el-input v-model="form.name" maxlength="50" placeholder="请输入地区名称" v-trim/>
      </el-form-item>
      <el-form-item label="地区简称" prop="shortName" required>
        <el-input v-model="form.shortName" maxlength="50" placeholder="请输入地区简称" v-trim/>
      </el-form-item>
      <el-form-item label="地区全称" prop="fullName" required>
        <el-input v-model="form.fullName" maxlength="100" placeholder="请输入地区全称" v-trim/>
      </el-form-item>
      <el-form-item label="地区拼音" prop="pinyin" required>
        <el-input v-model="form.pinyin" maxlength="100" placeholder="请输入地区名称拼音" v-trim/>
      </el-form-item>
      <el-form-item label="区号" prop="areaCode">
        <el-input v-model="form.areaCode" maxlength="20" placeholder="请输入地区区号" v-trim/>
      </el-form-item>
      <el-form-item label="邮编" prop="postalCode">
        <el-input v-model="form.postalCode" maxlength="20" placeholder="请输入地区邮编" v-trim/>
      </el-form-item>
      <el-form-item label="首字母" prop="firstLetter" required>
        <el-input v-model="form.firstLetter" maxlength="1" placeholder="请输入地区名称首字母" v-trim/>
      </el-form-item>
      <el-form-item label="经度" prop="lng">
        <el-input v-model="form.lng" maxlength="50" placeholder="请输入地区经度" v-trim/>
      </el-form-item>
      <el-form-item label="纬度" prop="lat">
        <el-input v-model="form.lat" maxlength="50" placeholder="请输入地区纬度" v-trim/>
      </el-form-item>
    </el-form>
  </GlobalWindow>
</template>

<script>
import BaseOpera from '@/components/base/BaseOpera'
import GlobalWindow from '@/components/common/GlobalWindow'
export default {
  name: 'OperaLocationWindow',
  extends: BaseOpera,
  components: { GlobalWindow },
  data () {
    return {
      // 表单数据
      form: {
        id: null,
        parentId: '',
        shortName: '',
        name: '',
        fullName: '',
        level: '',
        pinyin: '',
        areaCode: '',
        postalCode: '',
        firstLetter: '',
        lng: '',
        lat: ''
      },
      // 验证规则
      rules: {
        name: [
          { required: true, message: '请输入地区名称' }
        ],
        shortName: [
          { required: true, message: '请输入地区简称' }
        ],
        fullName: [
          { required: true, message: '请输入地区全称' }
        ],
        pinyin: [
          { required: true, message: '请输入地区名称拼音' }
        ],
        firstLetter: [
          { required: true, message: '请输入地区名称首字母' }
        ]
      }
    }
  },
  methods: {
    /**
     * @title 窗口标题
     * @parent 父区域ID
     * @target 编辑的区域对象
     */
    open (title, target, parent) {
      this.title = title
      this.visible = true
      // 新建
      if (target == null) {
        this.$nextTick(() => {
          this.$refs.form.resetFields()
          this.form.id = null
          this.form.parentId = parent == null ? null : parent.id
          this.form.level = parent == null ? 1 : parent.level + 1
        })
        return
      }
      // 编辑
      this.$nextTick(() => {
        for (const key in this.form) {
          this.form[key] = target[key]
        }
      })
    }
  },
  created () {
    this.config({
      api: '/system/location',
      'field.id': 'id'
    })
  }
}
</script>
