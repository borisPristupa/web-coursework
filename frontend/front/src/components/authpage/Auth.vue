<template>

    <div id="inner">

      <div @input="setinfo" class="row">
        <div class="col-12 d-flex flex-row">
          <h1 class="col-4 p-2">Логин</h1><input class="ml-auto col-7 p-2  backlight" name="login" @keyup.enter="test" v-model="aut.login" :placeholder="loginplaceholder">
        </div>

        <div class="col-12 d-flex flex-row">
          <h1 class=" col-4 p-2">Пароль</h1><input class="ml-auto col-7 p-2  backlight" name="password" @keyup.enter="test" v-model="aut.password" type="password" :placeholder="passplaceholder">
        </div>
        <div class="col-12 d-flex justify-content-center">
          <button class="col-4 signbtn" @keyup.enter="test" @click="test">Вход</button>
        </div>
      </div>

    </div>

</template>

<script>
  export default {
    name:'Auth',
    data(){
      return {
        aut:{
          login: '',
          password: '',
          hasAccess: false},
        loginplaceholder:'Логин',
        passplaceholder:'Пароль'
      }
    },
    methods:{
      setinfo:function(){
        this.$emit('getinfo',this.aut)
      },
      test:function(){
        if (this.aut.login ==''){
          this.loginplaceholder = 'строка пуста'
        }else if (this.aut.password ==''){
          this.passplaceholder = 'строка пуста'
        }else if (this.aut.login == 'n' && this.aut.password == 'q') {   // тут обработка запроса
          this.aut.hasAccess = true;
        }
      },

      sign_in:function () {
        Auth.login;
        alert('try to sign   ' + this.login +'  '+this.password )
      }
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
