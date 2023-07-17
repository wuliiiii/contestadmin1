<template>
  <el-dialog v-model="display" @close="close" title="申请收录新codeforces账户" ref="formRef">
    <el-form :model="data" :rules="rules" ref="formRef" label-width="100px">
      <el-form-item label="用户名" prop="atcoder_id">
        <el-input v-model="data.atcoder_id" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="设为主账户" prop="is_main">
        <el-radio-group v-model="data.is_main">
          <el-radio label="0">否</el-radio>
          <el-radio label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="close()">取消</el-button>
        <el-button type="primary" @click="submit" :loading="loading"
        >提交</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {reactive, onMounted, ref, defineExpose} from "vue";
import {ElMessage} from "element-plus";
import axiosUtil from "../../utils/axiosUtil";
import simpleUtils from "../../utils/simpleUtils";

let data = ref({
  atcoder_id: "",
  is_main: "0",
})
let formRef = ref(null)
let loading = ref(false)
const display = ref(false)
let rules = reactive({
  atcoder_id:[
    {required: true, message: "请输入用户名", trigger: "blur"},
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
  is_main:[
    {required: true, message: "请选择是否为主账户", trigger: "blur"}
  ]

})
let submit = ()=> {
  if (formRef.value === null) {
    return
  }
  formRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      axiosUtil.atcoderAPI.addNewAtcoderAccount(data.value.atcoder_id,parseInt(data.value.is_main)).then(res=>{
        if(res.data.code === 200){
          ElMessage.success("提交成功")
          close()
        }else{
          ElMessage.error(res.data.message)
        }
      }).catch(err=>{
        console.log(err)
        ElMessage.error("提交失败")
      }).finally(()=>{
        loading.value = false
      })
    }
  })
}
defineExpose({
  open() {
    display.value = true
  }
})
let close = () => {
  data.value = reactive({
    atcoder_id: "",
    is_main: "0",
  })
  display.value = false
}
</script>

<style scoped>

</style>