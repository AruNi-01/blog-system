# blog-system
基于B站码神之路的个人博客系统
- 新增文章查询功能
- 界面粗略美化
# 说明
- 云储存使用的阿里OSS，需要自己配置AccessKey，若不使用，将utils包下的OssUtil注释
- SQL文件在resource的static目录下
# 项目环境：
- JDK 1.8
- IDEA 2021.3
- SpringBoot 2.5

技术栈 ：SpringBoot + MybatisPlus


数据库：Redis+MySQL


权限认证：JWT


# 启动项目
> 前端项目启动步骤：
```bash
# install dependencies
$ npm install

# build for production and launch server
$ npm run build

# serve with hot reload at localhost:8080
$ npm run dev
```
- 需要node.js环境！
> 后端项目启动：
- 导入数据库(记得添加一些测试数据)
- 配置好resource下的`application.yaml`文件
- 启动SpringBoot项目: 直接运行`BlogApplication`
