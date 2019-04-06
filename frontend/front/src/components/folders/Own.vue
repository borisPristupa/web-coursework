<template>
  <h3>{{title}}</h3>
  <Info></Info>
</template>

<script>
import Info from './Own/Info'
  export default {
    name:'Own',
    data(){

      return{
        title:'Биография',
        component:'Info'
      }
    },
    components:{
      Info
    }
  }

</script>


<style>


</style>
