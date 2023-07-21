<template>
  <el-table :data="count<3 ? []:pageData" v-loading="count<3" border @filter-change="filterChange" @sort-change="sortChange">
    <el-table-column type="index" label="排名"></el-table-column>
    <el-table-column prop="school" label="学校" :filters="schoolFilter" column-key="school">
      <template #default="scope">
        {{schoolMap[scope.row.school]}}
      </template>
    </el-table-column>
    <el-table-column prop="classname" label="班级">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="classnameFilter"></el-input>
      </template>
      <template #default="scope">
        {{scope.row.classname}}
      </template>
    </el-table-column>
    <el-table-column prop="year" label="年级" :filters="yearFilter" column-key="year" >
    </el-table-column>
    <el-table-column prop="realname" label="真实姓名">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="realnameFilter"></el-input>
      </template>
    </el-table-column>
    <el-table-column prop="username" label="用户名">
      <template #header="scope">
        {{scope.column.label}}
        <el-input v-model="usernameFilter"></el-input>
      </template>
      <template #default="scope">
        <router-link :to="'/user/trainInfo/' + scope.row.username">{{scope.row.username}}</router-link>
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
    <el-table-column prop="cfRating" label="codeforces分数" sortable>
    </el-table-column>
    <el-table-column prop="cfParticipate" label="codeforces比赛次数" sortable>
    </el-table-column>
    <el-table-column prop="cfSolve" label="codeforces解题数" sortable>
    </el-table-column>
    <el-table-column prop="cfAfter" label="codeforces补题数" sortable>
    </el-table-column>
    <el-table-column prop="acRating" label="atcoder分数" sortable>
    </el-table-column>
    <el-table-column prop="acParticipate" label="atcoder比赛次数" sortable>
    </el-table-column>
    <el-table-column prop="acSolve" label="atcoder解题数" sortable>
    </el-table-column>
    <el-table-column prop="acAfter" label="atcoder补题数" sortable>
    </el-table-column>
    <template #append>
      <div class="pag">
        <el-pagination background layout="total, sizes, prev, pager, next" :total="dspData.length" v-model:page-size="pageSize" v-model:current-page="currentPage">
        </el-pagination>
      </div>
    </template>
  </el-table>
</template>

<script setup>


