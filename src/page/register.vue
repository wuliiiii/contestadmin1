<template>
  <div class="container">
    <el-card class="card">
      <el-form
          ref="formRef"
          :rules="rules"
          label-width="80px"
          :model="userinfo"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userinfo.username"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="userinfo.password" type="password"/>
        </el-form-item>
        <el-form-item label="确认密码" prop="passwordAgain">
          <el-input v-model="userinfo.passwordAgain" type="password"/>
        </el-form-item>
        <el-form-item label="班级" prop="classname">
          <el-input v-model="userinfo.classname"/>
        </el-form-item>
        <el-form-item label="学号" prop="stuno">
          <el-input v-model="userinfo.stuno"/>
        </el-form-item>
        <el-form-item label="学校" prop="school">
          <el-select v-model="userinfo.school">
            <el-option v-for="item in schoolList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年级" prop="year">
          <el-input-number v-model="userinfo.year" :min="2000" :max="(new Date()).getFullYear()"/>
        </el-form-item>
        <el-form-item label="姓名" prop="realname">
          <el-input v-model="userinfo.realname"/>
        </el-form-item>
      </el-form>
      <div class="btn">
        <el-button type="primary" @click="submit" :loading="loading">提交</el-button>
      </div>

    </el-card>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive, onMounted, ref} from "vue";
import InfoUtils from "../utils/InfoUtils";
import simpleUtils from "../utils/simpleUtils";
import axiosUtil from "../utils/axiosUtil";
import {ElMessage} from "element-plus";
const router = useRouter()
const schoolList = ref([])
const rules = reactive({
  username: [
    {required: true, message: "请输入用户名", trigger: "blur"},
    {min: 4, max: 12, message: "用户名长度必须在4到12位之间", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (simpleUtils.checkCharAndNumberAndUnderscore(value)) {
          callback()
        } else {
          callback(new Error("用户名只能包含数字、字母和下划线"))
        }
      }, trigger: "blur"
    }
  ],
  password: [
    {required: true, message: "请输入密码", trigger: "blur"},
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
  passwordAgain: [
    {required: true, message: "请再次输入密码", trigger: "blur"},
    {
      validator: (rule, value, callback) => {
        if (value === userinfo.password) {
          callback()
        } else {
          callback(new Error("两次输入的密码不一致"))
        }
      }, trigger: "blur"
    }
  ],
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
  school: [
    {required: true, message: "请选择学校", trigger: "blur"}
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
const formRef = ref(null)
const loading = ref(false)
let userinfo = reactive({
  username: "",
  password: "",
  passwordAgain: "",
  classname: "",
  stuno: "",
  school: 1,
  year: 0,
  realname: ""
})
onMounted(() => {
  InfoUtils.getSchoolMap().then(res => {
    schoolList.value = res.schoolList
  })
})
let submit = () => {
  if (!formRef) return
  formRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      axiosUtil.register(userinfo.username, simpleUtils.SHA512(userinfo.password),
          userinfo.school, userinfo.classname, userinfo.year, userinfo.stuno, userinfo.realname).then(res=>{
            if(res.data.code === 200) {
              ElMessage.success("成功提交注册申请，请等待管理员审核")
              router.push("/index")
            }else{
              ElMessage.error(res.data.msg)
            }
      }).catch(err=>{
        ElMessage.error("网络错误")
        console.log(err)
      }).finally(()=>{
        loading.value = false
      })
    }
  })

}
</script>

<style scoped lang="scss">
.container {
  min-height: 100vh;
  min-width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
}
.btn{
  display: flex;
  justify-content: center;
}

</style>