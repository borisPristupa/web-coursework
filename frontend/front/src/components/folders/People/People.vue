<template>
  <div>
    <div class="row  align-items-stretch">


      <div class="col-sm-4  col-12">
        <div id="msgbox" class="bg-dark">
          <input class="d5 bg-dark" id="findDialog" v-model="svalue" :placeholder="placeholder" v-on:keyup.enter="search">
          <ui v-for="namemsg in info.namemsgs">
            <hr>
            <div @click="showconv">
              <img id="avatar" class="avatar2" src="../../../img/testava.png"><b class="convname">{{namemsg}}</b>
            </div>
          </ui>
        </div>
      </div>


      <div class="col-sm-8  d-none d-sm-block  col-12"   >
        <div id="conver_area" class="bg-dark align-content-center" @click="createconv">
          <div v-if="this.default">
            <p class=" createconv  text-center">Нажмите, чтоб создать беседу</p>
          </div>
          <div v-else-if="iscreateconv">
            <Createconv  @isback="this.default = $event"></Createconv>
          </div>
          <div v-else-if="isopenconv">
            <Openconv></Openconv>
          </div>
        </div>

      </div>

    </div>

  </div>
</template>


<script>
  import Createconv from "./Createconv";
  import Openconv from "./Openconv";

  export default {
    name:'People',
    components: {Openconv, Createconv},
    props:['json'],
    data(){
      var people = JSON.parse(this.json);
      return{
        info:{
          namemsgs: [
            'aaaaa',
            'bbbbb',
            'ccccc',
            'ddddd'
          ],
          //people.namemsgs,
          msgs: people.msgs
        },
        svalue:'',
        placeholder:'Поиск',

        default:true,
        iscreateconv:false,
        isopenconv:false
      }
    },
    methods:{
      getmsgs:function(){
        //

      },
      search:function(){
        if (this.svalue == ''){
          this.placeholder = 'Строка пуста'
        }
        alert('ok!')
      },
      showconv:function(){
        this.isopenconv = true
        this.default = false
        this.iscreateconv = false
      },
      createconv:function () {
        this.iscreateconv = true
        this.default = false
        this.isopenconv = false
      }
    }

  }
</script>

<style>
  .row,. {
    padding: 40px;
  }

  .createconv {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
  #conver_area {
    height: 100%;
    margin: 10px;
    color: whitesmoke;
  }
  #msgbox {
    height: 100%;
    margin: 10px;
    color: whitesmoke;
  }
  #findDialog {
    float: top;
    margin: 10px;
    width: 60%;
  }

  .avatar2 {
    position: relative;
    left: 15px;
    vertical-align: middle;
    width: 45px;
    height: 45px;
    border-radius: 50%;
  }
  .convname {
    position: relative;
    left: 30px;
    vertical-align: middle;
  }
  hr{
    margin: 14px;
  }
</style>
