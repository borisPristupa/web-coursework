<template>

  <div>
    <div @input="setinfo">
      <div class="col-12 d-flex flex-row">
        <h1 class="col-4 p-2 h3">Name</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.name" :placeholder="nameplaceholder">
      </div>
      <div class="col-12 d-flex flex-row">
        <h1 class="col-4 p-2 h3">Surname</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.second_name" :placeholder="secplaceholder">
      </div>
      <div class="col-12 d-flex flex-row">
        <h1 class="col-4 p-2 h3">Last name</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.last_name" :placeholder="lastplaceholder">
      </div>
      <div class="col-12 d-flex flex-row">
        <h1 class="col-4 p-2 h3">Email</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.email" :placeholder="mailplaceholder">
      </div>
      <div class="col-12 d-flex flex-row">
        <h1 class="col-4 p-2 h3">Логин</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.login" :placeholder="loginplaceholder">
      </div>
      <div class="col-12 d-flex flex-row">
        <h1 class=" col-4 p-2 h3">Пароль</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.password" type="password" :placeholder="passplaceholder">
      </div>
      <div class="col-12 d-flex flex-row">
        <h1 class=" col-4 p-2 h3">Повтор</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.rep_pass" type="password" :placeholder="reppassplaceholder">
      </div>
    </div>

    <div class="col-12 d-flex justify-content-center">
      <button class="col-4 signbtn" @keyup.enter="reg_in" @click="reg_in">Регистрация</button>
    </div>
    <h4 class="text-danger">{{errormsg}}</h4>
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

      if (client == true){

        this.$emit('getinfo',client)

      }

    },
    data(){

      return{
        aut:{
          name:'',
          second_name:'',
          last_name:'',
          email:'',
          login:'',
          password:'',
          rep_pass:'',

          hasAccess:false
        },


        nameplaceholder:'Имя',
        secplaceholder:'Фамилия',
        lastplaceholder:'Отчество',
        mailplaceholder:'Почта',
        loginplaceholder:'Логин',
        passplaceholder:'Пароль',
        reppassplaceholder:'Повторите пароль',


        errormsg:'',

      }
    },
    methods:{
      setinfo:function(){
        this.$emit('getinfo',this.aut)
      },
      reg_in:function () {
        // this.same_pass;

        this.setuserdata()
      },
      validation:function () {
        // if (this.aut.password != this.aut.rep_pass){
        //     this.errormsg = 'пароли должны совпадать';
        // }
        this.setuserdata()
      },

      setuserdata:function(){
        var self = this;
        $.ajax({
          type:'POST',
          url: 'http://localhost:8181/sign/up',
          xhrFields: {withCredentials: true},
          data: {
            email: this.aut.email,
            username: this.aut.login,
            password: this.aut.password,
            first_name:this.aut.name,
            second_name:this.aut.second_name,
            last_name:this.aut.last_name,


          },

          success: function(data){
            const parsed = JSON.stringify(data);
            localStorage.setItem('client',parsed);
            alert('Вы успешно зарегестрировались!');
            self.aut.hasAccess = true;
            sessionStorage.setItem('client',JSON.stringify(self.aut.hasAccess));

          },
          error: (msg)=> {
            // if (msg.status == 409) {
            //   this.errormsg = JSON.parse(msg.responseText).error;
            // }
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

</style>


