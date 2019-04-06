<template>
  <div id="app">

    <Header></Header>
    <br>

    <component :is="component"></component>

    <button @click="change">{{button_name}}</button>

    <a href="https://oauth.vk.com/authorize?client_id=6823463&redirect_uri=http://localhost:8084&response_type=token&v=5.92&scope=email"><img id="vk" src="img/vkcom.svg"></a>
    <a href="http://www.google.com"><img id="google" src="img/google.jpg"></a>
  </div>
</template>

<script>
import Auth from './components/Auth'
import Reg from './components/Reg'
import Header from './components/Header'


export default {
  name: 'App',
  vk_link:'https://oauth.vk.com/authorize?client_id=6823463&redirect_uri=http://localhost:8082&response_type=token&v=5.92&scope=email',
  components: {
    Auth,
    Reg,
    Header
  },
  data(){

    return{
      button_name:'Создать акк?',
      component:'Auth'
    }
  },
  methods:{
    change:function () {
      if (this.component === 'Auth'){
        this.button_name = 'Уже есть акк?';
        this.component = 'Reg'
      } else {
        this.button_name = 'Создать акк?';
        this.component = 'Auth'
      }

    }
  }
}


</script>
