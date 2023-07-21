<template>
  <el-table :data="pageData" border @sort-change="sortChange">
    <el-table-column prop="school" label="学校">
      <template #default="scope">
        {{schoolMap[scope.row.school]}}
      </template>
    </el-table-column>
    <el-table-column prop="classname" label="班级">
      <template #default="scope">
        {{scope.row.classname}}
      </template>
    </el-table-column>
    <el-table-column prop="username" label="用户名/真实姓名">
      <template #default="scope">
        <router-link :to="'/user/trainInfo/'+scope.row.username">{{scope.row.classname}}({{scope.row.realname}})</router-link>
      </template>
    </el-table-column>
    <el-table-column prop="codeforcesId" label="账户">
      <template #default="scope">
        <router-link :to="InfoJumpPrefix + scope.row[idProp]">{{scope.row[idProp]}}</router-link>
      </template>
    </el-table-column>
    <el-table-column prop="tRank" label="排名" sortable="custom">
    </el-table-column>
    <el-table-column prop="solve" label="解题数">
    </el-table-column>
    <el-table-column prop="after" label="补题数">
    </el-table-column>
    <el-table-column prop="rating" label="当前积分">
    </el-table-column>
    <el-table-column prop="diff" label="积分变化">
      <template #default="scope">
        <span :style="{color:scope.row.diff<0?'darkgreen':'red'}">
          {{scope.row.diff}}
        </span>
      </template>
    </el-table-column>
    <template #append="scope">
      <div class="pag">
        <el-pagination background
                       layout="total, sizes, prev, pager, next"
                       :total="display.length"
                       v-model:page-size="pageSize"
                       v-model:current-page="currentPage"
                       @size-change="handleSizeChange"
                       :page-sizes="[10,20,50,100,200]"
        >
        </el-pagination>
      </div>
    </template>
  </el-table>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import InfoUtils from "../../../utils/InfoUtils";
const pageSize = ref(10)
const prop = defineProps({
  rootData:{
    type:Array,
    required:true,
  },
  type:{
    type:String,
    required:true,
  },
  solveCount:{
    type:Object,
    required:true
  }
})
let InfoJumpPrefix = ref("")
let idProp = ref("")
let currentPage = ref(1)
let sortProp = ref("startTimeStamp")
let order = ref("descending")

let schoolList = ref([])
let schoolMap = ref({})
let display = computed(()=>{
  let dspData = prop.rootData.filter(v=>true)
  for (var i = 0;i<dspData.length;i++){
    if(prop.solveCount.hasOwnProperty(dspData[i].username)){
      dspData[i].solve = prop.solveCount[dspData[i].username].solve.length
      dspData[i].after = prop.solveCount[dspData[i].username].after.length
    }else{
      dspData[i].solve = 0
      dspData[i].after = 0
    }
  }
  dspData.sort((a,b)=>{
    return order.value === "descending"? b[sortProp.value] - a[sortProp.value] : a[sortProp.value] - b[sortProp.value]
  })
  console.log(prop.solveCount)
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
let handleSizeChange = (newSize) => {
  pageSize.value = newSize
}
onMounted(()=>{
  if (prop.type === "cf"){
    idProp.value = 'codeforcesId'
    InfoJumpPrefix.value = "/codeforces/account/"
  }else if(prop.type === "ac") {
    idProp.value = 'atcoderId'
    InfoJumpPrefix.value = "/atcoder/account/"
  }
  InfoUtils.getSchoolMap().then(res => {
    schoolList.value = res.schoolList
    schoolMap.value = res.schoolMap
  })
})


</script>

<style scoped>
.pag{
  display: flex;
  justify-content: center;
}
</style>