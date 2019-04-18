
<template>
  <div id="exppage" class="container-12 d-flex flex-column flex-md-row">

    <div class="col-8 col-sm-8 col-md-4 col-lg-3 align-self-center">
      <img class="expicon" src="../../../img/expicon.png" width="100%" border="60px">
    </div>


    <div class="col-12 col-sm-12 col-md-8 col-lg-9 p-3" style="background: #1b1e23">
      <div class="col-12 d-flex">
        <div v-if="!isedit">
          <h3>{{expedition.name}}</h3>
        </div>

        <div v-else>
          <input v-model="expedition.name">
        </div>

        <h4 class="ml-auto">Статус: {{expedition.stage}}</h4>

        <div v-if="isowner" class="ml-auto" >
          <img @click="edit" src="../../../img/edit.png" width="50px" height="50px">
        </div>
      </div>
      <br>
        <div id="donate">
          <div class="col-12">
          <h3>Собрано {{expedition.current_sum}}$ из  {{expedition.full_sum}}$</h3>

          <progress :value="expedition.current_sum" :max="expedition.full_sum"></progress>
          <br>
          </div>
          <div class="col-12">
            <h3>Донаты</h3>
            <input type="range" min="1" max="100" v-model="donatval" value="5" class="slider" id="myRange">
            <input type="number" v-model="donatval"> USD <a @click="donate" class="btn-3d red">Донатить</a>
          </div>

        </div>
      <br>

      <div v-if="!isedit">
        <p>{{expedition.description}}</p>
      </div>

      <div v-else>
        <textarea class="col-12" v-model="expedition.description"></textarea>
      </div>
    </div>


  </div>
</template>
<script>
  var $ = require('jquery')
  window.jQuery = $ //для подкл jquery
  export default {
    name:'exppage',
    components:{
      $,
    },
    created:function () {

      this.hook();

    },
    data(){
      return{

        expedition:{},

        members:[],
        donatval:5,

        isedit:false,
        
        isowner:false,
        

      }
    },
    methods:{
      hook(){
        var exp = JSON.parse(localStorage.getItem('exppage'));

        $.ajax({
          type:'GET',
          url:'http://localhost:8181/expedition',
          xhrFields: {withCredentials: true},
          data:{
            id:exp.id
          },
          success:(data)=>{
            this.expedition = data;
          },
          error:(msg)=>{
            console.log(msg.responseText);
            console.log(msg.status);
          }

        });

        this.expedition = exp;


        var owner = JSON.parse(localStorage.getItem('client'));
        if (owner.id === this.expedition.head_id){
          this.isowner = true;
        }
      },
      donate(){
        $.ajax({
          async:false,
          type:'POST',
          url:'http://localhost:8181/expedition/donate',
          xhrFields: {withCredentials: true},
          data:{
            expedition_id: this.expedition.id,
            value: this.donatval,
          },
          success:()=>{
            alert('Вы задонатили!')

          },
          error:()=>{
            console.log(msg.responseText);
            console.log(msg.status);
          }

        });
        this.hook();
      },
      edit(){//TODO доделать
        if (this.isedit == true){

          var exp = JSON.parse(localStorage.getItem('exppage'));
          $.ajax({
            async:false,
            type: 'PATCH',
            url: 'http://localhost:8181/expedition',
            xhrFields: {withCredentials: true},
            data:{
              id: this.expedition.id,//exp.id,TODO check
              //stage: 'конец'
              name: this.expedition.name,
              description: this.expedition.description,
              //stage:,
              //full_sum:''
            },
            success:()=>{
              alert('изменения успешны')
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }
          });

        }
        this.isedit = !this.isedit;
        this.hook();
      }
    }
  }
</script>
<style>

  progress { /* контейнер */
    -webkit-appearance: none; /* убрать вид, который задан браузером по умолчанию */
    appearance: none;
    border: none; /* убрать рамку в Firefox */
    width: 100%;
    height: 20px;
    border-radius: 3px;
    color: #4d7198; /* цветная линия-индикатор в IE */
    background: #eee;
    background-image: linear-gradient(to right, #e4e4e4 1px, transparent 1px),
    linear-gradient(rgba(0, 0, 0, .2) 1px, rgba(255, 255, 255, .4) 1px, rgba(0, 0, 0, .2));
    background-size: 10% 100%, 100% 100%;
  }





  .btn-3d {
    position: relative;
    display: inline-block;
    font-size: 18px;
    padding: 10px 40px;
    color: gray;
    border-radius: 6px;
    text-align: center;
    transition: top .01s linear;
    text-shadow: 0 1px 0 rgba(0,0,0,0.15);
  }
  .btn-3d.red:hover    {background-color: darkred ;}
  .btn-3d:active {
    top: 3px;
  }
  .btn-3d.red {
    background-color: darkred;
    box-shadow: 0 0 0 1px darkred inset,
    0 0 0 2px rgba(255,255,255,0.15) inset,
    0 3px 0 0 darkred,
    0 3px 0 1px darkred,
    0 3px 3px 1px rgba(0,0,0,0.5);
  }

  .btn-3d.red:active {
    box-shadow: 0 0 0 1px darkred inset,
    0 0 0 2px rgba(255,255,255,0.15) inset,
    0 0 0 1px rgba(0,0,0,0.4);
  }

  .expicon {
    border-radius: 40%;
  }

  .slider {
     -webkit-appearance: none;
    width: 100%;
    height: 10px;
    background: #d3d3d3;
    outline: none;
    opacity: 0.7;
     -webkit-transition: .2s;
    transition: opacity .2s;
  }

  .slider:hover {
    opacity: 1;
  }

  .slider::-webkit-slider-thumb {
     -webkit-appearance: none;
    appearance: none;
    width: 25px;
    height: 25px;
    border-radius: 50%;
    background: red;
    cursor: pointer;
  }

  .slider::-moz-range-thumb {
    width: 25px;
    height: 25px;
    background: red;
    cursor: pointer;
  }
</style>
