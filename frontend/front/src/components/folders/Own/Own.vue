<template>
  <div id="Own">
    <h3 class="col-12">Биография</h3>
    <div>
        <div class="row">
          <div class="info col-12 ">
            <div id="info">
              <img id="editimg" @click="edit" src="../../../img/edit.png">
              <img id="avatar" class="avatar" src="../../../img/avatar.png">

              <div v-if="!isedit">

                  <h3>{{user.first_name}} {{user.second_name}} {{user.last_name}}</h3>
                  <p>Telegram: {{user.tg_nickname}}</p>
                  <p>E-mail: {{user.email}}</p>

                <div>
                  <b v-if="user.moderator">модератор</b>
                  <b v-if="user.archaeologist"> археолог</b>
                  <b v-if="user.researcher"> исследователь</b>
                  <b v-if="user.collector"> коллекцонер</b>
                  <b v-if="user.sponsor"> спонсор</b>
                </div>
                <br>
                <p>{{user.country}}</p>
                <p>{{user.bio}}</p>
              </div>
              <div v-else>
                <input v-model="user.first_name">
                <input v-model="user.second_name">
                <input v-model="user.last_name">
                <input v-model="user.tg_nickname">
                <input v-model="user.email">


                <!--<div v-bind:onloadstart="jobs">-->
                  <!--<b v-if="user.moderator">модератор</b>-->
                  <!--<b v-if="user.archaeologist">, археолог</b>-->
                  <!--<b v-if="user.researcher">, исследователь</b>-->
                  <!--<b v-if="user.collector">, коллекцонер</b>-->
                  <!--<b v-if="user.sponsor">, спонсор</b>-->
                <!--</div>-->
                <br>

                <select v-model="user.country" >
                  <option v-for="country in countries" :value="country">{{country}}</option>
                </select>
                <input v-model="user.bio">

              </div>
              <button class="align-content-center bg-danger" id="exitbtn" @click="exit">Выход</button>

            </div>
          </div>

            <!--<div id="contact" class="col-md-4 col-12 d-md-flex align-self-center align-self-md-auto">-->

              <!--<div class="row">-->
                <!--<div class=" likes col-md-12 col-6 text-center align-self-md-center">-->
                  <!--&lt;!&ndash;<img id="likeimg"  src="../../../img/like.png"><br>&ndash;&gt;-->
                  <!--&lt;!&ndash;<b>{{user.likes}}</b><br>&ndash;&gt;-->
                  <!--&lt;!&ndash;<button id="writebtn" @click="writemsg">Написать</button>&ndash;&gt;-->
                <!--</div>-->

                <!--<div class=" likes col-md-12 col-6 text-center align-self-md-center">-->
                  <!--&lt;!&ndash;<img id="dislikeimg" src="../../../img/dislike.png"><br>&ndash;&gt;-->
                  <!--&lt;!&ndash;<b>{{user.dislikes}}</b><br>&ndash;&gt;-->
                  <!--<button id="exitbtn" @click="exit">Выход</button>-->
                <!--</div>-->
            <!--</div>-->


          <!--</div>-->
        </div>
    </div>
  </div>
