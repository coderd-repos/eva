<template>
  <div class="light" :class="{normal: !warn && !danger, warn: !danger && warn, danger, mini: mini}">
    <em><i></i></em>
  </div>
</template>

<script>
export default {
  name: 'Light',
  props: {
    warn: {
      type: Boolean,
      default: false
    },
    danger: {
      type: Boolean,
      default: false
    },
    mini: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped lang="scss">
$cycle-size01: 16px;
$cycle-size02: 6px;
$normal-color: #00CC99;
$warn-color: #FFCC33;
$danger-color: #FF3300;
@mixin light-status ($cycle-bg) {
  em {
    background: $cycle-bg;
    i {
      background: $cycle-bg - 30;
    }
  }
}
.light {
  display: inline-block;
  border-radius: 50%;
  em {
    width: $cycle-size01;
    height: $cycle-size01;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    i {
      display: block;
      width: $cycle-size02;
      height: $cycle-size02;
      border-radius: 50%;
    }
  }
  &.mini {
    em {
      width: 12px;
      height: 12px;
    }
  }
  // 正常
  &.normal {
    @include light-status($normal-color);
    animation: shine-normal infinite 1s;
  }
  // 警告
  &.warn {
    @include light-status($warn-color);
    animation: shine-warn infinite .8s;
  }
  // 危险
  &.danger {
    @include light-status($danger-color);
    animation: shine-danger infinite .5s;
  }
}
@keyframes shine-normal {
  0% {
    box-shadow: 0 0 5px $normal-color + 10;
  }
  25% {
    box-shadow: 0 0 10px $normal-color + 10;
  }
  50% {
    box-shadow: 0 0 15px $normal-color + 10;
  }
  100% {
    box-shadow: 0 0 20px $normal-color + 10;
  }
}
@keyframes shine-warn {
  0% {
    box-shadow: 0 0 5px $warn-color - 50;
  }
  25% {
    box-shadow: 0 0 10px $warn-color - 50;
  }
  50% {
    box-shadow: 0 0 15px $warn-color - 50;
  }
  100% {
    box-shadow: 0 0 20px $warn-color - 50;
  }
}
@keyframes shine-danger {
  0% {
    box-shadow: 0 0 5px $danger-color + 10;
  }
  25% {
    box-shadow: 0 0 10px $danger-color + 10;
  }
  50% {
    box-shadow: 0 0 15px $danger-color + 10;
  }
  100% {
    box-shadow: 0 0 20px $danger-color + 10;
  }
}
</style>
