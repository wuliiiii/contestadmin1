<template>
  <el-table :data="pageData" border @filter-change="filterChange">
    <el-table-column type="index" label="排名"></el-table-column>
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
    <el-table-column prop="codeforcesId" label="用户名">
      <template #default="scope">
        <router-link :to="'/codeforces/account/' + scope.row.codeforcesId">{{scope.row.codeforcesId}}</router-link>
      </template>
    </el-table-column>
    <el-table-column prop="status" label="用户状态" :filters="statusFilter" column-key="status" :filtered-value="[1]">
      <template #default="scope">
        <div v-for="(item,index) in statusRule" :key="index">
          <el-tag   v-if="scope.row.status===index" :type="item.tag">
          {{item.msg}}
        </el-tag>
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="rating" label="分数">
      <template #default="scope">
        {{scope.row['rating']?scope.row['rating']:0}}
      </template>
    </el-table-column>
    <el-table-column prop="participateTime" label="比赛次数">
      <template #default="scope">
        {{scope.row['participateTime'] ? scope.row.participateTime : 0}}
      </template>
    </el-table-column>
    <el-table-column prop="solve" label="解题数" sortable>
      <template #default="scope">
        {{scope.row['solve'] ? scope.row.solve : 0}}
      </template>
    </el-table-column>
    <el-table-column prop="after" label="补题数">
      <template #default="scope">
        {{scope.row['after'] ? scope.row.after : 0}}
      </template>
    </el-table-column>
    <el-table-column prop="username" label="持有者">
      <template #default="scope">
        {{scope.row['username']}} {{scope.row['realname']?'(' + scope.row['realname'] + ')':""}}
      </template>
    </el-table-column>
    <el-table-column prop="is_main" label="帐号类型">
      <template #default="scope">
        <el-tag v-if="scope.row.isMain === 1" type="success">主帐号</el-tag>
        <el-tag v-else>辅助帐号</el-tag>
      </template>
    </el-table-column>
    <template #append="scope">
      <div class="pag">
        <el-pagination background
                       layout="total, sizes, prev, pager, next"
                       :total="display.length"
                       v-model:page-size="pageSize"
                       v-model:current-page="currentPage"
                       :page-sizes="[10,20,50,100,200]"
        >
        </el-pagination>
      </div>
    </template>
  </el-table>
</template>

<script setup>
import {computed, onBeforeMount, onMounted, ref} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
import simpleUtils from "../../utils/simpleUtils";
import InfoUtils from "../../utils/InfoUtils";
let schoolMap = ref({})
let statusFilter = computed(() => {
  let res=[]
  for (let key in statusRule.value) {
    res.push({
      text: statusRule.value[key].msg,
      value: Number.parseInt(key)
    })
  }
  // console.log(res)
  return res
})
let flag =ref(true)//记录是否第一次加载（默认用户状态为启用）
let filter = ref({})
let rootData = ref([])
let pageSize = ref(10)
let currentPage = ref(1)
let pageData = computed(()=>{
  return display.value.slice((currentPage.value - 1)*pageSize.value,(currentPage.value)*pageSize.value)
})
const statusRule=ref([{"tag":"danger","msg":"禁用"},{"tag":"success","msg":"启用"},{"tag":"info","msg":"退役"}])
let display = computed(()=>{
  if(rootData.length==0){
    return [];
  }
  if(flag.value){
    filter.value={
      'status':[1]
    };
    flag.value=false;
  }
  let dspData = rootData.value
  for (let i = 0; i < dspData.length; i++) {
    dspData[i].index = i + 1
    if(!dspData[i].hasOwnProperty('rating')){
      dspData[i].rating = 0
    }
    if(!dspData[i].hasOwnProperty('participateTime')){
      dspData[i].participateTime = 0
    }
    if(!dspData[i].hasOwnProperty('solve')){
      dspData[i].solve = 0
    }
    if(!dspData[i].hasOwnProperty('after')){
      dspData[i].after = 0
    }
  }
  dspData.sort((a,b)=>{
    return b.rating - a.rating;
  })
  dspData = dspData.filter((item) => {
    let allowRet = true
    for (let key in filter.value) {
      if (filter.value[key].length > 0 && !filter.value[key].includes(item[key])) {
        allowRet = false
      }
    }
    return allowRet
  })
  return dspData
})
const filterChange = (prop) => {
  filter.value = {...filter.value,...prop}
}
onBeforeMount(()=>{
  axiosUtil.codeforcesAPI.getAllCodeforcesAccount().then(res=>{
    if(res.data.code === 200) {
      rootData.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常")
  })
  InfoUtils.getSchoolMap().then(res => {
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