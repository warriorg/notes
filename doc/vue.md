## Vue 简介

* 构建==数据驱动==的web界面框架
* 实现响应的==数据绑定==和组合的视图组件

![image](assets/images/vue-mvvm.png)

```javascript
Vue({
  /* view */
  el: '#app',
  /* model */
  data: {
    message: 'Hello Vue.js!'
  },
  methods: {
    reverseMessage: function () {
      this.message = this.message.split('').reverse().join('')
    }
  }
})
```

### MVVM的原理

#### 脏检查机制：

Angular 采取的脏检查机制，当发生了某种事件（例如输入），Angular.js会检查新的数据结构和之前的数据结构是否发生来变动，来决定是否更新视图。

#### 数据劫持

Vue 的实现方式，对数据（Model）进行劫持，当数据变动时，数据会出发劫持时绑定的方法，对视图进行更新。


## Vue 技术栈

### [ES6](http://es6.ruanyifeng.com/)

#### Promise

#### Module

### [vue](https://cn.vuejs.org/index.html)

### [vue-cli](https://cli.vuejs.org/guide/)

> Vue的脚手架工具，用于自动生成Vuzzze项目的目录及文件。

```bash
# 全局安装 vue-cli
npm install -g @vue/cli
```

### [vue-router](https://router.vuejs.org/)

> Vue提供的前端路由工具，利用其我们实现页面的路由控制，局部刷新及按需加载，构建单页应用，实现前后端分离。

### [vuex](https://vuex.vuejs.org/)

>Vue提供的状态管理工具，用于同一管理我们项目中各种数据的交互和重用，存储我们需要用到数据对象。

### NPM

> node.js的包管理工具，用于同一管理我们前端项目中需要用到的包、插件、工具、命令等，便于开发和维护。

### webpack

> 一款强大的文件打包工具，可以将我们的前端项目文件同一打包压缩至js中，并且可以通过vue-loader等加载器实现语法转化与加载

### Babel

> 一款将ES6代码转化为浏览器兼容的ES5代码的插件







编辑调试配置，新建JavaScript调试配置，并设置要访问的url，以及Remote url配置，如下图所示:
![image-20190107161422500](assets/images/webstorm_debug_vue_setting.gif)
在URL处填写: http://localhost:8080			
在src的Remote url处填写: webpack:///src			
保存好调试配置



## VUE 学习

### scoped

#### scoped穿透

**stylus**

```
外层 >>> 第三方组件 
	样式

.wrapper >>> .swiper-pagination-bullet-active
	background: #fff
```

**less** 和 **scss**

```css
外层 /deep/ 第三方组件 {
  样式
}
.wrapper /deep/ .swiper-pagination-bullet-active{
  background: #fff;
}
```