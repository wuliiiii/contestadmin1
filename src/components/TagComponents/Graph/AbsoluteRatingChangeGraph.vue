<template>
    <div ref="chart" style="width: 100%;height: 50vh">
    </div>
</template>

<script setup>
import {nextTick, watchEffect,ref} from "vue";
import simpleUtils from "../../../utils/simpleUtils";
import * as echarts from "echarts";
import {useWindowStore} from "../../../store/window";

const prop = defineProps({
  contestData : {
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
let chart = ref(null)
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
let render = () => {
  let renderData = prop.contestData
  for(let key in renderData) {
    renderData[key] = renderData[key].sort((a,b) => {
      return a.startTimeStamp - b.startTimeStamp
    })
  }
  let minTime = new Date().getTime()
  for(let key in renderData) {
    if(renderData[key].length > 0) {
      minTime = Math.min(minTime,renderData[key][0].startTimeStamp*1000)
    }
  }
  let arr = simpleUtils.generateMonthArray(minTime,new Date().getTime())
  let xData = []
  let series = []
  let legend = []
  for(let item of arr){
    xData.push(item.year + "-" + item.month)
  }
  for(let key in renderData) {
    let data = []
    let i = 0;
    let nowRating = 0
    for(let item of arr) {
      while(i!==renderData[key].length) {
        let nt = new Date(renderData[key][i].startTimeStamp*1000)
        if(nt.getUTCFullYear() <= item.year && nt.getMonth()+1 <= item.month) {
          if(renderData[key][i].diff !== 0) {
            nowRating = renderData[key][i].rating
          }
          i++
        }else{
          break
        }
      }
      data.push(nowRating)
    }
    series.push({
      name: prop.userInfo[key].realname,
      type: 'line',
      smooth: true,
      data: data
    })
    legend.push(prop.userInfo[key].realname)
  }

  let option = {
    title: {
      text: prop.type + ' Rating Change',
      left: 'center'
    },
    tooltip: {
      trigger: 'axis'
    },
    dataZoom: [
      {
        type: 'inside'
      },
      {
        type: 'slider'
      }
    ],
    legend: {
      data: legend,
      top: '8%'
    },
    xAxis: {
      type: 'category',
      data: xData
    },
    yAxis: {
      type: 'value'
    },
    series: series
  }
  let e = echarts.init(chart.value)
  e.setOption(option)
  closer.value = () => {
    e.resize();
  }
}
watchEffect(()=>{
  if(prop.dataFinished) {
      nextTick(() => {
        render()
      })
  }
})
</script>

<style scoped>

</style>