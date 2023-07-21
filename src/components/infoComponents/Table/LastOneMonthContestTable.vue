<template>
  <el-card style="width: 100%">
    <template #header>
      <span>已结束的比赛</span>
    </template>
    <el-table :data="totalData" v-loading="loading">
      <el-table-column prop="name" label="名称">
        <template #default="scope">
          <router-link :to="scope.row.url">{{scope.row.name}}</router-link>
        </template>
      </el-table-column>
      <el-table-column prop="time" label="开始时间">
        <template #default="scope">
          {{simpleUtils.timeStampToLocalTime(scope.row.time*1000)}}
        </template>
      </el-table-column>
      <el-table-column prop="participate" label="参与人数">
        <template #default="scope">
          {{scope.row.participate}}
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import {computed, onMounted,ref} from "vue";
import axiosUtil from "../../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import simpleUtils from "../../../utils/simpleUtils";

let codeforcesData = ref([])
let atcoderData = ref([])
let totalData = computed(()=>{
  let ret = []
  for(let data of codeforcesData.value) {
    let item = {}
    item.name = data.name
    item.time = data.startTimeStamp
    item.participate = data.participate
    item.url = "/codeforces/contestInfo/" + data.cid
    ret.push(item)
  }
  for(let data of atcoderData.value) {
    let item = {}
    item.name = data.name
    item.time = data.startTimeStamp
    item.participate = data.participate
    item.url = "/atcoder/contestInfo/" + data.id
    ret.push(item)
  }
  ret.sort((a,b)=>b.time-a.time)
  return ret.filter(item => item.participate > 0)

})
onMounted(()=>{
  axiosUtil.codeforcesAPI.getCodeforcesRecentOneMonthInfo().then(res=>{
    if(res.data.code === 200) {
      codeforcesData.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常")
  })
  axiosUtil.atcoderAPI.getAtcoderRecentOneMonthInfo().then(res=>{
    if(res.data.code === 200) {
      atcoderData.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常")
  })
})
</script>

<style scoped>

</style>