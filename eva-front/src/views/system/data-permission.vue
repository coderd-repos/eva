<template>
  <TableLayout :permissions="['system:datapermission:query']">
    <!-- 搜索表单 -->
    <el-form ref="searchForm" slot="search-form" :model="searchForm" label-width="100px" inline>
      <el-form-item label="业务模块" prop="businessCode">
        <DataPermModuleSelect v-model="searchForm.businessCode" clearable @change="search"/>
      </el-form-item>
      <el-form-item label="角色" prop="roleId">
        <RoleSelect v-model="searchForm.roleId" clearable @change="search"/>
      </el-form-item>
      <section>
        <el-button type="primary" @click="search">搜索</el-button>
        <el-button @click="reset">重置</el-button>
      </section>
    </el-form>
    <!-- 表格和分页 -->
    <template v-slot:table-wrap>
      <ul class="toolbar" v-permissions="['system:datapermission:create', 'system:datapermission:delete']">
        <li><el-button type="primary" @click="$refs.operaDataPermissionWindow.open('新建数据权限')" icon="el-icon-plus" v-permissions="['system:datapermission:create']">新建</el-button></li>
        <li><el-button @click="deleteByIdInBatch" icon="el-icon-delete" v-permissions="['system:datapermission:delete']">删除</el-button></li>
      </ul>
      <el-table
        v-loading="isWorking.search"
        :data="tableData.list"
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="businessCode" label="业务模块" min-width="100px">
          <template slot-scope="{row}">{{row.businessCode | moduleText(modules)}}</template>
        </el-table-column>
        <el-table-column prop="roleId" label="角色" min-width="100px">
          <template slot-scope="{row}">{{row.role.name}}</template>
        </el-table-column>
        <el-table-column prop="type" label="权限类型" min-width="140px">
          <template slot-scope="{row}">{{row.type | typeText(types)}}</template>
        </el-table-column>
        <el-table-column prop="disabled" label="是否启用" min-width="100px">
          <template slot-scope="{row}">
            <el-switch v-model="row.disabled" :active-value="false" :inactive-value="true" @change="switchDisabled(row)"/>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="100px"></el-table-column>
        <el-table-column prop="createUser" label="创建人" min-width="100px">
          <template slot-scope="{row}">{{row.createUserInfo == null ? '' : row.createUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" min-width="140px"></el-table-column>
        <el-table-column prop="updateUser" label="修改人" min-width="100px">
          <template slot-scope="{row}">{{row.updateUserInfo == null ? '' : row.updateUserInfo.username}}</template>
        </el-table-column>
        <el-table-column prop="updateTime" label="修改时间" min-width="140px"></el-table-column>
        <el-table-column
          v-if="containPermissions(['system:datapermission:update', 'system:datapermission:delete'])"
          label="操作"
          min-width="120"
          fixed="right"
        >
          <template slot-scope="{row}">
            <el-button type="text" @click="$refs.operaDataPermissionWindow.open('编辑数据权限', row)" icon="el-icon-edit" v-permissions="['system:datapermission:update']">编辑</el-button>
            <el-button type="text" @click="deleteById(row)" icon="el-icon-delete" v-permissions="['system:datapermission:delete']">删除</el-button>
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
    <OperaDataPermissionWindow ref="operaDataPermissionWindow" @success="handlePageChange"/>
  </TableLayout>
</template>

<script>
import BaseTable from '@/components/base/BaseTable'
import TableLayout from '@/layouts/TableLayout'
import Pagination from '@/components/common/Pagination'
import OperaDataPermissionWindow from '@/components/system/datapermission/OperaDataPermissionWindow'
import DataPermModuleSelect from '@/components/system/datapermission/DataPermModuleSelect'
import RoleSelect from '../../components/system/role/RoleSelect'

// 获取模块名称
const __getModuleName = function (businessCode, modules) {
  for (const module of modules) {
    if (module.businessCode === businessCode) {
      return module.moduleName
    }
  }
  return '未知'
}

export default {
  name: 'DataPermission',
  extends: BaseTable,
  components: { RoleSelect, DataPermModuleSelect, TableLayout, Pagination, OperaDataPermissionWindow },
  data () {
    return {
      // 数据权限模块
      modules: [],
      // 数据权限类型
      types: [],
      // 搜索
      searchForm: {
        businessCode: '',
        roleId: null,
        type: ''
      }
    }
  },
  filters: {
    // 数据权限类型文案
    typeText (value, types) {
      for (const type of types) {
        if (type.code === value) {
          return type.remark
        }
      }
      return '未知'
    },
    // 数据权限模块文案
    moduleText (value, modules) {
      return __getModuleName(value, modules)
    }
  },
  methods: {
    // 启用/禁用菜单
    switchDisabled (row) {
      if (!row.disabled) {
        this.__updateStatus(row)
        return
      }
      this.$dialog.disableConfirm(`确认禁用 ${__getModuleName(row.businessCode, this.modules)}/${row.role.name} 数据权限吗？`)
        .then(() => {
          this.__updateStatus(row)
        }).catch(() => {
          row.disabled = !row.disabled
        })
    },
    // 删除行
    deleteById (row) {
      this.$dialog.deleteConfirm(`确认删除【${__getModuleName(row.businessCode, this.modules)}/${row.role.name}】数据权限吗？`)
        .then(() => {
          this.isWorking.delete = true
          this.api.deleteById(row.id)
            .then(() => {
              this.$tip.apiSuccess('删除成功')
              this.__afterDelete()
            })
            .catch(e => {
              this.$tip.apiFailed(e)
            })
            .finally(() => {
              this.isWorking.delete = false
            })
        })
        .catch(() => {})
    },
    // 修改状态
    __updateStatus (row) {
      this.api.updateStatus({
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
  async created () {
    this.config({
      module: '数据权限',
      api: '/system/dataPermission'
    })
    // 初始化数据权限模块
    await this.api.fetchModules()
      .then(data => {
        this.modules = data
      })
    // 初始化数据权限模块
    await this.api.fetchTypes()
      .then(data => {
        this.types = data
      })
      .catch(e => {
        console.log(e)
      })
    // 执行搜索
    this.search()
  }
}
</script>
