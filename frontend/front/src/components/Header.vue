<template>
  <div id="nav_menu">
      <ul v-for="button in buttons">
        <li>
          <button @click="component = button.name" >{{button.title}}</button>
        </li>
      </ul>
    <br>
    <component :is="component"></component>
  </div>


</template>



<script>
import People from './folders/People'
import Expedition from './folders/Expedition'
import Artefacts from './folders/Artefacts'
import Own from './folders/Own'
import Help from './folders/Help'


  export default {
    name:'Header',
    data(){
      return{
        component:'Own', //default component
        buttons:[
          {name:'People',
          title:'Люди'},
          {name:'Expedition',
            title:'Экспед'},
          {name:'Artefacts',
            title:'Артефакты'},
          {name:'Own',
            title:'Кабинет'},
          {name:'Help',
            title:'Помощь'}]
      }
    },
    components:{
      People , Expedition , Artefacts , Own , Help
    },
    methods:{
      change:function () {
        component= button.name
        console.log(component);
      }
    }
  }
</script>






<style>
  * {font-family: Arial, Helvetica, sans-serif}
  #nav_menu {padding: 0; width: 762px; margin: 0 auto}
  #nav_menu ul {list-style: none; margin: 0; padding: 0}
  #nav_menu li {display: inline}
  #nav_menu button {
    float: left;
    width: 125px;
    height: 30px;
    background-color: #cc0033;
    border-left: 2px solid #ffffff;
    text-decoration: none;
    text-align: center;
    padding: 0;
    padding-top: 10px;
    font-size: 18px;
    color: #ffffff
  }
  #nav_menu a:hover {background-color: #990033; color: Tomato}


</style>
