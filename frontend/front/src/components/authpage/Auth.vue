<template>

    <div id="inner">

      <div @input="setinfo" class="row">
        <div class="col-12 d-flex flex-row">
          <h1 class="col-4 p-2">Логин</h1><input class="ml-auto col-7 p-2  backlight" name="login" @keyup.enter="validation" v-model="aut.login" :placeholder="loginplaceholder">
        </div>

        <div class="col-12 d-flex flex-row">
          <h1 class=" col-4 p-2">Пароль</h1><input class="ml-auto col-7 p-2  backlight" name="password" @keyup.enter="validation" v-model="aut.password" type="password" :placeholder="passplaceholder">
        </div>
        <samp class="col-12 d-flex justify-content-center">{{this.errormsg}}</samp>
        <div class="col-12 d-flex justify-content-center">
          <button class="col-4 signbtn"  @keyup.enter="validation"  @click="validation">Вход</button>
        </div>

      </div>

    </div>

</template>



<script>
  var $ = require('jquery')
  window.jQuery = $ //для подкл jquery

  export default {
    name:'Auth',
    components:{
      $//для подкл jquery
    },
    beforeCreate: function () {

        var client = JSON.parse(sessionStorage.getItem('client'));

        // alert(client.hasAccess);
        if (client.hasAccess !== true){
          return
        }
        else if (client.hasAccess === true){

          $.ajax({
            async: false,
            type:'POST',
            url: 'http://localhost:8181/sign/in',
            xhrFields: {withCredentials: true},
            data: {
              username: client.login,
              password: client.password
            },
            success: (data)=>{

              if(window.localStorage) {

                const parsed = JSON.stringify(data);

                localStorage.setItem('client',parsed);

                this.$emit('getinfo',client)

              } else {

              }
            },
            error: (msg)=> {
              client.hasAccess = false;
              console.log(msg.responseText);
              console.log(msg.status);

              self.errormsg = 'Сервер передал: '+ JSON.parse(msg.responseText).error;

            }
          });

        }

    },
    data(){


      return {
        aut:{
          login: '',
          password: '',
          hasAccess: false,
        },


        errormsg:'',

        loginplaceholder:'Логин',
        passplaceholder:'Пароль'


      }


    },
    methods:{
      setinfo:function(){
        this.$emit('getinfo',this.aut)
      },
      validation:function(){
        if (this.aut.login ===''){
          this.loginplaceholder = 'строка пуста'
        }else if (this.aut.password ===''){
          this.passplaceholder = 'строка пуста'
        }else {
          this.setuserdata();// тут обработка запроса
        }
      },

      setuserdata:function(){
          var self = this;
          $.ajax({
            type:'POST',
            url: 'http://localhost:8181/sign/in',
            xhrFields: {withCredentials: true},
            data: {
              username: this.aut.login,
              password: this.aut.password
            },
            success: (data)=>{

              if(window.localStorage) {

                const parsed = JSON.stringify(data);
                localStorage.setItem('client',parsed);
                self.aut.hasAccess = true;
                sessionStorage.setItem('client',JSON.stringify(self.aut));
              } else {
              }
            },
            error: (msg)=> {
              self.aut.hasAccess = false;
              console.log(msg.responseText);
              console.log(msg.status);

              self.errormsg = 'Сервер передал: '+ JSON.parse(msg.responseText).error;

            }
          });

      },
    }
  }
</script>

<style>
  .signbtn{
    color: whitesmoke;
    background: #5c060d;
    border: #5c060d;
  }
  #inner {
    color: whitesmoke;
  }
  .backlight {
    display: block;
    width: 100%;
    max-width: 300px;
    box-sizing: border-box;
    margin: 0 auto 10px;
    padding: 10px;
    border: 1px solid #000;
    border-radius: 4px;
    box-shadow: inset 0 2px 10px 1px rgba(0,0,0,.3), inset 0 0 0 60px rgba(0,0,0,.3), 0 1px rgba(255,255,255,.08);
    background: linear-gradient(rgb(70,70,70), rgb(120,120,120));
    color: #ccc;
    transition: .5s linear;
  }
  .backlight:focus {
    outline: none;
    box-shadow: inset 0 1px 3px 1px  rgba(0,0,0,.5), inset 0 0 0 60px rgba(0,0,0,0), 0 1px rgba(255,255,255,.08);
  }

</style>