import {computed, onMounted, ref,onBeforeMount} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
import InfoUtils from "../utils/InfoUtils";
const table=ref(null)
const schoolMap = ref({})
let count = ref(0)//记录axios成功数量
let flag =ref(true)//记录是否第一次加载（默认用户状态为启用）
const acMap = ref({})
const cfMap = ref({})
const rootData = ref([])
const statusRule=ref([{"tag":"danger","msg":"禁用"},{"tag":"success","msg":"启用"},{"tag":"info","msg":"退役"}])
const classnameFilter = ref('')
const realnameFilter = ref('')
const usernameFilter = ref('')
const sortProp = ref("cfRating")
let sortOrder = ref('descending')
let pageSize = ref(20)
const currentPage = ref(1)
const pageData = computed(()=>{
   return dspData.value.slice((currentPage.value-1)*pageSize.value, currentPage.value*pageSize.value)
})
let yearFilter = computed(() => {
  let yearList = []
  for(let item of rootData.value) {
    if(!yearList.includes(item.year)) {
      yearList.push(item.year)
    }
  }
  let ret = []
  for (let year of yearList.sort((a, b) => b - a)) {
    ret.push({text : "" + year,value: year})
  }
  console.log(ret)
  return ret
})
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
let schoolFilter = computed(() => {
  let res = []
  for (let key in schoolMap.value) {
    res.push({
      text: schoolMap.value[key],
      value: Number.parseInt(key)
    })
  }
  console.log(res)
  return res
})
let filter = ref({})
const dspData = computed(()=>{
  if(count.value<3)
    return [];
  if(flag.value){
    filter.value={
      'status':[1]
    };
    flag.value=false;
  }
  var retData = rootData.value
  for(var i = 0;i<retData.length;i++) {
    if (cfMap.value.hasOwnProperty(retData[i].username)) {
      retData[i].cfRating = cfMap.value[retData[i].username].rating == null ? 0 : cfMap.value[retData[i].username].rating
      retData[i].cfParticipate = cfMap.value[retData[i].username].participate == null ? 0 : cfMap.value[retData[i].username].participate
      retData[i].cfSolve = cfMap.value[retData[i].username].solve == null ? 0 : cfMap.value[retData[i].username].solve
      retData[i].cfAfter = cfMap.value[retData[i].username].after == null ? 0 : cfMap.value[retData[i].username].after
    } else {
      retData[i].cfRating = 0
      retData[i].cfParticipate = 0
      retData[i].cfSolve = 0
      retData[i].cfAfter = 0
    }
    if (acMap.value.hasOwnProperty(retData[i].username)) {
      retData[i].acRating = acMap.value[retData[i].username].rating == null ? 0 : acMap.value[retData[i].username].rating
      retData[i].acParticipate = acMap.value[retData[i].username].participate == null ? 0 : acMap.value[retData[i].username].participate
      retData[i].acSolve = acMap.value[retData[i].username].solve == null ? 0 : acMap.value[retData[i].username].solve
      retData[i].acAfter = acMap.value[retData[i].username].after == null ? 0 : acMap.value[retData[i].username].after
    } else {
      retData[i].acRating = 0
      retData[i].acParticipate = 0
      retData[i].acSolve = 0
      retData[i].acAfter = 0
    }
  }
  retData.sort((a,b) => {
    if (sortOrder.value === 'descending') {
      return b[sortProp.value] - a[sortProp.value]
    } else {
      return a[sortProp.value] - b[sortProp.value]
    }
  })
  retData = retData.filter((item) => {
    if (usernameFilter.value !== '' && !item.username.includes(usernameFilter.value)) {
      return false
    }
    if (realnameFilter.value !== '' && !item.realname.includes(realnameFilter.value)) {
      return false
    }
    if (classnameFilter.value !== '' && !item.classname.includes(classnameFilter.value)) {
      return false
    }
    let allowRet = true
    for (let key in filter.value) {
      if (filter.value[key].length > 0 && !filter.value[key].includes(item[key])) {
        allowRet = false
      }
    }
    return allowRet
  })
  return retData;
})
const sortChange = (prop) => {
  sortProp.value = prop.prop
  sortOrder.value = prop.order
}
onBeforeMount(()=>{
  axiosUtil.getAllUser().then((res)=>{
    if(res.data.code === 200) {
      rootData.value = res.data.result;
      count.value++;
    }
  })
  axiosUtil.codeforcesAPI.getAllCodeforcesAccount().then(res=>{
    let newCfMap = {}
    if(res.data.code === 200) {
      res.data.result.forEach(item=>{
        if(!newCfMap.hasOwnProperty(item.username)) {
          newCfMap[item.username] = {
            rating: 0,
            participate: 0,
            solve: 0,
            after: 0
          }
        }
        if(item.isMain) {
          newCfMap[item.username].rating = item.rating
        }
        if(item.hasOwnProperty("participateTime"))
        newCfMap[item.username].participate += item.participateTime

        if (item.hasOwnProperty("solve"))
        newCfMap[item.username].solve += item.solve

        if (item.hasOwnProperty("after"))
        newCfMap[item.username].after += item.after
      })
      cfMap.value = newCfMap;
      count.value++;
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常")
  })
  axiosUtil.atcoderAPI.getAllAtcoderAccount().then(res=>{
    let newAcMap = {}
    if(res.data.code === 200) {
      res.data.result.forEach(item=>{
        if(!newAcMap.hasOwnProperty(item.username)) {
          newAcMap[item.username] = {
            rating: 0,
            participate: 0,
            solve: 0,
            after: 0
          }
        }
        if(item.isMain) {
          newAcMap[item.username].rating = item.rating
        }
        if(item.hasOwnProperty("participateTime"))
        newAcMap[item.username].participate += item.participateTime
        if (item.hasOwnProperty("solve"))
        newAcMap[item.username].solve += item.solve
        if (item.hasOwnProperty("after"))
        newAcMap[item.username].after += item['after']
      })
      acMap.value = newAcMap
      count.value++
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    console.log(err)
    ElMessage.error("网络异常")
  })
  InfoUtils.getSchoolMap().then(res => {
    schoolMap.value = res.schoolMap
  })

})
const filterChange = (prop) => {
  filter.value = {...filter.value,...prop}
}
</script>

<style scoped>
.pag{
  display: flex;
  justify-content: center;
}
</style>