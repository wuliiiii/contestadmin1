<template>
<!-- <el-tabs> 组件是其中的一个标签页组件，用于创建标签页导航和切换内容的功能 -->
  <el-tabs>
    
    <el-tab-pane lazy label="codeforces比赛情况">
      <mulity-rating-change-graph type="cf" :data="mixinCodeforcesParticipate"></mulity-rating-change-graph>
      <ParticipateTable :root-data="codeforcesParticipate" type="cf" :solve="codeforcesBeforeSolve" :after-solve="codeforcesAfterSolve"></ParticipateTable>
    </el-tab-pane>

    <el-tab-pane lazy label="atcoder比赛情况">
      <mulity-rating-change-graph type="ac" :data="mixinAtcoderParticipate"></mulity-rating-change-graph>
      <ParticipateTable :root-data="atcoderParticipate" type="ac" :solve="atcoderBeforeSolve" :after-solve="atcoderAfterSolve"></ParticipateTable>
    </el-tab-pane>

    <el-tab-pane lazy label="提交情况">
      <SingleSolveCountInfoBarGraph :before-solve-data="mixinBeforeSolve" :after-solve-data="mixinAfterSolve" ></SingleSolveCountInfoBarGraph>
      <submit-table :root-data="mixinAfterSolve.concat(mixinBeforeSolve)" ></submit-table>
    </el-tab-pane>

  </el-tabs>
</template>

<script setup>

import {onMounted,ref,computed} from "vue";
import {useRoute, useRouter} from "vue-router";
import InfoUtils from "../../utils/InfoUtils";
import {ElMessage} from "element-plus";
import axiosUtil from "../../utils/axiosUtil";
import MulityRatingChangeGraph from "../../components/infoComponents/Graphs/MulityRatingChangeGraph.vue";
import SingleSolveCountInfoBarGraph from "../../components/infoComponents/Graphs/SingleSolveCountInfoBarGraph.vue";
import ParticipateTable from "../../components/infoComponents/Table/ParticipateTable.vue";
import SubmitTable from "../../components/infoComponents/Table/SubmitTable.vue";
let router = useRouter()
let route = useRoute()
let atcoderParticipate = ref([])
let atcoderAfterSolve = ref([])
let atcoderBeforeSolve = ref([])
let codeforcesParticipate = ref([])
let codeforcesAfterSolve = ref([])
let codeforcesBeforeSolve = ref([])
let mixinAtcoderParticipate = computed(()=>{
  let ret = {}
  for(let i = 0;i<atcoderParticipate.value.length;i++){
    let item = atcoderParticipate.value[i]
    if (!ret.hasOwnProperty(item.atcoderId)){
      ret[item.atcoderId] = []
    }
    ret[item.atcoderId].push(item)
  }
  return ret
})
let mixinCodeforcesParticipate = computed(()=>{
  let ret = {}
  for(let i = 0;i<codeforcesParticipate.value.length;i++){
    let item = codeforcesParticipate.value[i]
    if (!ret.hasOwnProperty(item.codeforcesId)){
      ret[item.codeforcesId] = []
    }
    ret[item.codeforcesId].push(item)
  }
  return ret
})
let mixinBeforeSolve = computed(()=>{
  let arr = []
  arr = arr.concat(atcoderBeforeSolve.value)
  arr = arr.concat(codeforcesBeforeSolve.value)
  return arr
})
let mixinAfterSolve = computed(()=>{
  let arr = []
  arr = arr.concat(atcoderAfterSolve.value)
  arr = arr.concat(codeforcesAfterSolve.value)
  return arr
})
onMounted(()=>{
  let username = ""
  if(route.path === "/user/myTrainInfo"){ //使用 === 更严格 值的比较 不会进行类型转换
    if(InfoUtils.isLogin()) {
      username = window.localStorage.getItem("normal_username")
    }else{
      ElMessage.error("尚未登录")
      router.push("/index")
    }
  }else if(route.path.startsWith("/user/trainInfo/")) {
    username = route.params.username
  }else{
    router.push("/index")
    return
  }
  axiosUtil.atcoderAPI.getAtcoderAccountParticipate(username).then(res=>{
    if(res.data.code === 200) {
      atcoderParticipate.value = res.data.result
    }else{
      // ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  }).finally(()=>{
    // participateLoading.value = false
  })
  axiosUtil.atcoderAPI.getAtcoderAccountAfterSolve(username).then(res=>{
    if(res.data.code === 200) {
      atcoderAfterSolve.value = res.data.result
    }else{
      // ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  })
  axiosUtil.atcoderAPI.getAtcoderAccountContestSolve(username).then(res=>{
    if(res.data.code === 200) {
      atcoderBeforeSolve.value = res.data.result
    }else{
      // ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  })
  axiosUtil.codeforcesAPI.getCodeforcesAccountParticipate(username).then(res=>{
    if(res.data.code === 200) {
      codeforcesParticipate.value = res.data.result
    }else{
      // ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  }).finally(()=>{
    // participateLoading.value = false
  })
  axiosUtil.codeforcesAPI.getCodeforcesAccountAfterSolve(username).then(res=>{
    if(res.data.code === 200) {
      codeforcesAfterSolve.value = res.data.result
    }else{
      // ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  })
  axiosUtil.codeforcesAPI.getCodeforcesAccountContestSolve(username).then(res=>{
    if(res.data.code === 200) {
      codeforcesBeforeSolve.value = res.data.result
    }else{
      // ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  })
})

</script>

<style scoped>

</style>