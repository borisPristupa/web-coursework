// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'



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



