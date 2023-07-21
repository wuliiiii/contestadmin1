<template>
  <el-card style="width: 100%">
    <template #header>
      <span>即将到来的比赛</span>
    </template>
    <el-table :data="data" v-loading="loading">
      <el-table-column prop="name" label="名称">
        <template #default="scope">
          <a v-if="scope.row.type === 'ac'" :href="'https://atcoder.jp/contests/'+scope.row.nickname" target="_blank">{{scope.row.name}}</a>
          <a v-if="scope.row.type === 'cf'" :href="'http://codeforces.com/contest/'+scope.row.id" target="_blank">{{scope.row.name}}</a>
        </template>
      </el-table-column>
      <el-table-column prop="startTime" label="开始时间">
        <template #default="scope">
          {{simpleUtils.timeStampToLocalTime(scope.row.startTimeStamp*1000)}}
        </template>
      </el-table-column>
      <el-table-column prop="duration" label="持续时间">
        <template #default="scope">
          {{simpleUtils.getDuration(scope.row.duration)}}
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>

import {onMounted,ref} from "vue";
import AxiosUtil from "../../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import simpleUtils from "../../../utils/simpleUtils";
const data = ref([])
const loading = ref(true)
onMounted(()=>{
  AxiosUtil.getFutureContest().then((res)=>{
    if(res.data.code === 200) {
      data.value = res.data.result.sort((a,b)=>{
        return a.startTimeStamp - b.startTimeStamp
      })
      loading.value = false
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch((e)=>{
    ElMessage.error(e)
  })
})

</script>

<style scoped>

</style>