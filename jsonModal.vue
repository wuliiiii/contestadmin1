<template>
  <el-dialog v-model="display" @close="close">
    <json-viewer
        :value="jsonData"
        :expand-depth=5
        copyable
        boxed
        expanded
        style="text-align: left"
        sort></json-viewer>
  </el-dialog>
</template>

<script setup>
import {ref, defineProps, defineExpose, onMounted} from "vue";
const props = defineProps({
  jsonData: {
    type: String,
    default:""
  },
})
const display = ref(false)
defineExpose({open(){
  display.value = true
  }})
let close = ()=>{
  display.value = false
}


onMounted(()=>{
  console.log("mounted")
})

</script>

<style scoped>

</style>