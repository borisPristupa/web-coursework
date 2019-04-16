<template>
  <div id="artifact">



    <div v-if="!isartpage" class="container-12 d-md-flex">

  <!--на моб-->

      <div v-if="!isfiltre" class="col-12 d-block d-md-none">
        <button class="backbutton" @click="filtrebttn" >Фильтровать</button>
      </div>

      <div v-else class="col-md-3 d-md-block">
        <div id="bytypemob" class="searchblock d-flex flex-column">
          <p>Поиск по типу</p>
          <a v-for="type in types">
            <input type="checkbox" :value="type" v-model="checkedtypes" @change="findart" >{{type}}

          </a>
        </div>

        <div id="bypricemob" class="searchblock d-flex flex-column">
          <p>Поиск по цене</p>
          <div class="d-flex flex-column">
            <div class="col-12 p-0">
              <input name="byprice" type="checkbox" v-model="on_sale"><label for="byprice">Продается</label>
            </div>
            <div v-if="on_sale" class="d-flex col-12 p-0">
              <input class="col-5" type="number" @change="findart" v-model="pricefrom" placeholder="от">
              <samp class="col-2">→</samp>
              <input class="col-5 ml-auto" type="number" @change="findart" v-model="toprice" placeholder="до">
            </div>
          </div>
        </div>

        <div id="byyearmob" class="searchblock d-flex flex-column">
          <p>Поиск по возрасту</p>
          <div class="d-flex">
            <select class="col-md-5 bg-light" type="number" @change="findart"  v-model="checkedages[0]">
              <option disabled selected>от</option>
              <option v-for="age in ages" :value="age" >{{age}}</option>
            </select>
            <samp class="col-md-2">→</samp>
            <select class="col-md-5 bg-light" type="number" @change="findart"  v-model="checkedages[1]">
              <option disabled selected>до</option>
              <option v-for="age in ages" :value="age" >{{age}}</option>
            </select>
          </div>
        </div>

        <div id="bycountrymob" class="searchblock d-flex flex-column">
          <p>Поиск по стране</p>
          <select id="countryselectmob" @change="findart">
            <option v-for="country in countries" v-model="checkedcountry" @change="findart" >
              {{country}}
            </option>
          </select>
        </div>
        <button class="backbutton" @click="filtrebttn">Готово</button>
      </div>

  <!--на комп-->
     <div class="col-md-3 d-none d-md-block">
        <div id="bytype" class="searchblock d-flex flex-column">
          <p>Поиск по типу</p>
          <a v-for="type in types">
            <input type="checkbox" :value="type" v-model="checkedtypes" @change="findart" >{{type}}

          </a>
        </div>

        <div id="byprice" class="searchblock d-flex flex-column">
          <p>Поиск по цене</p>
          <div class="d-flex flex-column">
            <div class="col-12 p-0">
              <input name="byprice" type="checkbox" v-model="on_sale"><label for="byprice">Продается</label>
            </div>
            <div v-if="on_sale" class="d-flex col-12 p-0">
              <input class="col-5" type="number" @change="findart" v-model="pricefrom" placeholder="от">
              <samp class="col-2">→</samp>
              <input class="col-5 ml-auto" type="number" @change="findart" v-model="toprice" placeholder="до">
            </div>
          </div>
        </div>

        <div id="byyear" class="searchblock d-flex flex-column">
          <p>Поиск по возрасту</p>
          <div class="d-flex">
            <select class="col-md-5 bg-light" type="number" @change="findart"  v-model="checkedages[0]">
              <option disabled selected>от</option>
              <option v-for="age in ages" :value="age" >{{age}}</option>
            </select>
            <samp class="col-md-2">→</samp>
            <select class="col-md-5 bg-light" type="number" @change="findart"  v-model="checkedages[1]">
              <option disabled selected>до</option>
              <option v-for="age in ages" :value="age" >{{age}}</option>
            </select>
          </div>
        </div>

        <div id="bycountry" class="searchblock d-flex flex-column">
          <p>Поиск по стране</p>
          <select id="countryselect" @change="findart">
            <option v-for="country in countries" v-model="checkedcountry" @change="findart" >
              {{country}}
            </option>
          </select>
        </div>
      </div>



      <div class="col-12 col-md-9">
        <div class="col-12 p-3 d-none d-md-block">
          <!--<a> Here is page number 1 2 3 4 5 6 7 8</a>-->
        </div>



        <div class="col-12 artifacts  mr-md-2 ">
          <ul v-for="art in artifacts" class="d-flex " @click="artpage(art)">

            <img class="articonmini" src="../../../img/artifact.png" >

            <div class="col-12"  >
              <em class="col-6">{{art.name}}</em> <em class="col-6">{{art.current_sum}}</em>
              <br>
              <em>{{art.description}}...</em>
            </div>

          </ul>
        </div>
      </div>


    </div>



    <div v-else  class="container-12 d-flex flex-column">
      <div class="col-12">
        <button class="col-4 col-sm-3 col-md-2 col-lg-1 backbutton" @click="artpage(null)">back</button>
      </div>
      <div class="col-12">
        <artpage></artpage>
      </div>


    </div>

