<template>
  <el-table :data="pageData" border @sort-change="sortChange">
    <el-table-column prop="codeforcesId" label="提交序号">
      <template #default="scope">
        {{scope.row.sid}}
      </template>
    </el-table-column>
    <el-table-column prop="codeforcesId" label="用户名">
      <template #default="scope">
        <span v-if="scope.row.hasOwnProperty('codeforcesId')">
          {{scope.row.codeforcesId}} ({{scope.row.username}})
        </span>
        <span v-else-if="scope.row.hasOwnProperty('atcoderId')">
          {{scope.row.atcoderId}} ({{scope.row.username}})
        </span>
      </template>
    </el-table-column>
    <el-table-column prop="name" label="比赛名称">
    </el-table-column>
    <el-table-column prop="qIndex" label="题号" sortable="custom">
    </el-table-column>
    <el-table-column prop="status" label="状态">
    </el-table-column>
    <el-table-column prop="language" label="语言">
      <template #default="scope">
        {{scope.row.language}}
      </template>
    </el-table-column>
    <el-table-column prop="submitTime" label="提交时间">
      <template #default="scope">
        {{simpleUtils.timeStampToLocalTime(scope.row[timeProp]*1000)}}
      </template>
    </el-table-column>
    <el-table-column prop="isAfter" label="补题">
      <template #default="scope">
        <el-tag v-if="scope.row.isAfter === 1">补题</el-tag>
        <el-tag v-else>现场提交</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="查看代码">
      <template #default="scope">
        <span v-if="scope.row.hasOwnProperty('codeforcesId')">
          <el-button @click="lookupCode(scope.row.sid,'cf')">查看代码</el-button>
        </span>
        <span v-else-if="scope.row.hasOwnProperty('atcoderId')">
          <el-button @click="lookupCode(scope.row.sid,'ac')">查看代码</el-button>
        </span>

      </template>

    </el-table-column>
    <template #append="scope">
      <div class="pag">
        <el-pagination background layout="total, prev, pager, next" :total="display.length" :page-size="10" v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </template>
  </el-table>
  <CodeDisplay :code-data="codeData" ref="codeDisplay"></CodeDisplay>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import simpleUtils from "../../../utils/simpleUtils";
import CodeDisplay from "../../ContestComponent/Table/CodeDisplay.vue";
import axiosUtil from "../../../utils/axiosUtil";
import {ElMessage} from "element-plus";
const prop = defineProps({
  rootData:{
    type:Array,
    required:true,
  }
})
let codeData = ref("")
let codeDisplay = ref(null)
let currentPage = ref(1)
let sortProp = ref("startTimeStamp")
let order = ref("ascending")
let timeProp = ref("submitTime")
let display = computed(()=>{
  let dspData = prop.rootData.filter(v=>true)
  dspData.sort((a,b)=>{
    return order.value === "descending"? b[sortProp.value] - a[sortProp.value] : a[sortProp.value] - b[sortProp.value]
  })
  return dspData
})
let pageData = computed(()=>{
  console.log(display.value)
  return display.value.slice((currentPage.value - 1)*10,(currentPage.value)*10)
})
let sortChange = (info)=>{
  sortProp.value = info.prop
  order.value = info.order
}
let lookupCode = (sid,type) => {
  if(type === "cf"){
    axiosUtil.codeforcesAPI.getCodeforcesSubmitCode(sid).then(res=>{
      if(res.data.code === 200) {
        codeData.value = res.data.result.code
        codeDisplay.value.open()
      }else{
        ElMessage.error(res.data.msg)
      }
    })
  }else if(type === "ac"){
    axiosUtil.atcoderAPI.getAtcoderSubmitCode(sid).then(res=>{
      if(res.data.code === 200) {
        codeData.value = res.data.result.code
        codeDisplay.value.open()
      }else{
        ElMessage.error(res.data.msg)
      }
    })
  }

}
onMounted(()=>{
  sortProp.value = timeProp.value
})
</script>

<style scoped>
.pag{
  display: flex;
  justify-content: center;
}
</style>