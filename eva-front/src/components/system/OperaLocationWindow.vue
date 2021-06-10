<template>
  <GlobalWindow
    :title="title"
    :visible.sync="visible"
    :confirm-working="isWorking"
    @confirm="confirm"
  >
    <el-form :model="form" ref="form" :rules="rules">
      <el-form-item label="父id" prop="parentId">
        <el-input v-model="form.parentId" placeholder="请输入父id" v-trim/>
      </el-form-item>
      <el-form-item label="简称" prop="shortName">
        <el-input v-model="form.shortName" placeholder="请输入简称" v-trim/>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入名称" v-trim/>
      </el-form-item>
      <el-form-item label="全称" prop="fullName">
        <el-input v-model="form.fullName" placeholder="请输入全称" v-trim/>
      </el-form-item>
      <el-form-item label="层级 0 1 2 省市区县" prop="level">
        <el-input v-model="form.level" placeholder="请输入层级 0 1 2 省市区县" v-trim/>
      </el-form-item>
      <el-form-item label="拼音" prop="pinyin">
        <el-input v-model="form.pinyin" placeholder="请输入拼音" v-trim/>
      </el-form-item>
      <el-form-item label="长途区号" prop="areaCode">
        <el-input v-model="form.areaCode" placeholder="请输入长途区号" v-trim/>
      </el-form-item>
      <el-form-item label="邮编" prop="postalCode">
        <el-input v-model="form.postalCode" placeholder="请输入邮编" v-trim/>
      </el-form-item>
      <el-form-item label="首字母" prop="firstLetter">
        <el-input v-model="form.firstLetter" placeholder="请输入首字母" v-trim/>
      </el-form-item>
      <el-form-item label="经度" prop="lng">
        <el-input v-model="form.lng" placeholder="请输入经度" v-trim/>
      </el-form-item>
      <el-form-item label="纬度" prop="lat">
        <el-input v-model="form.lat" placeholder="请输入纬度" v-trim/>
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
      }
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
