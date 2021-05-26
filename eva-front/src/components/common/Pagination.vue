<template>
  <div class="table-pagination">
    <el-pagination
      :current-page="pagination.pageIndex"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pagination.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      @size-change="sizeChange"
      @current-change="currentChange"
      background>
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: 'Pagination',
  props: {
    pagination: {
      type: Object,
      default: function () {
        return {}
      }
    }
  },
  data () {
    return {
    }
  },
  methods: {
    sizeChange (value) {
      this.$emit('size-change', value)
    },
    currentChange (value) {
      this.$emit('current-change', value)
    }
  }
}
</script>
