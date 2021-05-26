<script>
import BasePage from './BasePage'
export default {
  name: 'BaseTable',
  extends: BasePage,
  data () {
    return {
      // 模块名称
      module: '',
      // 接口
      api: null,
      // 是否正在执行
      isWorking: {
        // 搜索中
        search: false,
        // 删除中
        delete: false
      },
      // 表格数据
      tableData: {
        // 已选中的数据
        selectedRows: [],
        // 排序的字段
        sorts: [],
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
    // 配置
    config (extParams = {}) {
      if (extParams.api == null) {
        throw new Error('config缺少api参数')
      }
      const params = {
        module: '数据',
        defaultSorts: []
      }
      Object.assign(params, extParams)
      this.module = params.module
      this.api = require(`@/api${params.api}`)
      this.tableData.sorts = params.defaultSorts
    },
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
    },
    // 排序
    handleSortChange (sortData) {
      this.tableData.sorts = []
      if (sortData.order != null) {
        this.tableData.sorts.push({
          property: sortData.column.sortBy,
          direction: sortData.order === 'descending' ? 'DESC' : 'ASC'
        })
      }
      this.handlePageChange()
    },
    // 页码变更处理
    handlePageChange (pageIndex) {
      this.__checkApi()
      this.tableData.pagination.pageIndex = pageIndex || this.tableData.pagination.pageIndex
      this.isWorking.search = true
      this.api.fetchList({
        page: this.tableData.pagination.pageIndex,
        capacity: this.tableData.pagination.pageSize,
        model: this.searchForm,
        sorts: this.tableData.sorts
      })
        .then(data => {
          this.tableData.list = data.records
          this.tableData.pagination.total = data.total
        })
        .catch(e => {
          this.$tip.error(e.message)
        })
        .finally(() => {
          this.isWorking.search = false
        })
    },
    // 删除
    deleteById (id) {
      this.__checkApi()
      this.$dialog.confirm(`确认删除此${this.module}吗?`, '提示', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.isWorking.delete = true
        this.api.deleteById(id)
          .then(() => {
            this.$tip.success('删除成功')
            // 删除当前页最后一条记录时查询上一页数据
            if (this.tableData.list.length - 1 === 0) {
              this.handlePageChange(this.tableData.pagination.pageIndex - 1 === 0 ? 1 : this.tableData.pagination.pageIndex - 1)
            } else {
              this.handlePageChange(this.tableData.pagination.pageIndex)
            }
          })
          .catch(e => {
            this.$tip.error(e.message)
          })
          .finally(() => {
            this.isWorking.delete = false
          })
      })
    },
    // 批量删除
    deleteByIdInBatch () {
      this.__checkApi()
      if (this.tableData.selectedRows.length === 0) {
        this.$tip.warning('请至少选择一条数据')
        return
      }
      this.$dialog.confirm(`确认删除已选中的 ${this.tableData.selectedRows.length} 条数据吗?`, '提示', {
        confirmButtonText: '确认删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.isWorking.delete = true
        this.api.deleteByIdInBatch(this.tableData.selectedRows.map(row => row.id).join(','))
          .then(() => {
            this.$tip.success('删除成功')
            // 删除当前页最后一条记录时查询上一页数据
            if (this.tableData.list.length - 1 === 0) {
              this.handlePageChange(this.tableData.pagination.pageIndex - 1 === 0 ? 1 : this.tableData.pagination.pageIndex - 1)
            } else {
              this.handlePageChange(this.tableData.pagination.pageIndex)
            }
          })
          .catch(e => {
            this.$tip.error(e.message)
          })
          .finally(() => {
            this.isWorking.delete = false
          })
      })
    },
    // 检查接口是否配置
    __checkApi () {
      if (this.api == null) {
        throw new Error('页面未初始化配置，请使用this.config进行基础功能配置')
      }
    }
  }
}
</script>
