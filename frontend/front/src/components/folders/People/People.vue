<template>
  <div id="allpeople" class="col-12 d-flex flex-row">

  <div id="msgbox" class="col-sm-4 h col-12 m-0">
    <div class="bg-dark">

      <div v-if="chathum" class="overflow-auto" >

        <button @click="chchathum" >Люди</button>

        <input @keyup="search" v-model="searchText" type="text" placeholder="Поиск чата" >

        <div class=" elements overflow-auto">
          <a v-for="chat in chats">
            <hr>
            <div @click="showconv(chat)">
              <!--@click="showchat"-->
              <img id="chaticon"  class="avatar2" src="../../../img/avatar.png"><b class="convname">{{chat.name}}</b>
            </div>
          </a>

        </div>
      </div>


      <div v-else  class="overflow-auto" >

        <button @click="chchathum" >Чат</button>

        <input @keyup="search" v-model="searchText" type="text" placeholder="Поиск людей" >

        <div id="scrollable">
          <a class="elements" v-for="human in people">
            <hr>
            <div @click="showhuman(human)">
              <img id="avatarmini" class="avatar2" src="../../../img/avatar.png"><b class="convname">{{human.username}}</b>
            </div>
          </a>
        </div>

      </div>

    </div>

  </div>


    <div class="col-sm-8  d-none d-sm-block  col-12">
      <div id="conver_area" class="bg-dark align-content-center">
        <!--{{this.defaultbutt}} {{this.iscreateconv}} {{this.isopenconv}}-->
          <div v-if="chathum">

            <div v-if="defaultbutt">
            <p class="createconv text-center" @click="createconv">Нажмите, чтоб создать беседу </p>
            </div>

            <div v-else-if="iscreateconv">
              <Createconv  @isback="defaultbutt = $event"></Createconv>
            </div>

            <div v-else-if="isopenconv">
              <Openconv @isback="defaultbutt = $event"></Openconv>
            </div>

          </div>

          <div v-else>

            <div v-if="!humaninfopage">
              <p class="createconv text-center"> Здесь инфа о челе </p>
            </div>

            <div v-else-if="humaninfopage"  id="info">
              <img id="avatar" class="avatar" src="../../../img/avatar.png" width="200px" height="200px">

              <div>
                <h3>{{humaninfo.first_name}} {{humaninfo.second_name}} {{humaninfo.last_name}}</h3>
                <p>Telegram: {{humaninfo.tg_nickname}}</p>
                <p>E-mail: {{humaninfo.email}}</p>

                <div v-bind:onloadstart="jobs">
                  <b v-if="humaninfo.moderator">модератор</b>
                  <b v-if="humaninfo.archaeologist"> археолог</b>
                  <b v-if="humaninfo.researcher"> исследователь</b>
                  <b v-if="humaninfo.collector"> коллекцонер</b>
                  <b v-if="humaninfo.sponsor"> спонсор</b>
                </div>
                <br>
                <p>{{humaninfo.country}}</p>
                <p>{{humaninfo.bio}}</p>
              </div>
            </div>
          </div>

      </div>

    </div>

  </div>
</template>
<script>
  var $ = require('jquery')
  window.jQuery = $ //для подкл jquery

  import Createconv from "./Createconv";
  import Openconv from "./Openconv";


  export default {
    components: {
      $,Createconv,Openconv
    },
    data () {
      return {

        searchText: '',
        archaeologist:true,
        researcher:true,
        collector:true,
        sponsor:true,


        people:[],
        chats:[],

        humaninfopage:false,
        humaninfo:{},


        chathum:false,//true - chat


        defaultbutt:false,
        iscreateconv:false,
        isopenconv:true,


      }
    },
    created() {
      this.search();
    },
    methods: {

      search () {
        if (this.people.length === 0) {
          $.ajax({
            type:'GET',
            url:'http://localhost:8181/human/search',
            xhrFields: {withCredentials: true},
            data:{
              amount:100,
              archaeologist:this.archaeologist,
              researcher:this.researcher,
              collector:this.collector,
              sponsor:this.sponsor,
              searchfor:this.searchText,
            },
            success:(data)=>{
              console.log(this.searchText);
              this.people = data;
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }

          });
            //chats
          $.ajax({
            type:'GET',
            url:'http://localhost:8181/chat/search',
            xhrFields: {withCredentials: true},
            data:{
              searchfor:'',
            },
            success:(chats)=>{
              this.chats = chats;

              localStorage.setItem('allchats',JSON.stringify(chats))
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }

          });
        }else {
          $.ajax({
            type:'GET',
            url:'http://localhost:8181/human/search',
            xhrFields: {withCredentials: true},
            data:{
              amount:100,
              archaeologist:this.archaeologist,
              researcher:this.researcher,
              collector:this.collector,
              sponsor:this.sponsor,
              searchfor:this.searchText,
            },
            success:(data)=>{
              console.log(this.searchText);
              this.people = data;
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }
          },
            {//chat
              type:'GET',
              url:'http://localhost:8181/chat/search',
              xhrFields: {withCredentials: true},
              data:{
                searchfor:this.searchText,
              },
              success:(chats)=>{
                this.chats = chats;
                alert('nodefchat')
              },
              error:(msg)=>{
                console.log(msg.responseText);
                console.log(msg.status);
              }

            }
            )

        }
      },


      chchathum(){
        this.chathum = !this.chathum;
      },

      showhuman(human){
        this.humaninfopage = true;
        this.humaninfo = human;
      },
      showconv(chat){
        localStorage.setItem('thischat',JSON.stringify(chat));
          this.isopenconv = true;
          this.defaultbutt = false;
          this.iscreateconv = false;
      },
      createconv () {
          this.iscreateconv = true;
          this.defaultbutt = false;
          this.isopenconv = false;

      },
      defpage(){
        localStorage.setItem('chat','');
          this.iscreateconv = false;
          this.defaultbutt = true;
          this.isopenconv = false;

      }


    },



  }



</script>
<style>
  .row,. {
    padding: 40px;
  }

  /*.elements {*/
    /*background: white;*/
    /*overflow-y: scroll !important;*/

  /*}*/

  /*div#allpeople{*/
    /*position: fixed;*/
    /*height: 100%;*/
  /*}*/

  /*div#msgbox{*/
  /*}*/
  /*div#scrollable {*/
    /*overflow-y: scroll;*/
    /*background: lightblue;*/

    /*position: relative;*/
  /*}*/



  .createconv {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
  #conver_area {
    height: 100%;
    margin: 10px;
    color: whitesmoke;
  }
  #msgbox {
    height: 100%;
    margin: 10px;
    color: whitesmoke;
  }
  .multi {
    float: top;
    padding: 10px;
    margin: 10px;
  }

  .avatar2 {
    position: relative;
    left: 15px;
    vertical-align: middle;
    width: 45px;
    height: 45px;
    border-radius: 50%;
  }
  .convname {
    position: relative;
    left: 30px;
    vertical-align: middle;
  }
  hr{
    margin: 14px;
  }
</style>


