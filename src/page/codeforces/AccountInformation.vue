<template>
  <el-tabs>
    <el-tab-pane label="参赛统计" lazy>
      <SingleRatingChangeGraph type="cf" :data="participate" ></SingleRatingChangeGraph>
      <ParticipateTable type="cf" :root-data="participate" :loading="participateLoading" :solve="contestSolve" :after-solve="afterSolve"></ParticipateTable>
    </el-tab-pane>
    <el-tab-pane label="提交统计" lazy>
      <SingleSolveCountInfoBarGraph type="cf" :after-solve-data="afterSolve" :before-solve-data="contestSolve"></SingleSolveCountInfoBarGraph>
      <ContestSubmitTable type="cf" :root-data="mergeData"></ContestSubmitTable>
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import {useRoute} from "vue-router";
import {computed, onBeforeMount, onMounted, ref} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import SingleRatingChangeGraph from "../../components/infoComponents/Graphs/SingleRatingChangeGraph.vue";
import ParticipateTable from "../../components/infoComponents/Table/ParticipateTable.vue";
import SingleSolveCountInfoBarGraph from "../../components/infoComponents/Graphs/SingleSolveCountInfoBarGraph.vue";
import ContestSubmitTable from "../../components/ContestComponent/Table/ContestSubmitTable.vue";
let participate = ref([])
let afterSolve = ref([])
let contestSolve = ref([])
let participateLoading = ref(true)
const mergeData = computed(()=>{
  return afterSolve.value.concat(contestSolve.value)
})
let route = useRoute()
onBeforeMount(()=>{
  let cfAccountName = route.params.name
  console.log(cfAccountName)
  axiosUtil.codeforcesAPI.getCodeforcesUserParticipate(cfAccountName).then(res=>{
    if(res.data.code === 200) {
      participate.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  }).finally(()=>{
    participateLoading.value = false
  })
  axiosUtil.codeforcesAPI.getCodeforcesUserAfterSolve(cfAccountName).then(res=>{
    if(res.data.code === 200) {
      afterSolve.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  })
  axiosUtil.codeforcesAPI.getCodeforcesUserContestSolve(cfAccountName).then(res=>{
    if(res.data.code === 200) {
      contestSolve.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  })
})
</script>

<style scoped lang="scss">

</style>