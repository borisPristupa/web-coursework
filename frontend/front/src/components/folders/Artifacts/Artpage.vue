
<template>
  <div id="artpage" class="container-12 d-flex flex-column flex-md-row bg-dark">

    <div class="col-8 col-sm-8 col-md-4 col-lg-3 align-self-center">
      Аукцион
      <p>{{this.artifact.auction.start_time}}=>{{this.artifact.auction.end_time}}</p>
      <!--<button @click="subscribe">Подписаться</button>-->
      <p>{{this.artifact.auction.price_old}}$=>{{this.artifact.auction.price_new}}$</p>
      <p>Поднял: {{this.artifact.auction.raiser.username}}</p>
      <!--<p>{{this.artifact.auction.raiser.first_name}}/{{this.artifact.auction.raiser.second_name}}/{{this.artifact.auction.raiser.last_name}}</p>-->

      <input type="number" v-model="new_bet"> USD <a @click="rate" class="btn-3d red">Ставка</a>
      <!--@change="validate"-->
      <p>{{errormsg}}</p>
      {{new_bet}}
    </div>


    <div class="col-12 col-sm-12 col-md-8 col-lg-9 p-3 bg-dark">
      <!--style="background: #1b1e23"-->
      <div class="col-12 d-flex flex-column flex-md-row">

        <div class="col-12 col-md-4 col-lg-3">
          <img class="articon" src="../../../img/artifact.png">
        </div>
        <div class="col-12 col-md-8 col-lg-9">

          <div v-if="!isedit">
            <h3>{{this.artifact.name}}</h3>
          </div>

          <div v-else>
            <input v-model="this.artifact.name">
          </div>

          <div v-if="isowner" class="ml-auto">
            <img @click="edit" src="../../../img/edit.png" width="50px" height="50px">
          </div>

        </div>

      </div>
      <br>
      
      <div v-if="!isedit">
        <p>{{this.artifact.description}}</p>
        <!--description-->
      </div>
      <div v-else>
        <textarea class="col-12" v-model="artifact.description"></textarea>
      </div>


    </div>


  </div>
</template>
<script>
  var $ = require('jquery')
  window.jQuery = $ //для подкл jquery
  export default {
    name:'Artpage',
    components:{
      $,
    },
    created:function () {
      this.hook();
    },
    data(){
      return{

        artifact:{},

        new_bet:'',

        isedit:false,

        isowner:false,

        auctionprice: '',

        errormsg:'',

      }
    },
    methods:{
      hook(){
        // alert('test')

        var art = JSON.parse(localStorage.getItem('artpage'));

        var time = art.auction.start_time;
        art.auction.start_time = time.replace(/.{12}$/, '').replace( /[T]/,' ');

        var time = art.auction.end_time;
        art.auction.end_time = time.replace(/.{12}$/, '').replace( /[T]/,' ');

        console.log(art.auction.price_old);
        this.artifact = art;



        var owner = JSON.parse(sessionStorage.getItem('client'));

        console.log(art.owner.username);
        console.log(owner.login);

        if (owner.login === art.owner.username){
          this.isowner = true;
        }
      },
      validate(){
        if (this.new_bet < this.artifact.auction.price_new) {
          this.new_bet = this.artifact.auction.price_new;
          alert('nope!')
          // this.errormsg = 'nope!';
        }else{
          this.errormsg = '';
        }
      },
      // subscribe(){
      //   alert('подписались');
      // },
      rate(){
        // alert('rate')
        $.ajax({
          async:false,
          type: 'PUT',
          url: 'http://localhost:8181/artifact/bet',
          xhrFields: {withCredentials: true},
          data:{
            auction_id:this.artifact.auction.id,
            value: this.new_bet,
          },
          success:()=>{
            alert('ставка сделана!')
          },
          error:(msg)=>{
            if (msg.status == 500) {
              this.errormsg = 'новое значение меньше старого!'

            }
            console.log(msg.responseText);
            console.log(msg.status);
          }
        });
      },
      edit(){//TODO доделать
        if (this.isedit == true){

          var exp = JSON.parse(localStorage.getItem('exppage'));
          alert(exp)
          $.ajax({
            async:false,
            type: 'PATCH',
            url: 'http://localhost:8181/artifact',
            xhrFields: {withCredentials: true},
            data:{
              id: this.artifact.id,
              name: this.artifact.name,
              description: this.artifact.description,
              age: this.artifact.age,
              type: this.artifact.type,
              country: this.artifact.country
            },
            success:()=>{
              alert('new name: ' + this.artifact.name)
            },
            error:(msg)=>{
              console.log(msg.responseText);
              console.log(msg.status);
            }
          });

        }
        this.isedit = !this.isedit;
      },

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

  .articon {
    border-radius: 40%;
    width: 100%;
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
