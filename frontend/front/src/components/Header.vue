<template>
  <div id="all" class="container-12 d-flex flex-column justify-content-center">

      <div class="col-12 p-0">
        <b-navbar class="navbar  bg-dark" toggleable="lg" type="dark" style="width: 100%" variant="info">
          <b-navbar-brand :href="BT">
            <img src="../img/duck.png" width="40px"  height="40px" >
            Археология
          </b-navbar-brand>

          <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

          <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>

              <ul class="navbar-nav mr-auto header" @click="change"  v-for="button in buttons">
                <!--nav-tabs-->
                <b-nav-item @click="component = button.name"  href="#">{{button.title}}</b-nav-item>
              </ul>

            </b-navbar-nav>
          </b-collapse>
        </b-navbar>
      </div>


    <div class="col-12">
      <component :is="component" ></component> <!--v-bind:json="json"-->
    </div>
  </div>
</template>
<script>
import People from './folders/People/People';
import Expedition from './folders/Expedition/Expedition';
import Artifact from './folders/Artifacts/Artifact';
import Own from './folders/Own/Own';
import Help from './folders/Help/Help';

  export default {
    name:'Header',
    components:{
      People , Expedition , Own , Help ,
      Artifact
    },
    created(){

      this.component = JSON.parse(localStorage.getItem('component'));

    },
    data(){
      return{
        BT:'http://se.ifmo.ru',
        component:'', //default component
        buttons:[
          {name:'People',
          title:'Люди'},
          {name:'Expedition',
            title:'Экспедиции'},
          {name:'Artifact',
            title:'Артефакты'},
          {name:'Own',
            title:'Кабинет'},
          {name:'Help',
            title:'Помощь'}],
        button:{},
      }
    },
    methods:{
      change:function () {
        var comp = JSON.stringify(this.component);
        console.log(comp);
        localStorage.setItem('component',comp);
        // component= button.name;
        console.log(component);
      }
    }
  }


</script>
<style>
  /*#all {*/
    /*width: 100%;*/
    /*margin: 0;*/
    /*padding: 0;*/
    /*background: #474953;*/
  /*}*/
</style>
