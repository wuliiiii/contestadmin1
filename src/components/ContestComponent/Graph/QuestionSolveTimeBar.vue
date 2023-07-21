<template>
  <el-card style="width: 100%" >
    <div ref="graph" style="height: 30vh"></div>
  </el-card>
</template>

<script setup>
import {computed, watchEffect, ref, onMounted, nextTick} from "vue";
import * as echarts from "echarts";
import {useWindowStore} from "../../../store/window";
let graph = ref(null)
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
const timeSpan = computed(()=>{
  let inContestData = prop.rootData.filter(v=>v.isAfter === 0)
  let arr = []
  for(var i = prop.contestData.startTimeStamp;i<prop.contestData.endTimeStamp;i+=600){
    arr.push({})
  }
  for (let submit of inContestData) {
    var offset = 0
    if (prop.type === 'ac'){
      offset = submit.submitTime - prop.contestData.startTimeStamp
    }else if(prop.type === 'cf') {
      offset = submit.submitTime - prop.contestData.startTimeStamp
    }
    let idx = Math.floor(offset/600)
    if(!arr[idx].hasOwnProperty(submit.qIndex)) {
      arr[idx][submit.qIndex] = {0:0,1:0}
    }

    if(submit.status === "OK"){
      arr[idx][submit.qIndex][1] += 1
    }else{
      arr[idx][submit.qIndex][0] += 1
    }
  }
  return arr
})
const submitType = computed(()=>{
  let inContestData = prop.rootData.filter(v=>v.isAfter === 0)
  let st = new Set()
  for (let submit of inContestData) {
    st.add(submit.qIndex)
  }
  return st
})
const baseOption = {
  title: {
    text: '题目提交时间分布',
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  legend: {},
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  yAxis: [
    {
      type: 'value'
    }
  ]
}
onMounted(()=>{
  let xData = []
  for(var i = prop.contestData.startTimeStamp ;i<prop.contestData.endTimeStamp;i+=600){
    xData.push(new Date(i*1000).toLocaleTimeString())
  }
  let series = []
  for(var qIdx of submitType.value) {
    let data1 = []
    let data2 = []
    for(var i = 0;i<timeSpan.value.length;i++){
      if(timeSpan.value[i].hasOwnProperty(qIdx)){
        data1.push(timeSpan.value[i][qIdx][0])
        data2.push(timeSpan.value[i][qIdx][1])
      }else{
        data1.push(0)
        data2.push(0)
      }
    }
    series.push({
      name: qIdx + '_Err',
      type: 'bar',
      stack: 'normal',
      emphasis: {
        focus: 'series'
      },
      data: data1
    })
    series.push({
      name: qIdx + '_AC',
      type: 'bar',
      stack: 'normal',
      emphasis: {
        focus: 'series'
      },
      data: data2
    })
  }
  let option = {
    ...baseOption,
    xAxis: {
      type: 'category',
      data: xData
    },
    series: series
  }
  nextTick(()=>{
    let c = echarts.init(graph.value)
    c.setOption(option)
    closer.value = () => {
      c.resize();
    }
  })

})
</script>

<style scoped>

</style>