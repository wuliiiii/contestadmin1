<template>
  <el-form
      ref="passwordFormRef"
      :model="passwordForm"
      :rules="rules"
  >
    <el-form-item label="旧密码" prop="oldPassword">
      <el-input v-model="passwordForm.oldPassword" type="password" placeholder="请输入旧密码" />
    </el-form-item >
    <el-form-item label="新密码" prop="newPassword">
      <el-input v-model="passwordForm.newPassword" type="password" placeholder="请输入新密码" />
    </el-form-item>
    <el-form-item label="确认" prop="newPasswordAgain">
      <el-input v-model="passwordForm.newPasswordAgain" type="password" placeholder="请再次输入新密码" />
    </el-form-item>
    <el-button @click="submit" :loading="loading">提交</el-button>
  </el-form>
</template>

<script setup>

import {reactive,ref} from "vue";
import simpleUtils from "../utils/simpleUtils";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
let passwordFormRef = ref(null)
let loading = ref(false)
let passwordForm = reactive({
  oldPassword: "",
  newPassword: "",
  newPasswordAgain: ""
})
let rules = reactive({
  oldPassword: [
    {required: true, message: "请输入旧密码", trigger: "blur"},
    // {min: 8, message: "密码长度必须大于8位", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkCharAndNumber(value)) {
          callback()
        } else {
          callback(new Error("密码只能包含数字与字母"))
        }
      }, trigger: "blur"
    }
  ],
  newPassword: [
    {required: true, message: "请输入新密码", trigger: "blur"},
    {min: 8, message: "密码长度必须大于8位", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkCharAndNumber(value)) {
          callback()
        } else {
          callback(new Error("密码只能包含数字与字母"))
        }
      }, trigger: "blur"
    }
  ],
  newPasswordAgain: [
    {required: true, message: "请再次输入新密码", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (value === passwordForm.newPassword) {
          callback()
        } else {
          callback(new Error("两次输入的密码不一致"))
        }
      }, trigger: "blur"
    }
  ]
})
let submit = ()=>{
  if (passwordFormRef.value === null) {
    return
  }
  passwordFormRef.value.validate((valid) => {
    if (valid) {
      let newEncodePassword = simpleUtils.SHA512(passwordForm.newPassword)
      let oleEncodePassword = simpleUtils.SHA512(passwordForm.oldPassword)
      loading.value = true
      axiosUtil.resetPassword(oleEncodePassword,newEncodePassword).then(res=>{
        if(res.data.code === 200){
          ElMessage.success("修改成功")
          passwordForm.oldPassword = ""
          passwordForm.newPassword = ""
          passwordForm.newPasswordAgain = ""
        }else{
          ElMessage(
              {
                type:"error",
                message:"修改失败，旧密码错误",
              }
          )
        }
        loading.value = false
      }).catch(err=>{
        ElMessage(
            {
              type:"error",
              message:"修改失败，网络异常",
            }
        )
      }).finally(()=>{
        loading.value = false
      })
    }
  })
}
</script>

<style scoped>

</style>