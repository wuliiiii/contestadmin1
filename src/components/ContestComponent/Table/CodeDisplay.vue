<template>
  <el-dialog v-model="display" @close="close">
    <highlightjs autodectect :code="codeData" ></highlightjs>
  </el-dialog>
</template>

<script setup>
import {ref, defineProps, defineExpose, onMounted} from "vue";
const props = defineProps({
  codeData: {
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
</script>

<script>
import hljsVuePlugin from "@highlightjs/vue-plugin";
export default {
  components:{
    highlightjs:hljsVuePlugin.component
  }
}
</script>
<style scoped>

</style>