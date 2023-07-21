<template>
  <el-table :data="pageData" border @sort-change="sortChange" v-loading="prop.loading">
    <el-table-column prop="school" label="学校">
      <template #default="scope">
        {{schoolMap[scope.row.school]}}
      </template>
    </el-table-column>
    <el-table-column prop="classname" label="班级">
      <template #default="scope">
        {{scope.row.classname}}
      </template>
    </el-table-column>
    <el-table-column prop="username" label="用户名/真实姓名">
      <template #default="scope">
        <router-link :to="'/user/trainInfo/'+scope.row.username">{{scope.row.classname}}({{scope.row.realname}})</router-link>
      </template>
    </el-table-column>
    <el-table-column :prop="idProp" label="账户">
      <template #default="scope">
        <router-link :to="InfoJumpPrefix + scope.row[idProp]">{{scope.row[idProp]}}</router-link>
      </template>
    </el-table-column>
    <el-table-column prop="name" label="比赛名称" sortable="custom">
    </el-table-column>
    <el-table-column prop="startTimeStamp" label="比赛时间">
      <template #default="scope">
        {{simpleUtils.timeStampToLocalTime(scope.row.startTimeStamp*1000)}}
      </template>
    </el-table-column>
    <el-table-column prop="tRank" label="排名" >
    </el-table-column>
    <el-table-column label="解题数">
      <template #default="scope">
        {{contestSolve.hasOwnProperty(scope.row.cid) ? contestSolve[scope.row.cid].solve:0}}
      </template>
    </el-table-column>
    <el-table-column label="补题数">
      <template #default="scope">
        {{contestSolve.hasOwnProperty(scope.row.cid) ? contestSolve[scope.row.cid].afterSolve:0}}
      </template>
    </el-table-column>
    <el-table-column prop="rating" label="赛后积分">
    </el-table-column>
    <el-table-column prop="diff" label="积分变化">
      <template #default="scope">
        <span :style="{color:scope.row.diff<0?'darkgreen':'red'}">
          {{scope.row.diff}}
        </span>
      </template>
    </el-table-column>
    <template #append="scope">
      <div class="pag">
        <el-pagination background layout="total, sizes, prev, pager, next" :total="display.length" v-model:page-size="pageSize" v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </template>
  </el-table>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import InfoUtils from "../../../utils/InfoUtils";
import simpleUtils from "../../../utils/simpleUtils";
const loading = ref(true)
const prop = defineProps({
  rootData:{
    type:Array,
    required:true,
  },
  type:{
    type:String,
    required:true,
  },
  loading : {
    type:Boolean,
    required:true,
  },
  solve:{
    type:Array,
    required: true
  },
  afterSolve:{
    type:Array,
    required: true
  }
})
let contestSolve = computed(() => {
  let solveMp = new Set();
  let solveCnt = {};
  for(let s of prop.solve) {
    if(!solveCnt.hasOwnProperty(s.cid)) {
      solveCnt[s.cid] = {
        solve:0,
        afterSolve:0
      }
    }
    if((s.status === "OK" && prop.type === "cf") || (s.status === "AC" && prop.type === "ac")) {
      if(!solveMp.has(s.cid + s.qIndex)) {
        solveMp.add(s.cid + s.qIndex)
        solveCnt[s.cid].solve++
      }
    }
  }
  console.log(solveCnt)
  for(let s of prop.afterSolve) {
    if(!solveCnt.hasOwnProperty(s.cid)) {
      solveCnt[s.cid] = {
        solve:0,
        afterSolve:0
      }
    }
    if((s.status === "OK" && prop.type === "cf") || (s.status === "AC" && prop.type === "ac")) {
      if(!solveMp.has(s.cid + s.qIndex)) {
        solveMp.add(s.cid + s.qIndex)
        solveCnt[s.cid].afterSolve++
      }
    }
  }
  return solveCnt
})
let InfoJumpPrefix = ref("")
let idProp = ref("")
let currentPage = ref(1)
let sortProp = ref("startTimeStamp")
let order = ref("descending")

let schoolList = ref([])
let schoolMap = ref({})
let display = computed(()=>{
  let dspData = prop.rootData
  dspData.sort((a,b)=>{
    return order.value === "descending"? b[sortProp.value] - a[sortProp.value] : a[sortProp.value] - b[sortProp.value]
  })
  return dspData
})
let pageSize = ref(10)
let pageData = computed(()=>{
  console.log(display.value)
  return display.value.slice((currentPage.value - 1)*pageSize.value,(currentPage.value)*pageSize.value)
})
let sortChange = (info)=>{
  sortProp.value = info.prop
  order.value = info.order
}
onMounted(()=>{
  if (prop.type === "cf"){
    idProp.value = 'codeforcesId'
    InfoJumpPrefix.value = "/codeforces/account/"
  }else if(prop.type === "ac") {
    idProp.value = 'atcoderId'
    InfoJumpPrefix.value = "/atcoder/account/"
  }
  InfoUtils.getSchoolMap().then(res => {
    schoolList.value = res.schoolList
    schoolMap.value = res.schoolMap
  })
})
</script>

<style scoped>
.pag{
  display: flex;
  justify-content: center;
}
</style>