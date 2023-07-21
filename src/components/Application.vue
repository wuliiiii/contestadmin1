<template>
  <div class="container">
    <el-table
        :data="applicationList" border
    >
      <el-table-column fixed prop="id" label="Id" align="center" />
      <el-table-column fixed prop="opertation" align="center" label="操作"/>
      <el-table-column fixed prop="time" align="center" label="申请时间">
        <template #default="scope">
          {{simpleUtils.timeStampToLocalTime(scope.row.time)}}
        </template>
      </el-table-column>
      <el-table-column fixed prop="status" label="状态" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status===1" type="success">
            通过
          </el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="danger">
            拒绝
          </el-tag>
          <el-tag v-else-if="scope.row.status === 0" type="warning">
            待审核
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center">
        <template #default="scope" >
          <el-button type="primary" @click="handleCheck(scope.row.parameter)">查看参数</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <json-modal :json-data="nowInfo" ref="jsonM"></json-modal>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import {useRouter} from "vue-router";
import simpleUtils from "../utils/simpleUtils";
import infoUtils from "../utils/InfoUtils";
import axiosUtil from "../utils/axiosUtil";
import JsonModal from "./jsonModal.vue";
const router = useRouter()
const applicationList = ref([])
let nowInfo = ref("")
let jsonM = ref(null)
onMounted(()=>{
  if(!infoUtils.isLogin()){
    router.push("/login")
  }
  axiosUtil.getMyApplication().then(res=>{
    if(res.data.code === 200){
      applicationList.value = res.data.result
    }
  })

})
let handleCheck = (parameter)=>{
  nowInfo.value = JSON.parse(parameter)
  console.log(jsonM.value)
  if (jsonM.value) {
    jsonM.value.open()
  }
}
</script>

<style scoped>

</style>