<!--
 * @Descripttion: 
 * @Author: AruNi
 * @Date: 2022-04-10 16:33:05
-->
<template>
  <el-footer class="me-area">
    <div class="me-footer">
      <p>Don't give yourself a chance to say that I could have
        <strong>
          <router-link to="/" class="me-login-design-color">&ensp;-AruNi</router-link>
        </strong>
      </p>
    </div>
  </el-footer>

</template>

<script>

  export default {
    name: 'BaseFooter',
    data() {
      return {}
    },
    methods: {},
    mounted() {
    }
  }
</script>

<style>

  .el-footer {
    min-width: 100%;
    box-shadow: 0 -2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 1024;
  }

  .me-footer {
    text-align: center;
    line-height: 60px;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;

  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

</style>
