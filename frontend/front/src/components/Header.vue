<template>
  <div id="all">

      <div>
        <b-navbar class="navbar  bg-dark" toggleable="lg" type="dark" style="width: 100%" variant="info">
          <b-navbar-brand :href="BT">
            <img src="../img/duck.png" width="40px"  height="40px" >
            Археология
          </b-navbar-brand>

          <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

          <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>

              <ul class="navbar-nav mr-auto header"  v-for="button in buttons">
                <!--nav-tabs-->
                <b-nav-item @click="component = button.name"   href="#">{{button.title}}</b-nav-item>
              </ul>

            </b-navbar-nav>
          </b-collapse>
        </b-navbar>
      </div>

    <component :is="component" v-bind:json="json"></component>
  </div>
</template>
<script>
import People from './folders/People/People'
import Expedition from './folders/Expedition/Expedition'
import Artefacts from './folders/Artefacts/Artefacts'
import Own from './folders/Own/Own'
import Help from './folders/Help/Help'

  export default {
    name:'Header',
    data(){
      return{
        BT:'http://se.ifmo.ru',
        json:'    {\n' +
          '        "id":1, \n' +
          '        "username":"user", \n' +
          '        "first_name":"first", \n' +
          '        "second_name":"second", \n' +
          '        "last_name": null,\n' +
          '        "bio": "тот еще пидр This text is the actual content of the box. We have added a 25px padding, 25px margin and a 25px green border. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",\n' +
          '        "vk_id": 1630342124,\n' +
          '        "tg_nickname": null,\n' +
          '        "email": "e@mail.com",\n' +
          '        "likes": 0,\n' +
          '        "dislikes": 0,\n' +
          '        "banned": false,\n' +
          '        "coutry": {"id": 1, "name": "ВТ"},\n' +
          '        "moderator": true,\n' +
          '        "archaeologist": false,\n' +
          '        "researcher": true,\n' +
          '        "collector": false,\n' +
          '        "sponsor": true\n' +
          '    }',

        // for own
        /*
          {
            "id":1,
            "username":"user",
            "first_name":"first",
            "second_name":"second",
            "last_name": null,
            "bio": "",
            "vk_id": 1630342124,
            "tg_nickname": null,
            "email": "e@mail.com",
            "likes": 0,
            "dislikes": 0,
            "banned": false,
            "coutry": {id: 1, name: "ВТ"},
            "moderator": false,
            "archaeologist": false,
            "researcher": true,
            "collector": false,
            "sponsor": false
          }
        */
        // for people  { "namemsgs": {"Ford":{"date":"1210/12/12","msgs":["lol","ford"]}, "BMW":{"date":"1211/12/12","msgs":["lol","bmw"]}, "Fiat":{"date":"1212/12/12","msgs":["lol","kek"]}}}
        component:'Expedition', //default component
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
  #all {
    width: 100%;
    margin: 0;
    padding: 0;
    background: #474953;
  }
</style>
