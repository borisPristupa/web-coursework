<template>

    <div>
      <h1>Логин</h1> <input v-model="login">

      <h1>Пароль</h1> <input v-model="password" type="password" name="">

      <button @click="sign_in">Войти</button>



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
        login:'',
        password:''
      }
    },
    methods:{
      sign_in:function () {
        this.login
        alert('try to sign   ' + this.login +'  '+this.password )
      }
    }
  }
</script>
