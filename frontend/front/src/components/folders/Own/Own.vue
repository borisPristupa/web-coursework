<template>
  <div class="overflow-auto" id="Own">
    <h3 class="col-12">Биография</h3>
    <div v-if="this.json">
        <div class="row">
          <div class="info col-md-8  col-sm-12">
            <div id="info" class="bg-dark">
              <img id="reportimg" @click="report" src="../../../img/info_block.png">
              <img id="avatar" class="avatar" src="../../../img/testava.png">
              <h3>{{user.first_name}} {{user.second_name}} {{user.last_name}}</h3>
              <p>Telegram: {{user.tg_nickname}}</p>
              <p>E-mail: {{user.email}}</p>
              <div v-bind:onloadstart="jobs">
                <b v-if="user.moderator">модератор</b>
                <b v-if="user.archaeologist">, археолог</b>
                <b v-if="user.researcher">, исследователь</b>
                <b v-if="user.collector">, коллекцонер</b>
                <b v-if="user.sponsor">, спонсор</b>
              </div>
              <br>
              <p>{{user.bio}}</p>
            </div>
          </div>

          <div id="contact" class="col-md-4 col-12 align-self-center">

            <div class="row">
              <div class=" likes col-md-6 col-6 text-center">
                <img id="likeimg"  src="../../../img/like.png"><br>
                <b>{{user.likes}}</b><br>
                <button id="writebtn" @click="writemsg">Написать</button>
              </div>

              <div class=" likes col-md-6 col-6 text-center">
                <img id="dislikeimg" src="../../../img/dislike.png"><br>
                <b>{{user.dislikes}}</b><br>
                <button id="exitbtn" @click="exit">Выход</button>
              </div>
            </div>


          </div>
        </div>
    </div>
  </div>
</template>
<script>
  export default {
    props:['json'],
    name:'Own',
    data(){

      var user = JSON.parse(this.json);


      /*{
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
        "coutry": 'Россия',
        "moderator": false,
        "archaeologist": false,
        "researcher": true,
        "collector": false,
        "sponsor": false
      }*/

      return{
        user: {
          id: user.id,
          username: user.username,
          first_name: user.first_name,
          second_name: user.second_name,
          last_name: user.last_name,
          bio: user.bio,

          vk_id: user.vk_id,
          tg_nickname: user.tg_nickname,
          email: user.email,

          likes:user.likes,
          dislikes:user.dislikes,
          banned: user.banned,

          coutry: {
            id: user.coutry.id,
            name: user.coutry.name},

          moderator: user.moderator,
          archaeologist: user.archaeologist,
          researcher: user.researcher,
          collector: user.collector,
          sponsor: user.sponsor

          },

        component:'Info'
      }
    },
    methods:{
      writemsg:function () {
        alert('writemsg');
      },
      exit:function () {
        alert('exit');
      },
      report:function () {
        alert('set:report?')
      },
    }

  }

</script>
<style>

  #info{
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

  #reportimg {
    float: right;
    width: 10%;
    height: 12%;
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
