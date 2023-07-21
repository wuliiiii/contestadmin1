<template>
    <div ref="graph" id="graph">
    </div>
</template>

<script setup>
import {nextTick, ref, watchEffect} from "vue";
import * as echarts from "echarts";
import {useWindowStore} from "../../../store/window";
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
const prop = defineProps({
  dataFin : {
    type : Boolean,
    required : true
  },
  codeforcesData : {
    type : Array,
    required : true
  }
})
const graph = ref(null)
let render = () => {
  let yearData = []
  let demensions = ["1000-1200","1200-1400","1400-1600","1600-1800","1800+"]
  for(let year in prop.codeforcesData) {
    yearData.push(year)
  }
  yearData = yearData.sort((a,b) => a - b)
  let option = {
    title:{
      left:'center',
      text:"各年份ACM竞赛队员积分对比分析"
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow',
      }
    },
    legend: {
      type: 'scroll',
      top: '5%',
    },
    grid: {
      top: '15%',
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: yearData
      }
    ],
    yAxis: [
      {
        type: 'value'
      }
    ],
    series: [
    ]
  };
  for(let i = 0; i < demensions.length; i++) {
    let row2 = {
      name: "Codeforces " + demensions[i],
      type: 'bar',
      stack: 'Codeforces',
      emphasis: {
        focus: 'series'
      },
      data: []
    }
    for(let j = 0; j < yearData.length; j++) {
      let year = yearData[j]
      if(prop.codeforcesData.hasOwnProperty(year)) {
        row2.data.push(prop.codeforcesData[year][demensions[i]])
      } else {
        row2.data.push(0)
      }
    }
    option.series.push(row2)
  }
  let myChart = echarts.init(graph.value);
  myChart.setOption(option);
  closer.value = () => {
    myChart.resize();
  }
}



watchEffect(() => {
  if(prop.dataFin&&prop.codeforcesData) {
    nextTick(()=>{
      render()
    })
  }
})
</script>

<style scoped>
#graph{
  width: 100%;
  height: 100%;
}
</style>