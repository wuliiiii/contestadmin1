<template>
  <ContestListTable :root-data="rootData" type="ac" idkey="id"></ContestListTable>
</template>

<script setup>
import {computed, onBeforeMount, onMounted, ref} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import ContestListTable from "../../components/ContestComponent/Table/ContestListTable.vue";
let rootData = ref([])
onBeforeMount(()=>{
  axiosUtil.atcoderAPI.getAtcoderContestList().then(res=>{
    if(res.data.code === 200) {
      rootData.value = res.data.result
      console.log(rootData.value)
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常")
  })
})
</script>

<style scoped lang="scss">

</style>