<template>
  <el-card style="width: 100%" >
    <el-row type="flex" justify="space-around">
      <el-col v-for="(item,index) in questionSort" :key="index" :span="6" :offset="2">
        <div :id="index" style="width: 20vw;height: 20vw"></div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import {computed, watchEffect, ref, onMounted, nextTick} from "vue";
import * as echarts from "echarts";
import {useWindowStore} from "../../../store/window";
let kRefs = ref([])
const prop = defineProps({
  rootData: {
    type: Array,
    required: true,
  },
  type: {
    type: String,
    required: true,
  },
  contestData: {
    type: Object,
    required: true,
  }
})
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
let questionSort = computed(()=>{
  let questionSort = {}
  for (let submit of prop.rootData) {
    if (!questionSort.hasOwnProperty(submit.qIndex)) {
      questionSort[submit.qIndex] = {
      }
    }
    if(submit.isAfter === 1) continue;
    questionSort[submit.qIndex].hasOwnProperty(submit.status) ? questionSort[submit.qIndex][submit.status] += 1 : questionSort[submit.qIndex][submit.status] = 1
  }
  return questionSort
})
watchEffect(()=>{
  for(let id in questionSort.value) {
    console.log(id)
    nextTick(()=>{
      let c = echarts.init(document.getElementById(id))
      let data = []
      for (let status in questionSort.value[id]) {
        data.push({
          name: status,
          value: questionSort.value[id][status],
        })
      }
      c.setOption({
        title: {
          left: 'center',
          text: id,
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: id,
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
              }
            },
            labelLine: {
              show: false
            },
            data:data
          }
        ]})
      closer.value = () => {
        c.resize();
      }
    })
  }

})


</script>

<style scoped>

</style>