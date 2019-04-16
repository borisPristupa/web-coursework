<template>
  <div id="expedition">


    <!--<div class="row">-->

    <div v-if="!isexppage" class="container-12 d-md-flex">

<!--на моб-->
      <div v-if="!isfiltre" class="col-12 d-block d-md-none">
        <button class="backbutton" @click="filtrebttn" >Фильтровать</button>
      </div>

      <div v-else class="col-md-3 d-md-block">
        <!--<div id="bycountrymob" class="searchblock d-flex flex-column">-->
          <!--<p>Поиск по стране</p>-->
          <!--<ui v-for="country in countries">-->
            <!--<input type="checkbox" :value="country">{{country}}-->
          <!--</ui>-->

        <!--</div>-->
        <div id="bypricemob" class="searchblock d-flex flex-column">
          <p>Поиск по цене</p>
          <div class="d-flex">
            <input type="number" class="col-md-6 m-2" @change="findexp" v-model="pricefrom" placeholder="от">
            <input type="number" class="col-md-6 m-2" @change="findexp" v-model="toprice" placeholder="до">
          </div>
        </div>
        <!--<div id="byyearmob" class="searchblock d-flex flex-column">-->
          <!--<p>Поиск по году</p>-->
          <!--<div class="d-flex">-->
            <!--<input class="col-md-6 m-2" v-model="yearfrom" placeholder="от">-->
            <!--<input class="col-md-6 m-2" v-model="tillyear" placeholder="до">-->
          <!--</div>-->
        <!--</div>-->
        <div id="bystatemob" class="searchblock d-flex flex-column">
          <p>Поиск по состоянию</p>
          <a v-for="state in states">
            <input type="checkbox">{{state}}
          </a>
        </div>
        <button class="backbutton" @click="filtrebttn">Готово</button>
      </div>

<!--на комп-->
    <div class="col-md-3 d-none d-md-block">
      <!--<div id="bycountry" class="searchblock d-flex flex-column">-->
        <!--<p>Поиск по стране</p>-->
        <!--<ui v-for="country in countries">-->
          <!--<input type="checkbox" :value="country">{{country}}-->
        <!--</ui>-->

      <!--</div>-->
      <div id="byprice" class="searchblock d-flex flex-column">
        <p>Поиск по цене</p>
        <div class="d-flex">
          <input type="number" class="col-md-5" @change="findexp" v-model="pricefrom" placeholder="от">
          <samp class="col-md-2"></samp>
          <input type="number" class="col-md-5" @change="findexp" v-model="toprice" placeholder="до">
        </div>
      </div>
      <!--<div id="byyear" class="searchblock d-flex flex-column">-->
        <!--<p>Поиск по году</p>-->
        <!--<div class="d-flex">-->
          <!--<input class="col-md-5" v-model="yearfrom" placeholder="от">-->
          <!--<samp class="col-md-2"></samp>-->
          <!--<input class="col-md-5" v-model="tillyear" placeholder="до">-->
        <!--</div>-->
      <!--</div>-->
      <div id="bystate" class="searchblock d-flex flex-column">
        <p>Поиск по состоянию</p>
        <a v-for="state in states">
          <input  type="checkbox" @change="findexp"  :value="state" v-model="checkedstates" >{{state}}
        </a>
      </div>

    </div>



      <div class="col-12 col-md-9">
        <div v-if="iscreateexp" class="col-12 p-3 d-none d-md-block">
          <button @click="iscreateexppage">Назад</button>



          <!--<div class="col-8 col-sm-8 col-md-4 col-lg-3 align-self-center">-->
            <!--<img class="expicon" src="../../../img/expicon.png" width="100%" border="60px">-->
          <!--</div>-->


          <!--<div class="col-12 col-sm-12 col-md-8 col-lg-9 p-3" style="background: #1b1e23">-->
            <div class="col-12 d-flex">

              Имя:<input v-model="expeditionnew.name">


              <h4 class="ml-auto">Статус: {{expeditionnew.stage}}</h4>

            </div>
              Полная сумма:<input type="number" v-model="expeditionnew.fullpricenew" >$
            <br>


              Описание:<textarea class="col-12" v-model="expeditionnew.description"></textarea>

          <button @click="createexp">Создать</button>

          <!--</div>-->
        </div>



        <div v-else class="col-12 expeditions  mr-md-2 ">
          <button @click="iscreateexppage">Создать</button>

          <a v-for="exp in expeditions" class="d-flex " @click="exppage(exp)">

            <img class="expiconmini" src="../../../img/expicon.png" >

              <div class="col-12"  >
                <em class="col-6">{{exp.name}}</em> <em class="col-6">{{exp.current_sum}}</em>
                <br>
                <em>{{exp.description}}...</em>
              </div>

          </a>
        </div>
      </div>


    </div>



    <div v-else  class="container-12 d-flex flex-column">
      <div class="col-12">
        <button class="col-4 col-sm-3 col-md-2 col-lg-1 backbutton" @click="exppage">back</button>
      </div>
      <div class="col-12">
        <exppage></exppage>
      </div>


    </div>

    <!--</div>-->


  </div>
