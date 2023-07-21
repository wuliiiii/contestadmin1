<template>
  <div class="container">
    <el-table :data="data">
      <el-table-column prop="id" label="id" fixed align="center" >
      </el-table-column>
      <el-table-column prop="codeforcesId" label="用户名" fixed align="center">
      </el-table-column>
      <el-table-column prop="rating" label="积分" fixed align="center">
      </el-table-column>
      <el-table-column prop="status" label="状态" fixed align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status===1" type="success">
            正常
          </el-tag>
          <el-tag v-else-if="scope.row.status === 0" type="warning">
            禁用
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="isMain" label="主账户" fixed align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.isMain===1" type="success">
            是
          </el-tag>
          <el-tag v-else-if="scope.row.isMain === 0" type="info">
            否
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" v-if="prop.username === ''">
        <template #header="scope">
          <el-button type="primary" @click="handleAdd()">添加新账户</el-button>
        </template>
        <template #default="scope">
          <el-button @click="setMain(scope.row.id)"> 设为主账户</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <AddCodeforcesAccount ref="formRef"></AddCodeforcesAccount>
</template>

<script setup>
import {useRouter} from "vue-router";
import {onMounted,ref} from "vue";
import infoUtils from "../utils/InfoUtils";
import axiosUtil from "../utils/axiosUtil";
import AddCodeforcesAccount from "./ApplicationComponents/AddCodeforcesAccount.vue";
import {ElMessage} from "element-plus";

const router = useRouter()
const prop = defineProps({
  username: {
    type: String,
    default: ""
  },
})
let formRef = ref(null)
let data = ref([])
onMounted(()=>{
  if(prop.username === ""){
    if(infoUtils.isLogin()){
      axiosUtil.codeforcesAPI.getThisUserCodeforcesAccount().then(res=>{
        if(res.data.code === 200){
          data.value = res.data.result
        }
      })
    }else{
      router.push("/login")
    }
  }else{

  }
})
let setMain = (id)=>{
  axiosUtil.codeforcesAPI.setMainCodeforces(id).then(res=>{
    if(res.data.code === 200){
      data.value.forEach(item=>{
        if(item.id === id){
          item.isMain = 1
        }else{
          item.isMain = 0
        }
      })
      ElMessage.success("修改成功")
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(err=>{
    ElMessage.error("修改失败")
  })
}
let handleAdd = () => {
  if(!formRef) return
  formRef.value.open()
}
</script>

<style scoped>

</style>