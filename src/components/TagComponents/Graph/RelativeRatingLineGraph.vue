<template>
    <div ref="chart" style="width: 100%;height: 50vh">
    </div>
</template>

<script setup>
import {nextTick, onMounted, watchEffect,ref} from "vue";
import simpleUtils from "../../../utils/simpleUtils";
import * as echarts from "echarts";
import {useWindowStore} from "../../../store/window";
const prop = defineProps({
  userInfo:{
    type: Object,
    required: true,
  },
  contestData: {
    type: Object,
    required: true,
  },
  dataFinished: {
    type: Boolean,
    required: true,
  },
  type: {
    type: String,
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
let chart = ref(null)
let render = () => {
  let renderData = prop.contestData
  for(let key in renderData) {
    renderData[key] = renderData[key].sort((a,b) => {
      return a.startTimeStamp - b.startTimeStamp
    })
  }
  let legend = []
  let xData = []
  let seq = ['09','10','11','12','01','02','03','04','05','06','07','08']
  let year = ['firstYear','secondYear','thirdYear','fourthYear']
  for(let i = 0;i < 4;i++) {
    for(let j = 0;j < 12;j++) {
      xData.push(year[i] + "-" +seq[j])
    }
  }
  let series = []
  for(let key in prop.userInfo) {
    let startYear = prop.userInfo[key].year
    let endYear = prop.userInfo[key].year + 4
    let startDate = new Date()
    startDate.setFullYear(startYear)
    startDate.setMonth(8)
    let endDate = new Date()
    endDate.setMonth(7)
    endDate.setFullYear(endYear)
    let generateMonthArray = simpleUtils.generateMonthArray(startDate.getTime(),endDate.getTime());
    let data = []
    let i = 0;
    let nowRating = 0;
    for(let j = 0;j < generateMonthArray.length;j++) {
      while (renderData.hasOwnProperty(key)&&i!==renderData[key].length) {
        let nt = new Date(renderData[key][i].startTimeStamp*1000)
        if(nt.getUTCFullYear() <= generateMonthArray[j].year && nt.getMonth()+1 <= generateMonthArray[j].month) {
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
      text: prop.type +' Relative Rating Change',
      left: 'center'
    },
    dataZoom: [
      {
        type: 'inside'
      },
      {
        type: 'slider'
      }
    ],
    tooltip: {
      trigger: 'axis'
    },
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
  let myChart = echarts.init(chart.value)
  myChart.setOption(option)
  closer.value = () => {
    myChart.resize();
  }
}
watchEffect(()=>{
  if(prop.dataFinished) {
      nextTick(()=>{
        render();
      })
  }
})
</script>

<style scoped>

</style>