<script>
import BasePage from './BasePage'

export default {
  name: 'BaseTable',
  extends: BasePage,
  data () {
    return {
      // 是否展示
      visible: {
        // 新建/修改数据窗口
        operaTable: false
      },
      // 是否正在执行
      isWorking: {
        // 新建/修改中
        operaTable: false,
        // 搜索中
        search: false,
        // 删除中
        delete: false
      },
      // 表格数据
      tableData: {
        // 已选中的数据
        selectedRows: [],
        // 当前页数据
        list: [],
        // 分页
        pagination: {
          pageIndex: 1,
          pageSize: 10,
          total: 0
        }
      }
    }
  },
  methods: {
    // 搜索
    search () {
      this.handlePageChange(1)
    },
    // 搜索框重置
    reset () {
      this.$refs.searchForm.resetFields()
      this.search()
    },
    // 每页显示数量变更处理
    handleSizeChange (val) {
      this.tableData.pagination.pageSize = val
      this.search()
    },
    // 行选中处理
    handleSelectionChange (selectedRows) {
      this.tableData.selectedRows = selectedRows
    }
  }
}
</script>
