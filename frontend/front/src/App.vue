<template>
  <div id="app">

    <div v-if="!aut.hasAccess" class="container-12 d-flex justify-content-md-center">

        <div id="authpage" class="col-12 col-md-8 col-lg-6"  >
          <div class="row">

          <div class="col-4">
            <a href="http://se.ifmo.ru"><img id="BT" src="./img/authduck.png" width="200px" height="200px"></a>
          </div>
          <div class="col-8 text-center">
            <h3>ИТМО ВТ</h3>
            <h1>Археология</h1>
          </div>

            <keep-alive>
              <component :is="component" @getinfo=" aut = $event " class="col-12"></component>
            </keep-alive>


          <!--<a class="p-2" :href="this.vk_link"><img id="vk" src="./img/vkcom.svg" ></a>-->
          <!--<a class="p-2" @click="listLabels" id="authorize_button"  href="#"><img src="./img/google.jpg" width="50px" height="50px" ></a>-->

            <!--&lt;!&ndash;Add buttons to initiate auth sequence and sign out&ndash;&gt;-->
            <!--<button id="authorize_button" style="d
            isplay: none;">Authorize</button>-->


            <!--<button id="signout_button" style="display: none;">Sign Out</button>-->

            <!--<pre id="content" style="white-space: pre-wrap;"></pre>-->

            <!--<script async defer src="https://apis.google.com/js/api.js"-->
                    <!--onload="this.onload=function(){};handleClientLoad()"-->
                    <!--onreadystatechange="if (this.readyState === 'complete') this.onload()">-->
            <!--</script>-->

            <a class="ml-auto p-2 chbutton" @click="change">{{button_name}}</a>
          </div>
        </div>
    </div>

    <div v-else>
      <Header></Header>
    </div>


  </div>
</template>

<script>

import Vue from 'vue'
import Auth from './components/authpage/Auth'
import Reg from './components/authpage/Reg'
import Header from './components/Header'
import People from "./components/folders/People/People";


import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
Vue.use(BootstrapVue)
export default {
  name: 'App',
  components: {
    People,
    Auth,
    Reg,
    Header
  },
  data(){

      return{
        aut:{
          //for log_in
          login: '',
          password: '',

          //for registr
          name:'',
          email:'',
          rep_pass:'',
          hasAccess:false},

        vk_link:'https://oauth.vk.com/authorize?client_id=6823463&redirect_uri=http://localhost:8081&response_type=token&v=5.92',//works
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
    },
  },
}
</script>

<style>
  #authpage {
    padding: 15px;
    top: 30px;
    color: whitesmoke;
    background: #222427;
  }
  #vk, #google{
    width: 50px;
    height: 50px;
    border-radius: 7px;
  }

  a.chbutton {
    position: relative;
    top:9px;
    font-size: 20px;
    color: whitesmoke;
    text-underline: whitesmoke;
    height: 50px;
    border-radius: 7px;
    display: inline-block;

  }
  a.chbutton:active {
    background: linear-gradient(rgba(0,0,0,.2), rgba(0,0,0,.1));
    box-shadow:
      inset rgba(0,0,0,.8) 0 1px 2px,
      inset rgba(0,0,0,.05) 0 -1px 0,
      #fff 0 1px 1px;
  }

  #app {

  }
</style>

