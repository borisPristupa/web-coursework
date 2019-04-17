<template>
  <div class=" allpeople col-12 d-flex flex-row">

  <div  class=" slidebar col-sm-4 col-12">

    <div class="bg-dark">

      <div v-if="chathum" class=" overflow-auto" >

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

      <div v-else   class="" >
        <div class="">
          <button @click="chchathum" >Чат</button>

          <input @keyup="search" v-model="searchText" type="text" placeholder="Поиск людей" >
        </div>
        <div class="elements">
          <a class=" overflow-auto" v-for="human in people">
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
              <Createconv @isback="defaultbutt = $event"></Createconv>
            </div>

            <div v-else-if="isopenconv">
              <!--<Openconv :chat="thischat" @isback="defaultbutt = $event"></Openconv>-->



              <div class="container-12 d-flex flex-column">
                <div class="col-12 d-flex flex-row">
                  <button @click="defpage" class="col-12 col-sm-3 col-md-3 col-lg-2">назад</button>

                  <h4 class="p-0 m-0 col-12 col-sm-9 col-md-9 col-lg-10" @click="showchatinfo">{{thischat.name}}</h4>
                  <!--<button @click="addhuman">Добавить участника</button>-->
                </div>
                <!--<div class="d-none">-->
                <!--{{thischat = this.chat}}-->
                <!--</div>-->

                <div v-if="!ischatinfo">

                  <div class="col-12 ">
                    <a v-for="msg in allmessages.slice().reverse()">
                      <br><small class="h6">{{msg.sender_name}}: {{msg.date}}</small><br>
                      <!--findusername(msg)-->
                      <a class="h5">{{msg.text}}</a><br>
                    </a>
                    <br>

                  </div>


                  <div class="col-12 d-flex flex-row">
                    <input class="col-8" id="msg" v-model="message" placeholder="Введите сообщение">
                    <button class="ml-auto" @click="sendmsg">Отправить</button>
                  </div>
                </div>
                <div v-else>
                  <p>Описание:{{thischat.description}}</p>
                  <ul v-for="human in thischat.members">
                    <img class="avatar2" src="../../../img/avatar.png"><b class="convname">{{human.username}}</b>
                  </ul>
                </div>

              </div>

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

                <div >
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
  // import Openconv from "./Openconv";


  export default {
    components: {
      $,Createconv
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
        thischat:{},
        thischatmembers:[],
        allmessages:[
          // '1',
          // '2',
          // '3',
          // '45',
          // '5'
        ],
        message:'',
        ischatinfo:false,
        user:{},


        defaultbutt:true,
        iscreateconv:false,
        isopenconv:false,


      }
    },
    created() {
      this.search();
    },
    methods: {

      search () {
        if (this.people.length === 0 && this.chats.length === 0)  {
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
          }),
            $.ajax({//chat
              type:'GET',
              url:'http://localhost:8181/chat/search',
              xhrFields: {withCredentials: true},
              data:{
                searchfor:this.searchText,
              },
              success:(chats)=>{
                this.chats = chats;
                // alert('nodefchat')
              },
              error:(msg)=>{
                console.log(msg.responseText);
                console.log(msg.status);
              }

            })

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
        // console.log(chat);
        //   this.thischat = chat;
        //   alert('chat-id:'+ chat.id)

          //получение инфи именно об этом чате сделалл так потому что не передает массив в members
          $.ajax({
            async:false,
            type:'GET',
            url:'http://localhost:8181/chat',
            xhrFields: {withCredentials: true},
            data:{
              id: chat.id,
              // amount:'',
              // page:''
            },
            success:(data)=>{
              // var time = data.date;
              this.thischat = data;
              for (let i = 0; i < this.thischat.members; i++) {
                console.log('thischat:'+this.thischat.members[i])
              }

              // this.allmessages.date = time.replace(/.{12}$/, '').replace( /[T]/,' ');
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }
          })
        //и после не передает((


          localStorage.setItem('thischat',JSON.stringify(chat));




          this.isopenconv = true;
          this.defaultbutt = false;
          this.iscreateconv = false;
          this.hook();
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

      },







      hook(){
        // var chat = JSON.parse(localStorage.getItem('chat'));
        // this.chat = chat;
        var user = JSON.parse(localStorage.getItem('client'));
        this.user = user;

        $.ajax({
          type:'GET',
          url:'http://localhost:8181/message',
          xhrFields: {withCredentials: true},
          data:{
            chat_id: this.thischat.id,
            // amount:'',
            // page:''
          },
          success:(data)=>{
            // var time = data.date;
            this.allmessages = data;

            for (let i = 0; i < this.allmessages.length; i++) {
              $.ajax({
                async: false,
                type: 'GET',
                url: 'http://localhost:8181/human',
                xhrFields: {withCredentials: true},
                data: {
                  id: this.allmessages[i].sender_id
                },
                success: (human) => {
                  this.allmessages[i].sender_name = human.first_name + ' ' + human.second_name;
                  this.allmessages[i].date = this.allmessages[i].date.replace(/.{12}$/, '').replace( /[T]/,' ');
                },
                error: (msg) => {
                  console.log("People/hook/gethuman" + msg.responseText);
                  console.log("People/hook/gethuman" + msg.status);
                }
              });
            }

            // console.log(this.allmessages)
          },
          error:(msg)=>{
            console.log(msg.responseText);
            console.log(msg.status);
          }
        })
      },

      showchatinfo(){
        this.ischatinfo = !this.ischatinfo;
        this.hook();
      },

      sendmsg:function () {
        console.log(this.thischat.id);

        $.ajax(
          {
            type:'POST',
            url:'http://localhost:8181/message',
            xhrFields: {withCredentials: true},
            data:{
              chat_id:this.thischat.id,
              text:this.message,
              sender_id: this.user.id,
            },
            success:(data)=>{
              // created();
              this.hook();
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }
          });
      }
    },



  }



</script>
<style>
  .row,. {
    padding: 40px;
  }

  .elements {
    overflow:auto !important;

  }

  /*.slidebar{*/
    /*position: fixed;*/
  /*}*/
  /*.scrollable {*/
    /*overflow-y: scroll;*/
    /*height: 100%;*/

    /*!*overflow-y: scroll;*!*/
    /*!*background: lightblue;*!*/
    /**/
    /*!*position: relative;*!*/
  /*}*/


  .allpeople{
    position: fixed;
    height: 100%;
  }

  .slidebar{
    overflow-y: auto;
    /*background: lightblue;*/

    position: relative;
  }


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


