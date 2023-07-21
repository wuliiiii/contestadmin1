<template>
  <el-card shadow="never">
    <el-row>
      <el-col :span="6">
        <el-select v-model="tagSelect" placeholder="标签选择">
          <el-option
              v-for="item in tagList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="getTagParticipate(tagSelect)">查询</el-button>
      </el-col>
    </el-row>
  </el-card>
  <el-divider></el-divider>
  <el-table :data="dspData" border>
    <el-table-column prop="classname" label="班级"></el-table-column>
    <el-table-column prop="realname" label="姓名"></el-table-column>
    <el-table-column prop="rating" label="赛后积分"></el-table-column>
    <el-table-column prop="diff" label="diff"></el-table-column>
    <el-table-column prop="tRank" label="排名" sortable="custom">
    </el-table-column>
    <el-table-column prop="solve" label="解题数">
    </el-table-column>
    <el-table-column prop="after" label="补题数">
    </el-table-column>
    <el-table-column prop="participate" label="备注">
      <template #default="scope">
        <el-tag v-if="scope.row.participate" type="success">
          成功参赛
        </el-tag>
        <el-tag v-else type="danger">
          未参赛
        </el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup>
import {computed, onBeforeMount, onMounted, ref} from "vue";
import axiosUtil from "../../../utils/axiosUtil";
import {ElMessage} from "element-plus";
const prop = defineProps({
  rootData:{
    type:Array,
    required:true,
  }
})
const dspData = computed(()=>{
  let data = tagUserList.value;
  for(let i in data) {
    let it = prop.rootData.find((item)=>{return data[i].username === item.username});
    if(it === undefined) {
      data[i].participate = false
      data[i].diff = 0
      data[i].rating = 0
      data[i].tRank = 0
      data[i].solve = 0
      data[i].after = 0
    }else{
      data[i].participate = true
      data[i].diff = it.diff
      data[i].rating = it.rating
      data[i].tRank = it.tRank
      data[i].solve = it.solve
      data[i].after = it.after
    }
  }
  return data
})
let tagUserList = ref([])
let tagList = ref([])
let tagSelect = ref(null)
onBeforeMount(()=>{
  axiosUtil.getTagList().then(res=>{
    if (res.data.code === 200){
      tagList.value = res.data.result
  }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("获取标签列表失败")
  })
})
let getTagParticipate = (tid) => {
  if(tid == null) {
    ElMessage.error("请选择标签")
    return
  }
  axiosUtil.getTagUserInfo(tid).then(res=>{
    if (res.data.code === 200){
      tagUserList.value = res.data.result
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("获取标签参与人员失败")
  })
}
</script>

<style scoped>

</style>