</div>
</template>
<script>
  var $ = require('jquery')
  window.jQuery = $; //для подкл jquery

  import Artpage from './Artpage'


  export default {
    name:'Artifact',
    components: {
      $,Artpage
    },
    created(){
      this.gettypes();
      this.getages();
      this.getcountries();
      this.hook();


    },
    data(){




      return{
        isartpage:false,

        isfiltre:false,


        types:[],
        checkedtypes:[],


        on_sale:false,
        pricefrom:'',
        toprice:'',

        ages:[],
        checkedages:[],

        countries:[],
        checkedcountry:'Казахстан',

        artifacts:[],

      }
    },
    methods:{
      hook(){
        var r = JSON.parse(localStorage.getItem('isartpage'));
        var isartpage = JSON.parse(r);

        this.findart();

        if(isartpage == true){
          this.isartpage = true;
        }else {
          return
        }
      },
      filtrebttn:function () {
        this.isfiltre = !this.isfiltre
      },

      gettypes(){
        $.ajax({//для типов
          async:false,
          type:'GET',
          url: 'http://localhost:8181/artifact/types',
          xhrFields: {withCredentials: true},
          data: {},
          success:(data)=>{
            var types = JSON.stringify(data)//test json
            // console.log(types);
            this.types = data;
            // const parsed = JSON.stringify(this.artifacts);
            // localStorage.setItem('artifacts',parsed);

          },
          error: (msg)=> {
            console.log(msg.responseText);
            console.log(msg.status);
            self.errormsg = 'Сервер передал: '+ JSON.parse(msg.responseText).error;
          }
        });
      },
      getcountries(){
        $.ajax({//для типов
          async:false,
          type:'GET',
          url: 'http://localhost:8181/country',
          xhrFields: {withCredentials: true},
          data: {},
          success:(data)=>{
            this.countries = data;
            // const parsed = JSON.stringify(this.artifacts);
            // localStorage.setItem('artifacts',parsed);

          },
          error: (msg)=> {
            console.log(msg.responseText);
            console.log(msg.status);
            self.errormsg = 'Сервер передал: '+ JSON.parse(msg.responseText).error;
          }
        });
      },
      getages(){
        $.ajax({//для возраст
          async:false,
          type:'GET',
          url: 'http://localhost:8181/age',
          xhrFields: {withCredentials: true},
          data: {},
          success:(data)=>{
            this.ages = data;
            // const parsed = JSON.stringify(this.artifacts);
            // localStorage.setItem('artifacts',parsed);

          },
          error: (msg)=> {
            console.log(msg.responseText);
            console.log(msg.status);
            self.errormsg = 'Сервер передал: '+ JSON.parse(msg.responseText).error;
          }
        });
      },
      findart(){

        if (this.artifacts.length == 0) {
          $.ajax({
            type:'GET',
            url: 'http://localhost:8181/artifact/search',
            xhrFields: {withCredentials: true},
            data: {

              "types[]": this.types,
              "ages[]": '', // типа пустой массив
              amount:100,
              // page:'',
              // on_auction:,

            },
            success:(data)=>{
              var art = JSON.stringify(data)//test json
              // console.log(art);
              this.artifacts = data;
              const parsed = JSON.stringify(this.artifacts);
              localStorage.setItem('artifacts',parsed);

            },
            error: (msg)=> {
              console.log(msg.responseText);
              console.log(msg.status);
              self.errormsg = 'Сервер передал: '+ JSON.parse(msg.responseText).error;
            }
          });
        }else{
          if (this.checkedtypes.length == 0){
            this.checkedtypes = this.types;
          }

          $.ajax({
            type:'GET',
            url: 'http://localhost:8181/artifact/search',
            xhrFields: {withCredentials: true},
            data: {

              "types[]": this.checkedtypes,
              "ages[]": this.checkedages, // типа пустой массив
              "country": this.checkedcountry,
              amount:'',
              page:'',
              on_auction:this.on_sale,

              price_min: this.pricefrom,
              price_max: this.toprice,

            },
            success:(data)=>{

              var art = JSON.stringify(data)//test json
              this.artifacts = data;
              const parsed = JSON.stringify(this.artifacts);
              localStorage.setItem('artifacts',parsed);
              if (this.toprice==9999999) {
                this.toprice = '';
              }


            },
            error: (msg)=> {
              console.log(msg.responseText);
              console.log(msg.status);
              self.errormsg = 'Сервер передал: '+ JSON.parse(msg.responseText).error;
            }
          });
        }

      },


      artpage(art){
        var str = JSON.stringify(art)
        localStorage.setItem('artpage',str)
        this.isartpage = !this.isartpage;
        localStorage.setItem('isartpage',JSON.stringify(this.isartpage));
      },

    }

  }
</script>
<style>
  #countryselect {
    color: black;
    background: whitesmoke;
  }
  .articonmini{
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


  #artifact{
    color: lightgray;
  }
  .artifacts {
    padding: 15px;
    background: #1b1e23;
  }
  .searchblock {
    padding: 10px;
    margin: 15px;
    background: #1b1e23;
  }
</style>
