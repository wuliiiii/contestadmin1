<template>
  <el-form
          :model="userinfo"
          :rules="rules"
          label-width="100px"
          ref="formRef"
  >
      <el-form-item label="username" prop="username">
        <el-input v-model="userinfo.username" :disabled="true" />
      </el-form-item>
      <el-form-item label="classname" prop="classname">
        <el-input v-model="userinfo.classname" :disabled="editStatus === false"/>
      </el-form-item>
      <el-form-item label="stuno" prop="stuno">
        <el-input v-model="userinfo.stuno" :disabled="editStatus === false"/>
      </el-form-item>
      <el-form-item label="school" prop="school">
        <el-select v-model="userinfo.school" :disabled="true">
          <el-option v-for="item in schoolList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="year" prop="year" :disabled="true">
        <el-input-number v-model="userinfo.year" :min="2000" :max="(new Date()).getFullYear()" :disabled="editStatus === false"/>
      </el-form-item>
      <el-form-item label="realname" prop="realname" :disabled="editStatus === false">
        <el-input v-model="userinfo.realname" :disabled="editStatus === false"/>
      </el-form-item>
    <el-button @click="startEdit" v-if="editStatus === false">修改信息</el-button>
    <el-button @click="cancel()"  v-if="editStatus === true" :loading="loading">取消</el-button>
    <el-button v-if="editStatus === true" @click="submit" :loading="loading">提交</el-button>
  </el-form>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
import InfoUtils from "../utils/InfoUtils";
import simpleUtils from "../utils/simpleUtils";
let formRef = ref(null)
let rules = reactive({
  classname: [
    {required: true, message: "请输入班级名称", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkAllChineseAndEnglishAndNumber(value)) {
          callback()
        } else {
          callback(new Error("班级名称不能包含特殊符号"))
        }
      }, trigger: "blur"
    }
  ],
  stuno: [
    {required: true, message: "请输入学号", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkCharAndNumber(value)) {
          callback()
        } else {
          callback(new Error("学号只能包含字母与数字"))
        }
      }, trigger: "blur"
    }
  ],
  year: [
    {required: true, message: "请输入入学年份", trigger: "blur"},
    {type: "number", message: "请输入数字", trigger: "blur"}
  ],
  realname: [
    {required: true, message: "请输入真实姓名", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkAllChineseAndEnglishAndNumber(value)) {
          callback()
        } else {
          callback(new Error("真实姓名不能包含特殊符号"))
        }
      }, trigger: "blur"
    }
  ]
})
let userinfoTmp = ref({
  "classname": "",
  "school": 1,
  "year": 0,
  "stuno": "",
  "username": "",
  "realname": ""
})
let editStatus = ref(false)
let userinfo = ref({
  "classname": "",
  "school": 1,
  "year": 0,
  "stuno": "",
  "username": "",
  "realname": ""
})
const loading = ref(false)
const schoolList = ref([])
onMounted(()=>{
  InfoUtils.getSchoolMap().then(res => {
    schoolList.value = res.schoolList
  })
  axiosUtil.getMyInfo().then(res=>{
    if(res.data.code === 200){
      userinfo.value = res.data.result
    }
  }).catch(err=>{
    ElMessage.error("网络异常")
  })
})
let startEdit = ()=>{
  userinfoTmp.value.classname = userinfo.value.classname
  userinfoTmp.value.school = userinfo.value.school
  userinfoTmp.value.year = userinfo.value.year
  userinfoTmp.value.stuno = userinfo.value.stuno
  userinfoTmp.value.username = userinfo.value.username
  userinfoTmp.value.realname = userinfo.value.realname
  editStatus.value = true
}
let cancel = ()=>{
  userinfo.value.classname = userinfoTmp.value.classname
  userinfo.value.school = userinfoTmp.value.school
  userinfo.value.year = userinfoTmp.value.year
  userinfo.value.stuno = userinfoTmp.value.stuno
  userinfo.value.username = userinfoTmp.value.username
  userinfo.value.realname = userinfoTmp.value.realname
  editStatus.value = false
}
let submit = ()=>{
  if (formRef.value === null) return
  formRef.value.validate((valid)=>{
    if (valid) {
      loading.value = true
      axiosUtil.updateMyInfo(userinfo.value.classname,userinfo.value.year,userinfo.value.stuno,userinfo.value.realname).then(res=>{
        if (res.data.code===200) {
          ElMessage.success("修改成功")
          editStatus.value = false
        }else{
          ElMessage.error(res.data.msg)
        }
      }).catch(err=>{
        console.log(err)
        ElMessage.error("网络异常")
      }).finally(()=>{
        loading.value = false
      })
    }
  })
}
</script>

<style scoped>

</style>