</template>
<script>
  var $ = require('jquery')
  window.jQuery = $ //для подкл jquery
  export default {
    name:'Own',
    components:{
    },
    data(){
      var use = JSON.parse(localStorage.getItem('client'));
      // console.log(use.email);
      // console.log(localStorage.getItem('client'));


      return{
        user: {
          id: use.id,
          username: use.username,
          first_name: use.first_name,
          second_name: use.second_name,
          last_name: use.last_name,
          bio: use.bio,

          vk_id: use.vk_id,
          tg_nickname: use.tg_nickname,
          email: use.email,

          likes:use.likes,
          dislikes:use.dislikes,
          banned: use.banned,

          country: use.country,


          moderator: use.moderator,
          archaeologist: use.archaeologist,
          researcher: use.researcher,
          collector: use.collector,
          sponsor: use.sponsor

          },

        countries:[],

        isedit:false,

      }
    },
    methods:{
      writemsg:function () {
        alert('writemsg');//TODO связать с сообщ
      },
      exit:function () {
        var exitdata = {
          login: '',
          password: '',
          hasAccess:false
        }

        sessionStorage.setItem('client',JSON.stringify(exitdata))
        location.reload(true)
      },
      edit(){

        $.ajax({
          async:false,
          type: 'GET',
          url: 'http://localhost:8181/country',
          xhrFields: {withCredentials: true},
          data:{},
          success:(data)=>{
            this.countries = data;
          },
          error:(msg)=>{
            console.log(msg.responseText);
            console.log(msg.status);
          }
        });


        if (this.isedit == true){

          $.ajax({
            async:false,
            type: 'PATCH',
            url: 'http://localhost:8181/human',
            xhrFields: {withCredentials: true},
            data:{
              id: this.user.id,
              username: this.user.username,
              first_name: this.user.first_name,
              second_name: this.user.second_name,
              last_name: this.user.last_name,
              bio: this.user.bio,

              vk_id: this.user.vk_id,
              tg_nickname: this.user.tg_nickname,
              email: this.user.email,

              likes:this.user.likes,
              dislikes:this.user.dislikes,
              banned: this.user.banned,

              country: this.user.country,

              moderator: false,//TODO доделать
              archaeologist: this.user.archaeologist,
              researcher: false,
              collector: this.user.collector,
              sponsor: this.user.sponsor
            },
            success:(data)=>{
              localStorage.setItem('client',JSON.stringify(data));
              alert('успешно')
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }
          });

        }
        this.isedit = !this.isedit;
      }
    }

  }

</script>
<style>

  #info{
    background: #1b1e23;
    color: whitesmoke;
    margin: 25px;
    padding: 10px;
  }
  .avatar {
    float: left;
    vertical-align: middle;
    width: 160px;
    height: 150px;
    border-radius: 50%;
  }

  #editimg {
    float: right;
    width: 60px;
    height: 60px;
  }

  #contact{
    color: whitesmoke;
    margin: 0px;
    padding: 10px;
  }

  #writebtn, #exitbtn{
  width: 120px;
  }


  #likeimg {
  width: 70px;
  height: 70px;
  }

  #dislikeimg {
  width: 70px;
  height: 70px;
  }


  {
  /*#Own {*/
    /*!*position: relative;*!*/
    /*height: 100%;*/
  /*}*/

  /*#info {*/
    /*!*position: relative;*!*/
    /*background: #1b1e23;*/
    /*color: #bcc3ca;*/
    /*width: 100%;*/
    /*height: 100%;*/
    /*padding: 30px;*/
    /*margin: 25px;*/
  /*}*/
  /*#contact {*/
    /*color: #bcc3ca;*/
    /*width: 100%;*/
    /*height: 100%;*/
    /*padding: 30px;*/
    /*margin: 25px;*/
  /*}*/
  /*#info h3 {margin: 5%}*/

  /*#info * {*/
    /*margin: 1px;*/
  /*}*/



  /*.header,.top-menu,.top-material,.left-sidebar,.content{*/
    /*background:#ccc;*/
    /*margin-bottom:1px;*/
  /*}*/
  /*!*ВЫСОТА ШАПКИ*!*/
  /*.header{*/
    /*height:150px;*/
  /*}*/
  /*!*ВЫСОТА МЕНЮ*!*/
  /*.top-menu{*/
    /*height:30px;*/
  /*}*/
  /*!*ВЫСОТА БЛОКА ВАЖНЫЕ МАТЕРИАЛЫ*!*/
  /*.top-material{*/
    /*height:200px;*/
  /*}*/
  /*!*ВЫСОТА САЙДБАРА И БЛОКА КОНТЕНТ*!*/
  /*.left-sidebar,.content{*/
    /*height:300px;*/
  /*}*/
  /*!*ГРАНИЦА У КОНТЕНТА*!*/
  /*.content{*/
    /*border-left:1px solid #fff;*/
  /*}*/
  }
</style>
