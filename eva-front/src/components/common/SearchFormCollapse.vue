<template>
  <div class="search-form-collapse" :class="{'collapse__hidden': !showMore}">
    <slot></slot>
    <el-button v-if="!showMore" class="collapse__switch" @click="showMore = true">更多查询...</el-button>
    <el-button v-else class="collapse__switch" @click="showMore = false">收起</el-button>
  </div>
</template>

<script>
export default {
  name: 'SearchFormCollapse',
  data () {
    return {
      showMore: false
    }
  }
}
</script>

<style scoped lang="scss">
.search-form-collapse {
  position: relative;
  padding-right: 75px;
  height: auto;
  .collapse__switch {
    position: absolute;
    top: 0;
    right: 0;
  }
  &.collapse__hidden {
    height: 50px;
    overflow: hidden;
    padding-right: 250px;
    /deep/ section {
      position: absolute;
      top: 0;
      right: 100px;
    }
  }
}
</style>
