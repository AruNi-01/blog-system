<template>
  <!--<transition name="el-zoom-in-center">-->
  <transition>
    <div @click="toTop" v-show="topShow" class="me-to-top">
      <i class="el-icon-caret-top"></i>
    </div>
  </transition>
</template>

<script>
  export default {
    name: 'GoTop',
    data() {
      return {
        topShow: false
      }
    },
    methods: {
      toTop() {
        document.body.scrollTop = 0;
        document.documentElement.scrollTop = 0;
        this.topShow = false;
      },
      needToTop() {
        let curHeight = document.documentElement.scrollTop || document.body.scrollTop;

        if (curHeight > 400) {
          this.topShow = true;
        } else {
          this.topShow = false;
        }

      }
    },
    mounted() {
      /**
       * 等到整个视图都渲染完毕
       */
      this.$nextTick(function () {
        window.addEventListener('scroll', this.needToTop);
      });
    }
  }
</script>

<style>

  .me-to-top {
    background-color: #fff;
    position: fixed;
    margin-bottom: -4%;
    right: 50px;
    bottom: 150px;
    width: 40px;
    height: 40px;
    border-radius: 5px;
    cursor: pointer;
    transition: .3s;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, .5);
    z-index: 5;
  }

  .me-to-top i {
    color: #00d1b2;
    display: block;
    text-align: center;
    line-height: 40px;
    font-size: 18px;
  }

  .me-to-top i:hover {
    color: #fff;
  }

  .me-to-top:hover {
    background-color: #2EE59D;
    box-shadow: 0px 10px 20px rgba(46, 229, 157, 0.4);
    color: #fff;
    transform: translateY(-3px);
  }

  .me-to-top:active {
    transform: translateY(-15px);
  }

</style>
