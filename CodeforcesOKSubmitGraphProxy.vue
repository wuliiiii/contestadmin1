<template>
  <CodeforcesOKSubmitScatterGraph :fin="tagFin&&okSubmitFin&&stuMapFin" :tags="tags" :ok-submit-list="okSubmitList" :student-map="stuMap"></CodeforcesOKSubmitScatterGraph>
</template>

<script setup>
import { defineComponent, ref, onMounted, onUnmounted } from 'vue'
import axiosUtil from "../../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import CodeforcesOKSubmitScatterGraph from "../../infoComponents/Graphs/CodeforcesOKSubmitScatterGraph.vue";
let tagFin = ref(false)
let tags = ref([])
let okSubmitList = ref([])
let okSubmitFin = ref(false)
let stuMap = ref(new Map())
let stuMapFin = ref(false)
const prop = defineProps({
  tid: {
    type: Number,
    required: true
  }
})
onMounted(()=>{
  axiosUtil.codeforcesAPI.getProblemTags().then(res=>{
    if(res.data.code===200) {
      tags.value = res.data.result
      tagFin.value = true
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常,标签获取失败")
  })
  axiosUtil.codeforcesAPI.getTagCodeforcesOKSubmit(prop.tid).then(res=>{
    if(res.data.code===200) {
      okSubmitList.value = res.data.result
      okSubmitFin.value = true
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常,标签获取失败")
  })
  axiosUtil.getAllUser().then(res=>{
    if(res.data.code===200) {
      let mp = {}
      res.data.result.forEach((item)=>{
        mp[item.id] = item
      })
      stuMap.value = mp
      stuMapFin.value = true
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常,标签获取失败")
  })
})
</script>

<style scoped>

</style>