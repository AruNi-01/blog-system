<!--
 * @Descripttion: 
 * @Author: AruNi
 * @Date: 2022-04-10 16:33:05
-->
<template>
  <el-card>
    <h1 class="me-author-name">AruNi_Lu&ensp;<i class="el-icon-male"></i></h1>
    <div class="me-author-description">
      <span><i class="el-icon-location-outline"></i> 湖北 | 武汉</span>
      <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <i class="el-icon-s-check"></i> 学生</span>
    </div>
    <div class="me-author-tool">
      <el-button
        type="small"
        plain
        @click="openQQ">
        QQ
      </el-button>
      <el-button
        type="small"
        plain
        @click="openWX">
        WeChat
      </el-button>
      <el-button
        type="small"
        plain
        @click="openGithub">
        Github
      </el-button>


      <!-- <i @click="showTool(qq)" :title="qq.title" class="iconfont icon-qq"></i>
      <i @click="showTool(bilibili)" :title="bilibili.title" class="iconfont icon-bilibili-fill"></i> -->
    </div>
  </el-card>

</template>

<script>
  export default {
    name: 'CardMe',
    data() {
      return {


        // qq: {title: 'QQ', message: '1298911600'},
        // bilibili: {
        //   title: '哔哩哔哩',
        //   message: '<a target="_blank" href="https://space.bilibili.com/473844125">https://space.bilibili.com/473844125</a>'
        // }
      }
    },
    methods: {
      openQQ() {
        this.$notify({
          title: 'Nice to meet you',
          message: 'QQ: 1298911600',
          type: 'success'
        });
      },
      openWX() {
        this.$notify({
          title: 'Nice to meet you',
          message: 'WeChat: 18786663642',
          type: 'success'
        });
      },
      openGithub() {
        this.$notify({
          title: 'Welcome to my github',
          message: 'Github: https://github.com/AruNi-01',
          type: 'success'
        });
      },




      showTool(tool) {
        this.$message({
          duration: 0,
          showClose: true,
          dangerouslyUseHTMLString: true,
          message: '<strong>' + tool.message + '</strong>'
        });
      }
    }
  }
</script>

<style scoped>
  .me-author-name {
    text-align: center;
    font-size: 30px;
    border-bottom: 1px solid #5FB878;
  }

  .me-author-description {
    padding: 8px 35px;
  }

  .me-icon-job {
    padding-left: 16px;
  }

  .me-author-tool {
    text-align: center;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }
</style>
