<script setup>
import api from '../utils/axiosUtil'
import simpleUtils from "../utils/simpleUtils";
import {onBeforeUnmount, onMounted, reactive, ref} from 'vue'
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
let router = useRouter()
let userinfo = reactive({
  username:"",
  password:""
})
let lock = ref(false)
let submit = ()=>{
  if (userinfo.username===""||userinfo.password === "") {
    ElMessage(
        {
          type:"error",
          message:"用户名或密码为空",
        }
    )
    return
  }
  lock.value = true
  let encodePassword = simpleUtils.SHA512(userinfo.password)
  api.login(userinfo.username,encodePassword).then(res=>{
    if(res.data.code === 200){
      let result = res.data.result
      window.localStorage.setItem("normal_token",result.token)
      window.localStorage.setItem("normal_expire", (Date.now() + 1000*3000).toString())
      
      window.localStorage.setItem("normal_username",result.username)
      window.localStorage.setItem("normal_id",result.id)
      ElMessage.success("登录成功")
      router.push("/")
    }else{
      ElMessage(
          {
            type:"error",
            message:res.data.msg||"登录失败，用户名或密码错误",
          }
      )
    }
    lock.value = false
  })
}
onMounted(()=>{
  window.onkeydown = (e)=>{
    if(e.keyCode === 13){
      submit()
    }
  }
})
onBeforeUnmount(()=>{
  window.onkeydown = null
})

</script>

<template>
  <div class="container">
    <el-card class="card">
      <el-form>
        <el-form-item label="username">
          <el-input v-model="userinfo.username" />
        </el-form-item>
        <el-form-item label="password">
          <el-input v-model="userinfo.password" type="password" />
        </el-form-item>
        <el-button @click="submit" :loading="lock" >登录</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped lang="scss">
.container {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>