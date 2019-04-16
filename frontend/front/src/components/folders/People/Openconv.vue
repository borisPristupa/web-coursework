<template>
  <div class="container-12 d-flex flex-column">
    <div class="col-12 d-flex flex-row">
      <button @click="back" class="col-12 col-sm-3 col-md-3 col-lg-2">назад</button>

      <h4 class="p-0 m-0 col-12 col-sm-9 col-md-9 col-lg-10" @click="showchatinfo">{{chat.name}}</h4>
      <!--<button @click="addhuman">Добавить участника</button>-->
    </div>
    <div v-if="!ischatinfo">
      <div class="col-12 ">
        <a v-for="msg in allmessages.slice().reverse()">
          <br><small class="h6">{{findusername(msg)}}:{{msg.date}}</small><br>
          <a class="h5">{{msg.text}}</a>
        </a>
        <br>

      </div>


      <div class="col-12 d-flex flex-row">
        <input class="col-8" id="msg" v-model="message" placeholder="Введите сообщение">
        <button class="ml-auto" @click="sendmsg">Отправить</button>
      </div>
    </div>
    <div v-else>
      <p>Описание:{{chat.description}}</p>
      <ul v-for="human in chat.members">
        <img id="avatarmini" class="avatar2" src="../../../img/avatar.png"><b class="convname">{{human.username}}</b>
      </ul>
    </div>


  </div>
</template>
<script>
  var $ = require('jquery');
  window.jQuery = $; //для подкл jquery

  export default {
    name:'Openconv',
    components:{
      $,
    },
    created(){
      // alert('test')
      var chat = JSON.parse(localStorage.getItem('thischat'));
      this.chat = chat;
      var user = JSON.parse(localStorage.getItem('client'));
      this.user = user;

      $.ajax({
        type:'GET',
        url:'http://localhost:8181/message',
        xhrFields: {withCredentials: true},
        data:{
          chat_id: this.chat.id,
          // amount:'',
          // page:''
        },
        success:(data)=>{
          // var time = data.date;
          this.allmessages = data;
          console.log(JSON.stringify(this.allmessages))
          // this.allmessages.date = time.replace(/.{12}$/, '').replace( /[T]/,' ');
        },
        error:(msg)=>{
          console.log(msg.responseText);
          console.log(msg.status);
        }
      })
      // alert('test')

    },
    data(){
      return{

        chat:{},

        user:{},

        allmessages:[],


        message:'',

        ischatinfo:false,

      }

    },
    methods:{
      // hook(){
      //   var chat = JSON.parse(localStorage.getItem('chat'));
      //   this.chat = chat;
      //   var user = JSON.parse(localStorage.getItem('client'));
      //   this.user = user;
      //
      //   $.ajax({
      //     type:'GET',
      //     url:'http://localhost:8181/message',
      //     xhrFields: {withCredentials: true},
      //     data:{
      //       chat_id: this.chat.id,
      //       // amount:'',
      //       // page:''
      //     },
      //     success:(data)=>{
      //       // var time = data.date;
      //       this.allmessages = data;
      //       console.log(this.allmessages)
      //       // this.allmessages.date = time.replace(/.{12}$/, '').replace( /[T]/,' ');
      //     },
      //     error:(msg)=>{
      //       console.log(msg.responseText);
      //       console.log(msg.status);
      //     }
      //   })
      // },
      findusername(msg){

        var time = msg.date;
        msg.date = time.replace(/.{12}$/, '').replace( /[T]/,' ');
        for (var i=0;i<this.chat.members.length;i++) {


          if (msg.sender_id === this.chat.members[i].id) {
            return  this.chat.members[i].first_name + ' ' + this.chat.members[i].second_name;

          }
        }


      },
      back:function(){
        this.isback = true;
        this.$emit('isback',this.isback)
      },

      showchatinfo(){
        this.ischatinfo = !this.ischatinfo;
        this.hook();
      },

      sendmsg:function () {
        console.log(this.user);

        $.ajax(
          {
            type:'POST',
            url:'http://localhost:8181/message',
            xhrFields: {withCredentials: true},
            data:{
              chat_id:this.chat.id,
              text:this.message,
              sender_id: this.user.id,
            },
            success:(data)=>{
              // created();
              alert(JSON.stringify(data))
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }
        });
        this.hook();
        alert(this.message)
      }
    }
  }

</script>
<style>
  small {
    font-size: smaller;
  }

</style>
