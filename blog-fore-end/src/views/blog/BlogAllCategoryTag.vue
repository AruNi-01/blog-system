<template>
  <div class="me-allct-body" v-title :data-title="categoryTagTitle" >
    <el-container class="me-allct-container">
      <el-main>
        <el-tabs v-model="activeName">

          <el-tab-pane label="" name="category">
            
            <ul class="me-allct-items">
              <li v-for="c in categories" @click="view(c.id)" :key="c.id" class="me-allct-item">
                <div class="me-allct-content">
                  <a class="me-allct-info">
                    <img class="me-allct-img" :src="c.avatar?c.avatar:defaultAvatar"/>
                    <h4 class="me-allct-name">{{c.categoryName}}</h4>
                    <p class="me-allct-description">{{c.description}}</p>
                  </a>

                  <div class="me-allct-meta">
                    <span>{{c.articles}}</span>
                  </div>
                </div>
              </li>
            </ul>
          </el-tab-pane>

          <el-tab-pane label="" name="tag">

            <ul class="me-allct-items">
              <li v-for="t in tags" @click="view(t.id)" :key="t.id" class="me-allct-item">
                <div class="me-allct-content">
                  <a class="me-allct-info">
                    <img class="me-allct-img" :src="t.avatar?t.avatar:defaultAvatar"/>
                    <h4 class="me-allct-name">{{t.tagName}}</h4>
                  </a>

                  <div class="me-allct-meta">
                    <span>{{t.articles}}</span>
                  </div>
                </div>
              </li>
            </ul>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import defaultAvatar from '@/assets/img/logo.png'
  import {getAllCategoriesDetail} from '@/api/category'
  import {getAllTagsDetail} from '@/api/tag'

  export default {
    name: 'BlogAllCategoryTag',
    created() {
      this.getCategories()
      this.getTags()
    },
    data() {
      return {
        defaultAvatar:defaultAvatar,
        categories: [],
        tags: [],
        currentActiveName: 'category'
      }
    },
    computed: {
      activeName: {
        get() {
          return (this.currentActiveName = this.$route.params.type)
        },
        set(newValue) {
          this.currentActiveName = newValue
        }
      },
      categoryTagTitle (){
        if(this.currentActiveName == 'category'){
          return '文章分类'
        }
        console.info('dddd')
        return '标签分类'
      }
    },
    methods: {
      view(id) {
        this.$router.push({path: `/${this.currentActiveName}/${id}`})
      },
      getCategories() {
        let that = this
        getAllCategoriesDetail().then(data => {
          that.categories = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '文章分类加载失败', showClose: true})
          }
        })
      },
      getTags() {
        let that = this
        getAllTagsDetail().then(data => {
          that.tags = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '标签加载失败', showClose: true})
          }
        })
      }
    },
    //组件内的守卫 调整body的背景色
    beforeRouteEnter(to, from, next) {
      window.document.body.style.backgroundColor = '#fff';
      next();
    },
    beforeRouteLeave(to, from, next) {
      window.document.body.style.backgroundColor = '#f5f5f5';
      next();
    }
  }
</script>

<style>

  .el-tabs {
    margin-top: -47px;
  }

  .el-tab-pane {
    margin-top: 15px;
  }

  .me-allct-body {
    margin: 60px auto 140px;
  }

  .me-allct-container {
    width: 1000px;
  }

  .me-allct-items {
    padding-top: 2rem;
  }

  .me-allct-item {
    width: 25%;
    display: inline-block;
    margin-bottom: 2.4rem;
    padding: 0 .7rem;
    box-sizing: border-box;
  }

  .me-allct-content {
    display: inline-block;
    width: 100%;
    background-color: #fff;
    border: 1px solid #6ab495;
    transition: border-color .3s;
    text-align: center;
    padding: 1.5rem 0;
  }
  .me-allct-content:hover {
    transform: translateY(-5px);
    box-shadow: 0px 2px 3px #5FB878;
  }
  .me-allct-content:active {
    transform: translateY(5px);
  }

  .me-allct-info {
    cursor: pointer;
  }

  .me-allct-img {
    margin: -40px 0 10px;
    width: 90px;
    height: 70px;
    vertical-align: middle;

  }

  .me-allct-name {
    font-size: 21px;
    font-weight: 500;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-top: 4px;
    color: black;
    
  }

  .me-allct-description {
    min-height: 50px;
    font-size: 13px;
    line-height: 25px;
  }

  .me-allct-meta {
    font-size: 12px;
    color: #969696;
  }
</style>
