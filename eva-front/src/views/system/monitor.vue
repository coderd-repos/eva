<template>
  <Profile :permissions="['system:monitor:query']">
    <div class="monitor">
      <div class="toolbar">
        <el-switch v-model="autoRefresh" @change="changeAutoRefresh"/><label>{{autoRefresh | autoRefreshText}}</label>
      </div>
      <ul>
        <li class="wrap">
          <h2>CPU<Light v-if="data != null" :warn="data.cpu.useRatio > 60" :danger="data.cpu.useRatio > 80"/></h2>
          <div>
            <dl>
              <dt>物理核数</dt>
              <dd><Value :data="data" prop="cpu.physicalCount"/></dd>
            </dl>
            <dl>
              <dt>逻辑核数</dt>
              <dd><Value :data="data" prop="cpu.logicalCount"/></dd>
            </dl>
            <dl class="important">
              <dt>当前使用率</dt>
              <dd><Value :data="data" prop="cpu.useRatio" :handler="keep2decimals" suffix="%"/></dd>
            </dl>
            <dl>
              <dt>当前空闲率</dt>
              <dd><Value :data="data" prop="cpu.freeRatio" :handler="keep2decimals" suffix="%"/></dd>
            </dl>
          </div>
        </li>
        <li class="wrap">
          <h2>内存<Light v-if="data != null" :warn="data.memory.useRatio > 60" :danger="data.memory.useRatio > 80"/></h2>
          <div>
            <dl>
              <dt>总空间</dt>
              <dd><Value :data="data" prop="memory.size" suffix="G" :handler="toG"/></dd>
            </dl>
            <dl>
              <dt>空闲空间</dt>
              <dd><Value :data="data" prop="memory.freeSpace" suffix="G" :handler="toG"/></dd>
            </dl>
            <dl class="important">
              <dt>已用空间</dt>
              <dd><Value :data="data" prop="memory.usedSpace" suffix="G" :handler="toG"/></dd>
            </dl>
            <dl class="important">
              <dt>使用率</dt>
              <dd><Value :data="data" prop="memory.useRatio" suffix="%" :handler="keep2decimals"/></dd>
            </dl>
          </div>
        </li>
        <li class="wrap">
          <h2>JVM<Light v-if="data != null" :warn="data.jvm.memory.useRatio > 60" :danger="data.jvm.memory.useRatio > 80"/></h2>
          <div>
            <dl>
              <dt>安装路径</dt>
              <dd><Value :data="data" prop="jvm.home"/></dd>
            </dl>
            <dl>
              <dt>版本</dt>
              <dd><Value :data="data" prop="jvm.version"/></dd>
            </dl>
            <dl>
              <dt>启动时间</dt>
              <dd><Value :data="data" prop="jvm.bootTime"/></dd>
            </dl>
            <dl>
              <dt>运行时长</dt>
              <dd><Value :data="data" prop="jvm.runtime"/></dd>
            </dl>
            <dl>
              <dt>总空间</dt>
              <dd><Value :data="data" prop="jvm.memory.size" suffix="M" :handler="keep2decimals"/></dd>
            </dl>
            <dl>
              <dt>空闲空间</dt>
              <dd><Value :data="data" prop="jvm.memory.freeSpace" suffix="M" :handler="keep2decimals"/></dd>
            </dl>
            <dl class="important">
              <dt>已用空间</dt>
              <dd><Value :data="data" prop="jvm.memory.usedSpace" suffix="M" :handler="keep2decimals"/></dd>
            </dl>
            <dl class="important">
              <dt>使用率</dt>
              <dd><Value :data="data" prop="jvm.memory.useRatio" suffix="%" :handler="keep2decimals"/></dd>
            </dl>
          </div>
        </li>
        <li class="wrap">
          <h2>服务器</h2>
          <div>
            <dl>
              <dt>操作系统</dt>
              <dd><Value :data="data" prop="osName"/></dd>
            </dl>
            <dl>
              <dt>系统版本</dt>
              <dd><Value :data="data" prop="osVersion"/></dd>
            </dl>
            <dl>
              <dt>系统架构</dt>
              <dd><Value :data="data" prop="osArch"/></dd>
            </dl>
            <dl class="important">
              <dt>IP地址</dt>
              <dd><Value :data="data" prop="ip"/></dd>
            </dl>
            <dl>
              <dt>MAC地址</dt>
              <dd><Value :data="data" prop="mac"/></dd>
            </dl>
            <dl>
              <dt>服务器时间</dt>
              <dd><Value :data="data" prop="currentTime"/></dd>
            </dl>
          </div>
        </li>
      </ul>
      <div class="wrap">
        <h2>磁盘信息</h2>
        <el-table :data="data ? data.disks : []" v-loading="loading">
          <el-table-column prop="name" label="磁盘名称"/>
          <el-table-column prop="dir" label="磁盘路径"/>
          <el-table-column prop="fsType" label="文件系统"/>
          <el-table-column prop="size" label="总空间">
            <template slot-scope="{row}">
              {{toG(row.size)}}G
            </template>
          </el-table-column>
          <el-table-column prop="freeSpace" label="可用空间">
            <template slot-scope="{row}">
              {{toG(row.freeSpace)}}G
            </template>
          </el-table-column>
          <el-table-column prop="usedSpace" label="已用空间">
            <template slot-scope="{row}">
              <label class="important">{{toG(row.usedSpace)}}G</label>
            </template>
          </el-table-column>
          <el-table-column prop="useRatio" label="已用占比">
            <template slot-scope="{row}">
              <label class="important">{{keep2decimals(row.useRatio)}}%</label>
            </template>
          </el-table-column>
          <el-table-column>
            <template slot-scope="{row}">
              <Light :warn="row.useRatio > 60" :danger="row.useRatio > 80" :mini="true"/>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </Profile>
