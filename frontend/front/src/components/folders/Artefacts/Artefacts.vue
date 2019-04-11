<template>
  <div id="artefacts" class="container-12 d-flex">
    <div class="col-3">
      <div id="bycountry" class="searchblock d-flex flex-column">
        <p>Поиск по стране</p>
        <ui v-for="country in countries">
          <input type="checkbox" :value="lcountry">{{country}}
        </ui>

      </div>
      <div id="byprice" class="searchblock d-flex flex-column">
        <p>Поиск по цене</p>
        <div>
          <input name="byprice" type="checkbox"><label for="byprice">Продается</label>
        </div>
        <div class="d-flex">
          <input class="col-5" v-model="pricefrom" placeholder="от">
          <samp class="col-2">→</samp>
          <input class="col-5 ml-auto" v-model="toprice" placeholder="до">
        </div>
      </div>
      <div id="bylocationcountry" class="searchblock d-flex flex-column">
        <p>Поиск по стр. содержания</p>
        <ui v-for="country in countries">
          <input type="checkbox" :value="lcountry">{{country}}
        </ui>
      </div>
      <div id="bytype" class="searchblock d-flex flex-column">
        <p>Поиск по типу</p>
        <ui v-for="type in types">
          <input type="checkbox" :value="type">{{type}}
        </ui>
      </div>
    </div>
    <div class="col-9 ">
      <div class="col-12">
        <a> Here is page number 1 2 3 4 5 6 7 8</a>
      </div>
      <div class="arts">
        <ui v-for="country in countries">
          <h3>{{country}}</h3>
        </ui>
      </div>
    </div>
  </div>
</template>


<script>
  export default {
    name:'Artefacts',
    props:['json'],
    data(){
      var artefact = JSON.parse(this.json);
      return{
        artefacts:[


       ],
        types:[
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
        ]
      }
    },
    methods:{

    }

  }
</script>

<style>
  #artefacts{
    color: lightgray;
  }
  .searchblock {
    padding: 10px;
    margin: 15px;
    background: #1b1e23;
  }
  .arts {
    padding: 15px;
    margin-right: 15px;
    background: #1b1e23;
  }

</style>
