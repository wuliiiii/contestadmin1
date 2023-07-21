<template>
  <div ref="chart" style="width: 100%;height: 40vh">
  </div>
</template>

<script setup>

import {watchEffect, ref, nextTick} from "vue";
import * as echarts from "echarts";
import simpleUtils from "../../../utils/simpleUtils";
import {useWindowStore} from "../../../store/window";

const prop = defineProps({
  submitData : {
    type: Object,
    required: true,
  },
  dataFinished: {
    type: Boolean,
    required: true,
  },
  userInfo: {
    type: Object,
    required: true,
  },
  type: {
    type: String,
    required: true,
  }
})

const chart = ref(null)
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
const render = () => {
  let renderData = prop.submitData
  let xData = []
  let minTime = new Date().getTime()
  for(let key in renderData) {
    for(let item of renderData[key]) {
      let year = Number.parseInt(String(item.ymDate).split("-")[0])
      let month = Number.parseInt(String(item.ymDate).split("-")[1])
      let date = new Date(year,month-1,1)
      minTime = Math.min(minTime,date.getTime())
    }
  }
  let arr = simpleUtils.generateMonthArray(minTime,new Date().getTime())
  for(let item of arr){
    xData.push(item.year + "-" + item.month)
  }
  let series = []
let legend = []
  for(let key in renderData) {
    let data = []
    for(let item of arr) {
      let year = Number.parseInt(String(item.year))
      let month = Number.parseInt(String(item.month))
      let cnt = 0
      for(let item2 of renderData[key]) {
        let y = Number.parseInt(String(item2.ymDate).split("-")[0])
        let m = Number.parseInt(String(item2.ymDate).split("-")[1])
        if(y === year && m === month) {
          cnt += item2.submitCnt
        }
      }
      data.push(cnt)
    }
    console.log(prop.userInfo[key].realname,data)
    series.push({
      name: prop.userInfo[key].realname,
      type: 'bar',
      stack: '总量',
      data: data,
      emphasis: {
        focus: 'series'
      },
    })
    legend.push(prop.userInfo[key].realname)
  }
  let option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    title: {
      text: '提交次数统计',
      left: 'center'
    },
    legend: {
      data: legend,
      top: '8%'
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
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        boundaryGap: true,
        data: xData
      }
    ],
    yAxis: [
      {
        type: 'value'
      }
    ],
    series: series
  }
  let c = echarts.init(chart.value)
  c.setOption(option)
  closer.value = () => {
    c.resize();
  }
}
watchEffect(()=>{
  if(prop.dataFinished) {
    nextTick(()=>{
      render()
    })
  }
})
</script>

<style scoped>

</style>