<template>
  <el-table :data="dspData" :default-sort="defaultSort" @sort-change="sortChange" v-loading="loading">
    <el-table-column prop="cid" label="比赛id" sortable="custom" align="center">
    </el-table-column>
    <el-table-column prop="qindex" label="问题编号" align="center">
    </el-table-column>
    <el-table-column prop="name" label="题目名称" align="center">
      <template #default="scope">
        <a :href="`https://codeforces.com/contest/${scope.row.cid}/problem/${scope.row.qindex}`" target="_blank">{{scope.row.name}}</a>
      </template>
    </el-table-column>
    <el-table-column prop="difficulty" label="题目难度" align="center" sortable="custom">
      <template #header="scope">
        <div>题目难度</div>
        <el-slider v-model="difficultyRange" :min="0" :max="4000" range :step="1" @change="loadPage"></el-slider>
      </template>
    </el-table-column>
    <el-table-column prop="tags" label="题目标签" align="center">
      <template #header="scope">
        <div>题目标签</div>
        <el-select
            v-model="tagSelect"
            multiple
            placeholder="Select"
            @change="loadPage"
        >
          <el-option
              v-for="item in tagFilters"
              :key="item.id"
              :label="item.tagName"
              :value="item.tagName"
          />
        </el-select>
      </template>
      <template #default="scope">
        <el-tag v-for="tag in String(scope.row.tags).split(',')" :key="tag" type="info" :closable="false">{{tag}}</el-tag>
      </template>
    </el-table-column>
    <template #append="scope">
      <div class="pag">
        <el-pagination background
                       layout="total, sizes, prev, pager, next"
                       :total="total"
                       v-model:page-size="pageSize"
                       v-model:current-page="currentPage"
                       :page-sizes="[10,20,50,100,200]"
                       @size-change="sizeChange"
                       @current-change="loadPage"
        >
        </el-pagination>
      </div>
    </template>
  </el-table>
</template>

<script setup>

import {computed, onMounted, ref} from "vue";
import axiosUtil from "../../utils/axiosUtil";
import {ElMessage} from "element-plus";
const difficultyRange = ref([0, 4000])
const loading = ref(false)
const dspData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const sortProp = ref('cid')
const sortType = ref('DESC')
const search = computed(()=>{
  return tagSelect.value.join(",")
})
const tagFilters = ref([])
const tagSelect = ref([])
const defaultSort = computed(()=>{
  return {
    prop: sortProp.value,
    order: sortType.value === 'DESC' ? 'descending' : 'ascending'
  }
})
onMounted(()=>{
  axiosUtil.codeforcesAPI.getProblemTags().then(res=>{
    if(res.data.code === 200){
      tagFilters.value = res.data.result
    }
  })
  loadPage()
})
const sortChange = ({column,prop,order})=>{
  console.log(column,prop,order)
  if(prop == null) {
    sortProp.value = 'cid'
  }else{
    sortProp.value = prop
  }
  console.log(order)
  if(order == null) {
    sortType.value = 'DESC'
  }else{
    sortType.value = (order === 'descending' ? 'DESC' : 'ASC')
  }
  console.log(sortType.value,sortProp.value)
  currentPage.value = 1
  loadPage()
}
const loadPage = () => {
  loading.value = true
  axiosUtil.codeforcesAPI.getCodeforcesProblemList
  (
      currentPage.value,
      pageSize.value,
      sortProp.value,
      sortType.value,
      search.value,
      difficultyRange.value[0],
      difficultyRange.value[1]
  )
      .then(res=>{
    if(res.data.code === 200){
      dspData.value = res.data.result.problemList
      total.value = res.data.result.total
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("网络异常")
  }).finally(()=>{
    loading.value = false
  })
}
const sizeChange = () => {
  currentPage.value = 1
  loadPage()
}
</script>

<style scoped>
.pag{
  display: flex;
  justify-content: center;
}
</style>