<template>
  <div class="table-layout">
    <!-- 头部 -->
    <div v-if="withBreadcrumb" class="table-header">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="path in paths" :key="path">{{path}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 搜索表单部分 -->
    <div class="table-search-form">
      <div class="form-wrap">
        <slot name="search-form"></slot>
      </div>
    </div>
    <slot name="space"></slot>
    <!-- 列表和分页部分 -->
    <div class="table-content">
      <div class="table-wrap">
        <slot name="table-wrap"></slot>
      </div>
    </div>
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: 'TableLayout',
  props: {
    // 是否展示头部面包屑
    withBreadcrumb: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    paths () {
      return this.$route.meta.paths
    }
  }
}
</script>

<style lang="scss">
@import "@/assets/style/variables.scss";
.el-container {
  background: #F7F8F9;
  min-height: calc(100vh);
}
// 头部
.table-header {
  overflow: hidden;
  padding: 12px 16px;
  // 页面路径
  .el-breadcrumb {
    .el-breadcrumb__item {
      .el-breadcrumb__inner {
        color: #ABB2BE;
        font-size: 12px;
      }
      &:last-of-type .el-breadcrumb__inner {
        color: #606263;
        font-size: 14px;
      }
    }
  }
}
// 搜索
.table-search-form {
  display: flex;
  flex-wrap: wrap;
  padding: 0 16px;
  .form-wrap {
    padding: 16px 16px 0 16px;
    width: 100%;
    background: #fff;
    &:empty {
      padding: 0;
    }
  }
  section {
    display: inline-block;
    margin-left: 16px;
  }
}
// 列表和分页
.table-content {
  margin-top: 10px;
  padding: 0 16px;
  .table-wrap {
    padding: 16px 16px 0 16px;
    background: #fff;
    // 工具栏
    .toolbar {
      border-bottom: 1px solid #eee;
      padding-bottom: 10px;
      li {
        display: inline-block;
        margin-right: 6px;
      }
    }
    // 表格
    .el-table {
      // 复选框列
      .el-table-column--selection {
        .cell {
          text-align: center !important;
        }
      }
      // 多值字段
      .table-column-strings {
        ul {
          li {
            display: inline-block;
            background: #eee;
            border-radius: 3px;
            padding: 0 3px;
            margin-right: 3px;
            margin-bottom: 3px;
          }
        }
      }
    }
    // 分页
    .table-pagination {
      padding: 16px 0;
      text-align: left;
    }
  }
}
</style>
