<template>
  <el-table :data="pageData" border @sort-change="sortChange">
    <el-table-column :prop="prop.idkey" label="比赛id" sortable>
    </el-table-column>
    <el-table-column prop="name" label="比赛名称">
      <template #default="scope">
        <router-link :to="contestLinkPrefix+scope.row[prop.idkey]">{{scope.row.name}}</router-link>
      </template>
    </el-table-column>
    <el-table-column prop="type" label="比赛类型">
    </el-table-column>
    <el-table-column prop="startTimeStamp" label="开始时间" sortable="custom" >
      <template #default="scope">
        {{simpleUtils.timeStampToLocalTime(scope.row.startTimeStamp*1000)}}
      </template>
    </el-table-column>
    <el-table-column prop="duration" label="持续时间">
      <template #default="scope">{{simpleUtils.getDuration(scope.row.duration)}}</template>
    </el-table-column>
    <el-table-column prop="participate" label="参与人数">
      <template #header="scope">
        <div>参与人数</div>
        <el-checkbox v-model="disPlayNoZero">显示所有比赛</el-checkbox>
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
import simpleUtils from "../../../utils/simpleUtils";
const pageSize = ref(20)
const prop = defineProps({
  rootData:{
    type:Array,
    required:true
  },
  type:{
    type:String,
    required:true,
  },
  idkey:{
    type:String,
    required:true,
  }
})
let contestLinkPrefix = ref("")
let currentPage = ref(1)
let disPlayNoZero = ref(false)
let sortProp = ref("startTimeStamp")
let order = ref("descending")
let display = computed(()=>{
  let dspData = prop.rootData.filter(v=>true)
  // console.log(dspData,disPlayNoZero.value)
  if(disPlayNoZero.value === false){
    dspData = dspData.filter(v=>v.participate !== 0)
  }
  // console.log(dspData,disPlayNoZero.value)
  dspData.sort((a,b)=>{
    return order.value === "descending"? b[sortProp.value] - a[sortProp.value] : a[sortProp.value] - b[sortProp.value]
  })
  return dspData
})
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
    contestLinkPrefix.value = '/codeforces/contestInfo/'
  }else if (prop.type === "ac") {
    contestLinkPrefix.value = '/atcoder/contestInfo/'
  }
})
</script>

<style scoped>
.pag{
  display: flex;
  justify-content: center;
}
</style>