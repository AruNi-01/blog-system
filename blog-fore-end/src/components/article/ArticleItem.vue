<template>
  <!--文章列表卡片-->
  <el-card class="me-area" :body-style="{ padding: '16px'}">

    <div class="me-article-header">
      <a @click="view(id)" class="me-article-title">{{title}}</a>
      <el-button v-if="weight > 0" class="me-article-icon" style="color: #67c23a; border: 1px solid #67c23a; margin-left: 8px;">置顶</el-button>
      <span class="me-pull-right me-article-count">
	    	<i class="el-icon-s-comment"></i>&nbsp;{{commentCounts}}
	    </span>
      <span class="me-pull-right me-article-count">
	    	<i class="el-icon-view"></i>&nbsp;{{viewCounts}}
	    </span>
    </div>

    <div class="me-artile-description">
      {{summary}}
    </div>

    <div class="me-article-footer">
	  	<span class="me-article-author">
	    	<i class="el-icon-user"></i>&nbsp;{{author}}
	    </span>
      
      <i class="el-icon-collection-tag" style="margin-right: -10px; "></i>
      <el-tag v-for="t in tags" :key="t.tagName" size="mini" type="success">{{t.tagName}}</el-tag>

      <span class="me-pull-right me-article-count">
	    	<i class="el-icon-time"></i>&nbsp;{{createDate | format}}
	    </span>
    </div>

  </el-card>
</template>

<script>
  import { formatTime } from "../../utils/time";

  export default {
    name: 'ArticleItem',
    props: {
      id: String,
      weight: Number,
      title: String,
      commentCounts: Number,
      viewCounts: Number,
      summary: String,
      author: String,
      tags: Array,
      createDate: String
    },
    data() {
      return {}
    },
    methods: {
      view(id) {
        this.$router.push({path: `/view/${id}`});
      }
    }
  }
</script>

<style scoped>

  .el-card {
    margin-top: 5px;
  }
  .el-card:hover {
    box-shadow: 0px 1px 1px;
    transform: translateY(-4px);
  }
  .el-card:active {
    transform: translateY(5px);
  }

  .me-article-header {
    /*padding: 10px 18px;*/
    padding-bottom: 10px;
  }

  .me-article-title {
    font-weight: 600;
  }

  .me-article-icon {
    padding: 3px 8px;
  }

  .me-article-count {
    color: #a6a6a6;
    padding-left: 14px;
    font-size: 13px;
  }

  .me-pull-right {
    float: right;
  }

  .me-artile-description {
    font-size: 13px;
    line-height: 24px;
    margin-bottom: 10px;
  }

  .me-article-author {
    color: #a6a6a6;
    padding-right: 18px;
    font-size: 13px;
  }

  .el-tag {
    margin-left: 6px;
  }

</style>
