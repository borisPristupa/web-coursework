<template>

  <div>
    <h1>email</h1> <input v-model="email">

    <h1>Логин</h1> <input v-model="login">

    <h1>Пароль</h1> <input v-model="password" type="password" name="">

    <h1>Повторите пароль</h1> <input v-model="rep_pass" type="password" name="">

    <button @click="reg_in">Регистрация</button>



    <!--      <button id="authorize_button" style="display: none;">Authorize</button>        авторизация Google-->
    <!--      <button id="signout_button" style="display: none;">Sign Out</button>-->

    <!--      <pre id="content" style="white-space: pre-wrap;"></pre>-->

  </div>

</template>

<script>
  export default {
    name:'Auth',
    data(){
      return{
        email:'',
        login:'',
        password:'',
        rep_pass:''
      }
    },
    methods:{
      reg_in:function () {
        alert('try to reg   ' + this.email +'  '+ this.login +'  '+this.password+'  '+this.rep_pass )
        this.same_pass()
      },

      same_pass:function () {
        if (this.password != this.rep_pass){
            alert('пароли должны совпадать');
        }
      }
    }
  }
</script>
