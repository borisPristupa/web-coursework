<template>

  <div>
    <div @input="setinfo">
      <div class="col-12 d-flex flex-row">
        <h1 class="col-4 p-2">Email</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.email" :placeholder="mailplaceholder">
      </div>
      <div class="col-12 d-flex flex-row">
        <h1 class="col-4 p-2">Логин</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.login" :placeholder="loginplaceholder">
      </div>
      <div class="col-12 d-flex flex-row">
        <h1 class=" col-4 p-2">Пароль</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.password" type="password" :placeholder="passplaceholder">
      </div>
      <div class="col-12 d-flex flex-row">
        <h1 class=" col-4 p-2">Повтор</h1><input class="ml-auto col-7 p-2  backlight"  @keyup.enter="reg_in" v-model="aut.rep_pass" type="password" :placeholder="reppassplaceholder">
      </div>
    </div>

    <div class="col-12 d-flex justify-content-center">
      <button class="col-4 signbtn" @keyup.enter="reg_in" @click="reg_in">Регистрация</button>
    </div>
  </div>

</template>

<script>
  export default {
    name:'Auth',
    data(){
      return{
        aut:{
          name:'',
          email:'',
          login:'',
          password:'',
          rep_pass:'',
          hasAccess:false
        },
        mailplaceholder:'Email',
        loginplaceholder:'Логин',
        passplaceholder:'Пароль',
        reppassplaceholder:'Повторите пароль'
      }
    },
    methods:{
      setinfo:function(){
        this.$emit('getinfo',this.aut)
      },
      reg_in:function () {
        if (this.aut.login == 'nursat') { //  надо написать валидацию значений
          this.aut.hasAccess = true;
        }
      },
      same_pass:function () {
        if (this.aut.password != this.aut.rep_pass){
            alert('пароли должны совпадать');
        }
      }
    }
  }
</script>
<style>

</style>


