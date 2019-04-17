<template>
  <div class=" container-12">
    <div class="  create col-12 d-flex flex-column">


      <div class="col-12 d-flex flex-md-row flex-column ">
        <button @click="back" class="col-12 col-sm-12 col-md-3 col-lg-2">назад</button>

        <input class="col-12 col-sm-12 col-md-9 col-lg-10" v-model="convname" id="convname" placeholder="Введите название беседы">

      </div>


      <textarea class="col-12 p-3 m-2" v-model="description" placeholder="Введите описание беседы"></textarea>
      <button class="col-12"@click="createconv">СОЗДАТЬ</button>

    </div>
    <br>


    <div class=" people col-12 overflow-auto">

      <ui class="col-12 d-flex" v-for="(human,index) in choosedpeople">
          <img class="avatar2 " src="../../../img/avatar.png"><b class="convname ">{{human.username}}</b><button class="ml-auto" @click="remove(human,index)">X</button><br>
      </ui>
      <hr>

      <input  v-model="searchText" type="text" placeholder="Поиск людей" @keyup="getpersons" >

      <input @change="getpersons" type="checkbox" id="archaeologist" v-model="archaeologist">
      <label  for="archaeologist">archaeologist</label>
      <input @change="getpersons" type="checkbox" id="researcher" v-model="researcher">
      <label for="researcher"> researcher</label>
      <input @change="getpersons" type="checkbox" id="collector" v-model="collector">
      <label for="collector"> collector </label>
      <input @change="getpersons" type="checkbox" id="sponsor" v-model="sponsor">
      <label for="sponsor"> sponsor </label>

      <hr>
      <ui class="col-12"  v-for="(human,index) in people">
        <div @click="choose(human,index)">
          <img id="avatar" class="avatar2" src="../../../img/avatar.png"><b class="convname">{{human.username}}</b>
        </div>
      </ui>



    </div>





  </div>
</template>
<script>
  var $ = require('jquery');
  window.jQuery = $; //для подкл jquery

  export default {
    name:'Createconv',
    components:{
      $,
    },
    data(){

      return{
        convname:'',
        description:'',

        searchText:'',
        archaeologist:false,
        researcher:false,
        collector:false,
        sponsor:false,

        choosedpeople:[],
        people:[],


        isback:false,

      }

    },
    methods:{
      getpersons(){
        $.ajax(
          {
            type:'GET',
            url:'http://localhost:8181/human/search',
            xhrFields: {withCredentials: true},
            data:{
              amount:100,
              archaeologist:this.archaeologist,
              researcher:this.researcher,
              collector:this.collector,
              sponsor:this.sponsor,
              searchfor:this.searchText,
            },
            success:(data)=>{
              console.log(this.searchText);
              this.people = data;
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }

          },
        )

      },
      choose(human,index){
        this.choosedpeople.push(human);
        this.people.splice(index,1);
      },
      remove(human,index){
        this.people.push(human);
        this.choosedpeople.pop(index,1);
      },
      back:function(){
        this.isback = true;
        this.$emit('isback',this.isback)
      },
      createconv:function () {
          // this.choosedpeople.push(999);
        console.log(this.choosedpeople);
        this.choosedpeopleid = [];
        for (let i = 0; i < this.choosedpeople.length; i++) {
          if (this.choosedpeople[i].id > 0)
            this.choosedpeopleid.push(1 * this.choosedpeople[i].id);
        }

          $.ajax({
            type:'POST',
            url:'http://localhost:8181/chat',
            xhrFields: {withCredentials: true},
            data:{
              id: 31231231,
              name:this.convname,
              "members": this.choosedpeopleid,
              description:this.description,
            },
            success:(data_chat)=>{

              var ggg = JSON.parse(localStorage.getItem('allchats'));
              ggg.push((data_chat));
              localStorage.setItem('allchats',JSON.stringify(ggg));

              localStorage.setItem('chat',JSON.stringify(data_chat));
              console.log(JSON.stringify(data_chat));
              alert('created!')
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }
          })


      }
    }
  }

</script>
<style>

</style>
