<template>
  <TableLayout :permissions="['system:location:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="100px" inline>
      <el-form-item label="名称" prop="name">
        <el-input v-model="searchForm.name" placeholder="请输入名称" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="地区范围" prop="parentId">
        <LocationSelect :city-id.sync="searchForm.parentId" placeholder="请选择地区范围" :level="2" clearable @change="search"/>
      </el-form-item>
      <el-form-item label="地区层级" prop="level">
        <el-select v-model="searchForm.level" placeholder="请选择地区层级" clearable @change="search">
          <el-option value="1" label="省"/>
          <el-option value="2" label="市"/>
          <el-option value="3" label="区/县"/>
        </el-select>
      </el-form-item>
      <el-form-item label="区号" prop="areaCode">
        <el-input v-model="searchForm.areaCode" placeholder="请输入区号" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <el-form-item label="邮编" prop="postalCode">
        <el-input v-model="searchForm.postalCode" placeholder="请输入邮编" @keypress.enter.native="search"></el-input>
      </el-form-item>
      <section>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </section>
    </el-form>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:location:create']">
        <li><el-button type="primary" @click="$refs.operaLocationWindow.open('新建地区')" icon="el-icon-plus" v-permissions="['system:location:create']">新建</el-button></li>
      </ul>
      <el-table
        v-loading="isWorking.search"
        :data="tableData.list"
        stripe
      >
        <el-table-column prop="name" label="名称" min-width="100px"></el-table-column>
        <el-table-column prop="shortName" label="简称" min-width="100px"></el-table-column>
        <el-table-column prop="fullName" label="全称" min-width="180px"></el-table-column>
        <el-table-column prop="pinyin" label="拼音" min-width="100px"></el-table-column>
        <el-table-column prop="level" label="层级" min-width="80px">
          <template slot-scope="{row}">
            {{ row.level | levelText }}
          </template>
        </el-table-column>
        <el-table-column prop="areaCode" label="区号" min-width="100px"></el-table-column>
        <el-table-column prop="postalCode" label="邮编" min-width="100px"></el-table-column>
        <el-table-column prop="firstLetter" label="首字母" min-width="100px"></el-table-column>
        <el-table-column prop="disabled" label="是否启用" min-width="80px">
          <template slot-scope="{row}">
            <el-switch v-model="row.disabled" :active-value="false" :inactive-value="true" @change="switchDisabled(row)"/>
          </template>
        </el-table-column>
        <el-table-column prop="lng" label="经度" min-width="100px"></el-table-column>
        <el-table-column prop="lat" label="纬度" min-width="100px"></el-table-column>
        <el-table-column
          v-if="containPermissions(['system:location:update', 'system:location:delete'])"
          label="操作"
          min-width="160"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button type="text" @click="$refs.operaLocationWindow.open('编辑地区', row)" icon="el-icon-edit" v-permissions="['system:location:update']">编辑</el-button>
            <el-button type="text" v-if="row.level === 1" @click="$refs.operaLocationWindow.open('新增市', null, row)" icon="el-icon-edit" v-permissions="['system:location:create']">新增市</el-button>
            <el-button type="text" v-if="row.level === 2" @click="$refs.operaLocationWindow.open('新增区/县', null, row)" icon="el-icon-edit" v-permissions="['system:location:create']">新增区/县</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
        :pagination="tableData.pagination"
      >
      </pagination>
    </template>
    <!-- 新建/修改 -->
    <OperaLocationWindow ref="operaLocationWindow" @success="handlePageChange"/>
  </TableLayout>
</template>

<script>
import BaseTable from '@/components/base/BaseTable'
import TableLayout from '@/layouts/TableLayout'
import Pagination from '@/components/common/Pagination'
import OperaLocationWindow from '@/components/system/location/OperaLocationWindow'
import LocationSelect from '@/components/common/LocationSelect'
import { updateStatus } from '@/api/system/location'

export default {
  name: 'SystemLocation',
  extends: BaseTable,
  components: { LocationSelect, TableLayout, Pagination, OperaLocationWindow },
  data () {
    return {
      // 搜索
      searchForm: {
        parentId: null,
        level: null,
        name: '',
        areaCode: '',
        postalCode: ''
      }
    }
  },
  filters: {
    levelText (value) {
      if (value === 1) {
        return '省'
      }
      if (value === 2) {
        return '市'
      }
      if (value === 3) {
        return '区/县'
      }
      return '未知'
    }
  },
  methods: {
    // 启用/禁用
    switchDisabled (row) {
      if (!row.disabled) {
        this.__updateMenuStatus(row)
        return
      }
      this.$dialog.disableConfirm(`确认禁用 ${row.fullName} 地区吗？`)
        .then(() => {
          this.__updateMenuStatus(row)
        }).catch(() => {
          row.disabled = !row.disabled
        })
    },
    // 修改菜单状态
    __updateMenuStatus (row) {
      updateStatus({
        id: row.id,
        disabled: row.disabled
      })
        .then(() => {
          this.$tip.apiSuccess('修改成功')
        })
        .catch(e => {
          row.disabled = !row.disabled
          this.$tip.apiFailed(e)
        })
    }
  },
  created () {
    this.config({
      module: '地区',
      api: '/system/location',
      'field.id': 'id',
      'field.main': 'fullName'
    })
    this.search()
  }
}
</script>
