<template>
  <div id="expedition" class="container-12 d-md-flex">


    <!--<div class="row">-->
<!--на моб-->
      <div v-if="!isfiltre" class="col-12 d-block d-md-none">
        <button @click="filtrebttn" >Фильтровать</button>
      </div>

      <div v-else class="col-md-3 d-md-block">
        <div id="bycountrymob" class="searchblock d-flex flex-column">
          <p>Поиск по стране</p>
          <ui v-for="country in countries">
            <input type="checkbox" :value="country">{{country}}
          </ui>

        </div>
        <div id="bypricemob" class="searchblock d-flex flex-column">
          <p>Поиск по цене</p>
          <div class="d-flex">
            <input class="col-md-6 m-2" v-model="pricefrom" placeholder="от">
            <input class="col-md-6 m-2" v-model="toprice" placeholder="до">
          </div>
        </div>
        <div id="byyearmob" class="searchblock d-flex flex-column">
          <p>Поиск по году</p>
          <div class="d-flex">
            <input class="col-md-6 m-2" v-model="yearfrom" placeholder="от">
            <input class="col-md-6 m-2" v-model="tillyear" placeholder="до">
          </div>
        </div>
        <div id="bystatemob" class="searchblock d-flex flex-column">
          <p>Поиск по состоянию</p>
          <ui v-for="state in states">
            <input type="checkbox">{{state}}
          </ui>
        </div>
        <button @click="filtrebttn">Готово</button>
      </div>

<!--на комп-->
    <div class="col-md-3 d-none d-md-block">
      <div id="bycountry" class="searchblock d-flex flex-column">
        <p>Поиск по стране</p>
        <ui v-for="country in countries">
          <input type="checkbox" :value="country">{{country}}
        </ui>

      </div>
      <div id="byprice" class="searchblock d-flex flex-column">
        <p>Поиск по цене</p>
        <div class="d-flex">
          <input class="col-md-5" v-model="pricefrom" placeholder="от">
          <samp class="col-md-2"></samp>
          <input class="col-md-5" v-model="toprice" placeholder="до">
        </div>
      </div>
      <div id="byyear" class="searchblock d-flex flex-column">
        <p>Поиск по году</p>
        <div class="d-flex">
          <input class="col-md-5" v-model="yearfrom" placeholder="от">
          <samp class="col-md-2"></samp>
          <input class="col-md-5" v-model="tillyear" placeholder="до">
        </div>
      </div>
      <div id="bystate" class="searchblock d-flex flex-column">
        <p>Поиск по состоянию</p>
        <ui v-for="state in states">
          <input type="checkbox">{{state}}
        </ui>
      </div>
    </div>



      <div class="col-12 col-md-9">
        <div class="col-12 p-3 d-none d-md-block">
          <a> Here is page number 1 2 3 4 5 6 7 8</a>
        </div>



        <div class="expeditions  mr-md-2 ">
          <ui v-for="exp in expeditions">
            <h3>{{exp}}</h3>
          </ui>
        </div>
        <!--<div style="width: 300px; height: 200px;">-->
          <!--<script type="text/javascript" charset="utf-8" src="https://api-maps.yandex.ru/services/constructor/1.0/js/?um=constructor%3A053bd947d462cc1a45aeba4070defff75501905071c0eaf68436ac9976ec698c&amp;width=514&amp;height=326"></script>-->
        <!--</div>-->
      </div>


    <!--</div>-->


  </div>
</template>
<script>
  export default {
    name:'Expedition',
    data(){
      return{
        expeditions:[
          'exp1',
          'exp2',
          'exp3',
          'exp4'

        ],
        states:[
          'type1',
          'type2',
          'type3',
          'type4'
        ],
        countries:[
          'test1',
          'test2',
          'test3',
          'test4'
        ],
        isfiltre:false,



      }
    },
    methods:{
      filtrebttn:function () {
        this.isfiltre = !this.isfiltre
      }
    }
  }
</script>
<style>
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
