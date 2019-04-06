// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Main from './Main'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  component:'Auth',
  data:{
    button_name:'Создать акк?',
    buttons:['Люди','Экспед','Артефакты','Кабинет','Помощь']
    // vk_img: 'src/',
    // google_img:''
  },
  components: { App },
  template: '<App/>'
})