</template>
<script>
  import Exppage from './Exppage'
  var $ = require('jquery')
  window.jQuery = $; //для подкл jquery


  export default {
    name:'Expedition',
    components: {
      Exppage,
      $,
    },

    created(){
      this.hook();
    },
    data(){
      this.defexpeds();
      this.getstates();

      return{
        expeditions:[],
        states:[],
        countries:[],


        expeditionnew:{},
        fullpricenew:'',


        pricefrom:'',
        toprice:'',


        checkedstates:[

        ],
        checkedcountries:[],


        isfiltre:false,
        errormsg:'',
        isexppage:false,
        iscreateexp:false,
      }
    },
    methods:{
      hook(){
        var r = JSON.parse(localStorage.getItem('exppage'));
        var isexppage = JSON.parse(localStorage.getItem('isexppage'));

        if(isexppage == true){
          this.isexppage = isexppage;
          var str = JSON.stringify(r);
          localStorage.setItem('exppage',str);
          localStorage.setItem('isexppage',isexppage);
        }else {return}
      },
      filtrebttn:function () {
        this.isfiltre = !this.isfiltre
      },

      iscreateexppage(){
        this.iscreateexp = !this.iscreateexp;
      },
      createexp(){
        $.post({
          url:'http://localhost:8181/expedition',
          xhrFields:{withCredentials:true},
          data:{
            name:this.expeditionnew.name,
            "stage": "Сбор средств",
            full_sum:this.expeditionnew.fullpricenew,
            description: this.expeditionnew.description,
          },
          success:(data)=>{
            alert('test');
            this.iscreateexp = false;

          },
          error:(msg)=>{
            console.log(msg.responseText);
            console.log(msg.status);
          }

        })
      },

      getstates(){
        console.log('test');
        $.ajax({
          type:'GET',
          url:'http://localhost:8181/stage',
          xhrFields: {withCredentials: true},

          success:(data)=>{
            this.states = data;
          },
          error:(msg)=>{
            console.log(msg.responseText);
            console.log(msg.status);
          }

        })
      },

      defexpeds:function(){
        var self = this;
        $.ajax({
          type:'GET',
          url: 'http://localhost:8181/expedition/search',
          xhrFields: {withCredentials: true},
          data: {
            "stages[]":["Сбор средств","В процессе","Окончена"],
            members_min: 1,
            members_max: 100,
            full_sum_min: 0,
            full_sum_max: 99999999
          },
          success: function(data){

            var exp = JSON.stringify(data)//test json
            self.expeditions = data;
            const parsed = JSON.stringify(self.expeditions);
            localStorage.setItem('expeditions',parsed);
          },
          error: (msg)=> {
            console.log(msg.responseText);
            console.log(msg.status);
            self.errormsg = 'Сервер передал: '+ JSON.parse(msg.responseText).error;
          }
        });

      },

      findexp(){
        var self = this;
        if (self.pricefrom == ''){self.pricefrom=1}
        if (self.toprice == ''){self.toprice=99999999}
        if (self.states === undefined || self.states == null || self.states == [] ){
          self.checkedstates = ["Сбор средств","В процессе","Окончена"];
        }



        $.ajax({
          type:'GET',
          url: 'http://localhost:8181/expedition/search',
          xhrFields: {withCredentials: true},
          data: {
            "stages[]":self.checkedstates,
            members_min: 0,
            members_max: 100,
            full_sum_min: self.pricefrom,
            full_sum_max: self.toprice//self.full_sum_max,

          },
          success: function(data){

            var exp = JSON.stringify(data)//test json
            self.expeditions = data;
            const parsed = JSON.stringify(self.expeditions);
            localStorage.setItem('expeditions',parsed);
            if (self.toprice==9999999) {
              self.toprice = '';
            }


          },
          error: (msg)=> {
            console.log(msg.responseText);
            console.log(msg.status);
            self.errormsg = 'Сервер передал: '+ JSON.parse(msg.responseText).error;
          }
        });

        this.hook();

      },

      exppage(exp){
        var str = JSON.stringify(exp)
        localStorage.setItem('exppage',str)
        this.isexppage = !this.isexppage;

        localStorage.setItem('isexppage',JSON.stringify(this.isexppage));
      },
    }
  }
</script>
<style>
  .expiconmini{
    float: left;
    border-radius: 20%;
    height: 50px;
    width: 50px;
  }
  .backbutton{
    background-color: #1b1e23;
    border: none;
    color: white;
    padding: 10px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 20px;
    margin: 4px 2px;
    -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
    cursor: pointer;
    border-radius: 20px;
  }
  .backbutton {
    background-color: #474953;
    color: black;
    border: 2px solid #1b1e23;
  }

  .backbutton:hover {
    background-color: #1b1e23;
    color: white;
  }


  #expedition{
    color: lightgray;
  }
  .expeditions {
    padding: 15px;
    background: #1b1e23;
  }
  .searchblock {
    padding: 10px;
    margin: 15px;
    background: #1b1e23;
  }
</style>
