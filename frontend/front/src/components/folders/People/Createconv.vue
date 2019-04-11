<template>
  <div>
    <button @click="back">назад</button>
    <input id="convname" placeholder="Введите название беседы">
    <button @click="addhuman">Добавить участника</button>
    <button @click="createconv">СОЗДАТЬ</button>






  </div>
</template>
<script>
  export default {
    name:'Createconv',
    data(){
      return{
        isback:false
      }

    },
    methods:{
      back:function(){
        this.isback = true
        this.$emit('isback',this.isback)
      },
      addhuman:function(){
        alert('human added!')
      },
      createconv:function () {
        alert('create!')
      }
    }
  }

</script>
<style>

</style>