</template>

<script>
import { getSystemInfo } from '@/api/system/monitor'
import Value from '@/components/common/Value'
import Light from '@/components/common/Light'
import Profile from '../../components/common/Profile'

export default {
  name: 'SystemMonitor',
  components: { Profile, Light, Value },
  data () {
    return {
      // 加载中
      loading: false,
      // 自动刷新标识
      autoRefresh: false,
      // 数据
      data: null,
      // 自动刷新定时器
      interval: null
    }
  },
  filters: {
    autoRefreshText (value) {
      if (value) {
        return '已开启自动刷新'
      }
      return '已关闭自动刷新'
    }
  },
  methods: {
    // 切换自动刷新
    changeAutoRefresh (value) {
      if (this.interval != null) {
        clearInterval(this.interval)
      }
      if (value) {
        this.getSystemInfo()
        this.interval = setInterval(() => {
          this.getSystemInfo()
        }, 3000)
      }
    },
    // 获取系统信息
    getSystemInfo () {
      if (this.loading) {
        return
      }
      this.loading = true
      getSystemInfo()
        .then(data => {
          this.data = data
        })
        .catch(e => {
          this.$tip.apiFailed(e)
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 单位转为G
    toG (value) {
      return Math.round(value / 1024 * 100) / 100
    },
    // 转为比率
    keep2decimals (value) {
      return Math.round(value * 100) / 100
    }
  },
  beforeRouteLeave (from, to, next) {
    clearInterval(this.interval)
    next()
  },
  created () {
    this.getSystemInfo()
  }
}
</script>

<style scoped lang="scss">
@import "@/assets/style/variables.scss";
.monitor {
  padding: 20px 20px;
}
// 工具栏
.toolbar {
  margin-bottom: 12px;
  background: #fff;
  padding: 8px 16px;
  label {
    font-size: 12px;
    margin-left: 8px;
    color: #999;
  }
}
ul {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  li {
    width: 49.5%;
    min-height: 200px;
    flex-shrink: 0;
  }
}
// 信息模块
.wrap {
  background: #fff;
  box-shadow: 2px 2px 10px -5px #999;
  border-radius: 8px;
  margin-bottom: 16px;
  h2 {
    border-bottom: 1px solid #eee;
    font-size: 18px;
    font-weight: normal;
    line-height: 40px;
    padding: 0 16px;
    color: #555;
    position: relative;
    .light {
      position: absolute;
      top: 12px;
      right: 12px;
    }
  }
  & > div {
    padding: 0 16px;
    font-size: 14px;
    dl {
      display: flex;
      dt {
        width: 80px;
        text-align: right;
        flex-shrink: 0;
        color: #999;
      }
      dd {
        width: 100%;
        margin: 0;
        padding-left: 12px;
        color: #555;
        overflow: hidden;
      }
    }
  }
}
// 重要信息
.important {
  color: $primary-color;
  font-weight: bold;
  & > dd > div {
    color: $primary-color;
    font-weight: bold;
  }
}
</style>
