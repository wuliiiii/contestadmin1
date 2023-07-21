<template>
  <el-card :shadow="false">
    <el-row justify="space-around" >
      <el-col :span="12">
        比赛名称: {{contestInfo.name}}
      </el-col>
      <el-col :span="6">
        开始时间: {{ simpleUtils.timeStampToLocalTime(contestInfo.startTimeStamp*1000) }}
      </el-col>
    </el-row>

  </el-card>
  <el-divider></el-divider>
  <el-tabs>
    <el-tab-pane label="参与情况" lazy>
      <StuContestList :root-data="participateList" type="ac" :solve-count="inputSolveCount"></StuContestList>
    </el-tab-pane>
    <el-tab-pane label="提交情况" lazy>
      <ContestSubmitTable :root-data="submitList" type="ac"></ContestSubmitTable>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import {useRoute, useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import StuContestList from "../../components/ContestComponent/Table/StuContestList.vue";
import axiosUtil from "../../utils/axiosUtil";
import ContestSubmitTable from "../../components/ContestComponent/Table/ContestSubmitTable.vue";
// import QuestionPie from "../../components/ContestComponent/Graph/QuestionPie.vue";
// import QuestionSolveTimeBar from "../../components/ContestComponent/Graph/QuestionSolveTimeBar.vue";
// import SolveWithTime from "../../components/ContestComponent/Graph/SolveWithTime.vue";
// import TagParticipateTable from "../../components/ContestComponent/Table/TagParticipateTable.vue";
import simpleUtils from "../../utils/simpleUtils";
let inputSolveCount = ref({})
let solveCount = ref({})
let router = useRouter()
let route = useRoute()
const participateList = ref([])
const submitList = ref([])
let contestInfo = ref({})
onMounted(()=>{
  axiosUtil.atcoderAPI.getSingleAtcoderContestInfo(route.params.id).then(res=>{
    if(res.data.code === 200) {
      contestInfo.value = res.data.result
    }
  })
  axiosUtil.atcoderAPI.getAtcoderParticipateList(route.params.id).then(res=>{
    if (res.data.code === 200) {
      participateList.value = res.data.result
    }
  })
  axiosUtil.atcoderAPI.getAtcoderContestSubmit(route.params.id).then(res=>{
    if(res.data.code === 200) {
      submitList.value = res.data.result
      for(let i = 0; i < submitList.value.length; i++) {
        if (!solveCount.value.hasOwnProperty(submitList.value[i].username)){
          solveCount.value[submitList.value[i].username] = {
            solve : [],
            after:[]
          }
        }
        if(submitList.value[i].status === "AC"&&submitList.value[i].isAfter === 0){
          if (solveCount.value[submitList.value[i].username].solve.indexOf(submitList.value[i].qIndex) === -1) {
            solveCount.value[submitList.value[i].username].solve.push(submitList.value[i].qIndex)
          }
        }
      }
      for(let i = 0; i < submitList.value.length; i++) {
        if (!solveCount.value.hasOwnProperty(submitList.value[i].username)){
          solveCount.value[submitList.value[i].username] = {
            solve : [],
            after:[]
          }
        }
        if(submitList.value[i].status === "AC"&&submitList.value[i].isAfter === 1){
          if (solveCount.value[submitList.value[i].username].solve.indexOf(submitList.value[i].qIndex) === -1 && solveCount.value[submitList.value[i].username].after.indexOf(submitList.value[i].qIndex) === -1) {
            solveCount.value[submitList.value[i].username].after.push(submitList.value[i].qIndex)
          }
        }
      }
      inputSolveCount.value = solveCount.value
    }
  })
})
</script>

<style scoped>

</style>