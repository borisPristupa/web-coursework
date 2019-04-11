<template>
  <div>

    <input id="convname" placeholder="Введите название беседы">
    <button @click="addhuman">Добавить участника</button>
    <input id="msg" v-model="message" placeholder="Введите сообщение">
    <button @click="sendmsg">Отправить</button>



  </div>
</template>
<script>
  export default {
    name:'Openconv',
    data(){
      return{

        message:'Hello World!',

      }

    },
    methods:{
      addhuman:function(){
        alert('human added!')
      },
      sendmsg:function () {
        alert(this.message)
      }
    }
  }

</script>
<style>

</style>
