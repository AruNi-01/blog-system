<template>
  <el-header class="me-area">
    <el-row class="me-header">

      <el-col :span="4" class="me-header-left" style="padding-left: 50px;">
        <router-link to="/" class="me-title">
          <img src="../assets/img/logo.png" />
        </router-link>
      </el-col>

      <el-col v-if="!simple" :span="16">
        <el-menu :router=true menu-trigger="click" active-text-color="#5FB878" :default-active="activeIndex"
                 mode="horizontal">
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/category/all">文章分类</el-menu-item>
          <el-menu-item index="/tag/all">标签分类</el-menu-item>
          <el-menu-item index="/archives">文章归档</el-menu-item>

          <el-col :span="4" :offset="4">
            <el-menu-item index="/write"><i class="el-icon-edit"></i>写文章</el-menu-item>
          </el-col>

          <!-- 搜索框 -->
          <el-popover placement="bottom" width="300" trigger="click">
            <el-input name="searchInput" v-model="keywords" @keyup.enter.native="searchClick"
              placeholder="请输入文章标题...">
              <el-button slot="append" @click="searchClick" class="search-button">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                  style="fill: rgba(0, 0, 0, 1);">
                  <path
                    d="M10 18a7.952 7.952 0 0 0 4.897-1.688l4.396 4.396 1.414-1.414-4.396-4.396A7.952 7.952 0 0 0 18 10c0-4.411-3.589-8-8-8s-8 3.589-8 8 3.589 8 8 8zm0-14c3.309 0 6 2.691 6 6s-2.691 6-6 6-6-2.691-6-6 2.691-6 6-6z">
                  </path>
                  <path
                    d="M11.412 8.586c.379.38.588.882.588 1.414h2a3.977 3.977 0 0 0-1.174-2.828c-1.514-1.512-4.139-1.512-5.652 0l1.412 1.416c.76-.758 2.07-.756 2.826-.002z">
                  </path>
                </svg>
              </el-button>
            </el-input>
            <el-button slot="reference" circle size="small" style="margin-top: 13px; margin-left: 100px;" class="search-small-button" title="点击搜索">
              <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24"
                style="fill: rgba(0, 0, 0, 1);">
                <path
                  d="M10 18a7.952 7.952 0 0 0 4.897-1.688l4.396 4.396 1.414-1.414-4.396-4.396A7.952 7.952 0 0 0 18 10c0-4.411-3.589-8-8-8s-8 3.589-8 8 3.589 8 8 8zm0-14c3.309 0 6 2.691 6 6s-2.691 6-6 6-6-2.691-6-6 2.691-6 6-6z">
                </path>
                <path
                  d="M11.412 8.586c.379.38.588.882.588 1.414h2a3.977 3.977 0 0 0-1.174-2.828c-1.514-1.512-4.139-1.512-5.652 0l1.412 1.416c.76-.758 2.07-.756 2.826-.002z">
                </path>
              </svg>
            </el-button>

            <ul v-if="articles.length > 0" style="margin-top: 10px;">
              <li v-for="a in articles" @click="view(a.id)" :key="a.id" class="search-result">
                  <a>{{a.title}}</a>
              </li>
            </ul>
          </el-popover>
         
        </el-menu>
      </el-col>

      <template v-else>
        <slot></slot>
      </template>

      <el-col :span="4">
        <el-menu :router=true menu-trigger="click" mode="horizontal" active-text-color="#5FB878">
          <div class="login-register" v-if="!user.login">
              <router-link class="login-button" to="/login">登录</router-link>
              <router-link class="register-button" to="/register">注册</router-link>
          </div>
          <template v-else>
            <el-submenu index>
              <template slot="title">
                <img class="me-header-picture" :src="user.avatar"/>
              </template>
              <el-menu-item index @click="logout"><i class="el-icon-back" ></i>退出</el-menu-item>
            </el-submenu>
          </template>
        </el-menu>
      </el-col>

    </el-row>
  </el-header>
</template>

<script>
  const axios = require('axios')

  // import {searchArticle} from '@/api/article'

  export default {
    name: 'BaseHeader',
    props: {
      activeIndex: String,
      simple: {
        type: Boolean,
        default: false
      }
    },
    data() {
        return {
          keywords: '',
          articles: [
            {
              id: '',
              title: '',
              tags: [
                {
                  tagName: ''
                }
              ]

            }
          ]
        }
    },
    computed: {
      user() {
        let login = this.$store.state.account.length != 0
        let avatar = this.$store.state.avatar
        return {
          login, avatar
        }
      }
    },
    methods: {
      searchClick(){
        axios({
          method: "post",
          url: "http://localhost:8888/articles/search",
          params:{title:this.keywords}
        }).then(res=>{
          this.articles=res.data.data;

          console.log(this.articles)
        })
      },

      view(id) {
        this.$router.push({path: `/view/${id}`})
      },


      logout() {
        let that = this
        this.$store.dispatch('logout').then(() => {
          this.$router.push({path: '/'})
        }).catch((error) => {
          if (error !== 'error') {
            that.$message({message: error, type: 'error', showClose: true});
          }
        })
      }
    }
  }
</script>

<style>

  .search-result {
    margin-top: 5px;
    margin-left: 2px;
    list-style: none;
    font-size: 15px;
    height: 25px;
  }

  .search-result:hover {
    background-color:#f5f5f5;
  }

  .search-small-button:hover {
    background-color: #5fb378;
  }

  .login-register {
    height: 60px;

  }
  .login-button {
    display: inline-block;
    margin-top: 6px;
    width: 50px;
    margin-right: 30px;
    padding: 5px;
    font-size: 15px;
    outline: none;
    border: 2px solid rgb(95, 179, 120);
    background-color: white;
    color: white;
    position: relative;
    letter-spacing: 1px;
  }
  .login-button::before {
    content: '登录';
    /*Button's value/text-content */
    position: absolute;
    top: -14%;
    left: 7%;
    background-color: rgb(95, 179, 120);
    width: 100%;
    height: 100%;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: 0.15s ease-in-out;
    font-weight: bold;
  }  
  .login-button:hover::before {
    top: 0;
    left: 0;
  }
  .register-button {
    display: inline-block;
    width: 50px;
    margin-top: 10px;
    margin-right: 30px;
    padding: 5px;
    font-size: 15px;
    outline: none;
    border: 2px solid rgb(95, 179, 120);
    background-color: white;
    color: white;
    position: relative;
    letter-spacing: 1px;
  }
  .register-button::before {
    content: '注册';
    /*Button's value/text-content */
    position: absolute;
    top: -14%;
    left: 7%;
    background-color: rgb(95, 179, 120);
    width: 100%;
    height: 100%;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: 0.15s ease-in-out;
    font-weight: bold;
  }  
  .register-button:hover::before {
    top: 0;
    left: 0;
  }

  .el-menu-item:hover {
    background-color: rgb(95, 179, 120) !important;
    color: white !important;
    border-bottom-color: rgb(95, 179, 120) !important;
  }

  .el-header {
    position: fixed;
    z-index: 1024;
    min-width: 100%;
    box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
  }

  .me-title {
    margin-top: 10px;
    font-size: 24px;
  }

  .me-header-left {
    margin-top: 10px;
  }

  .me-title img {
    max-height: 2.4rem;
    max-width: 100%;
  }

  .me-header-picture {
    width: 36px;
    height: 36px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
  }
</style>
