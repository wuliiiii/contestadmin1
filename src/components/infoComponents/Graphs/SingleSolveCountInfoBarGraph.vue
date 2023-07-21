<template>
  <el-card v-loading="loading" style="width: 100%">
    <div ref="chart" style="width: 100%;height: 30vh">
    </div>
  </el-card>
</template>

<script setup>
import {onMounted, watchEffect, ref, nextTick, onUpdated} from "vue";
import * as echarts from "echarts";
import simpleUtils from "../../../utils/simpleUtils";
import {useWindowStore} from "../../../store/window";
const timeProp = ref("submitTime")
const chart = ref(null)
let chartInst = ref(null)
const loading  = ref(true)
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
const prop = defineProps({
  beforeSolveData:{
    type:Array,
    required: true
  },
  afterSolveData:{
    type:Array,
    required: true
  }
})
onMounted(()=>{
})
let renderBeforeSolveData = ref([])
let renderAfterSolveData = ref([])
let hasRender = ref(false)
let render = () => {
  let beforeSolveData = prop.beforeSolveData.sort((a,b) => {
    return a[timeProp.value] - b[timeProp.value]
  })
  let afterSolveData = prop.afterSolveData.sort((a,b) => {
    return a[timeProp.value] - b[timeProp.value]
  })
  let minTime = new Date().getTime()
  if (afterSolveData.length === 0 && beforeSolveData.length === 0) return
  if (beforeSolveData&&beforeSolveData.length !== 0) {
    minTime = Math.min(minTime, beforeSolveData[0][timeProp.value])
  }
  if (afterSolveData&&afterSolveData.length !== 0) {
    minTime = Math.min(minTime, afterSolveData[0][timeProp.value])
  }
  let DateArr = simpleUtils.generateMonthArray(minTime * 1000, new Date().getTime())
  console.log(DateArr)
  let xData = []
  let beforeSolve = []
  let afterSolve = []

  let total = []
  let afterSolveIt = 0
  let beforeSolveIt = 0
  for(let i = 0;i<DateArr.length;i++) {
    let cnt1 = 0
    while (afterSolveIt !== afterSolveData.length ) {
      let tdate = new Date(afterSolveData[afterSolveIt][timeProp.value] * 1000)
      if(tdate.getMonth() + 1 === DateArr[i].month && tdate.getUTCFullYear() === DateArr[i].year) {
        afterSolveIt++
        cnt1++
      }else{
        break
      }
    }
    let cnt2 = 0
    while (beforeSolveIt !== beforeSolveData.length ) {
      let tdate = new Date(beforeSolveData[beforeSolveIt][timeProp.value] * 1000)
      if(tdate.getMonth() + 1 === DateArr[i].month && tdate.getUTCFullYear() === DateArr[i].year) {
        beforeSolveIt++
        cnt2++
      }else{
        break
      }
    }
    xData.push(DateArr[i].year + "-" + DateArr[i].month)
    beforeSolve.push(cnt2)
    afterSolve.push(cnt1)
    total.push(cnt2+cnt1)
  }
  let option = {
    tooltip: {
      trigger: 'axis',
      position: function (pt) {
        return [pt[0], '10%'];
      }
    },
    title: {
      left: 'center',
      text: '各月提交数'
    },
    dataZoom: [
      {
        type: 'inside',
        start: 0,
        end: 100
      },
      {
        start: 0,
        end: 100
      }
    ],
    xAxis: {
      type: 'category',
      data: xData
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: "总数",
        data: total,
        type: 'line',
        smooth: true
      },
      {
        name:"赛时提交数",
        data: beforeSolve,
        stack:"solve",
        type :'bar',
      },
      {
        name:"补题提交数",
        data: afterSolve,
        stack:"solve",
        type:'bar'
      }
    ]
  };
  let e = echarts.init(chart.value)
  e.setOption(option)
  closer.value = () => {
    e.resize();
  }
  chartInst.value = e
  hasRender.value = true
  loading.value = false
}
onUpdated(()=>{
  if(chartInst.value)
    chartInst.value.resize()
})
watchEffect(()=>{
  renderBeforeSolveData.value = prop.beforeSolveData
  renderAfterSolveData.value = prop.afterSolveData
  if (!hasRender.value&&renderAfterSolveData.value && renderBeforeSolveData.value) {
    nextTick(()=>{
      render()
    })
  }
})
</script>

<style scoped>

</style>