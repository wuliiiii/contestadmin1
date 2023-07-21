<template>
  <div ref="graph" id="graph" style="width: 100%;height: 60vh">
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
  data : {
    type : Array,
    required : true
  },
  type:{
    type:String,
    required:true
  }
})
const graph = ref(null)
let render = () => {
  let xData = []
  let sData = []
  let cnt = 0
  for(let item of prop.data) {
    if(item["participate"] === 0) {
      continue
    }
    let date = new Date(item["startTimeStamp"]*1000)
    let sDate = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate()
    if(prop.type === "cf") {
      xData.push(item["name"] + "\n" +sDate)
    }else if(prop.type === "ac") {
      xData.push(item["name"] + "\n" +sDate)
    }

    sData.push(item["participate"])
    cnt++;
    if(cnt >= 10) {
      break
    }
  }
  let xAxis =  [
    {
      type: 'category',
      data: xData
    }
  ]
  let series = [{
    name: "最近参与人数",
    type: 'bar',
    emphasis: {
      focus: 'series'
    },
    label: {
      show: true,
      position: 'top'
    },
    colorBy: 'data',
    data: sData
  }]
  let option = {
    title:{
      left:'center',
      text:""
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow',
      }
    },
    toolbox: {
      show: true,
      feature: {
        dataZoom: {
          yAxisIndex: 'none'
        },
        dataView: { readOnly: false },
        magicType: { type: ['line', 'bar'] },
        restore: {},
        saveAsImage: {}
      }
    },
    grid: {
      top: '15%',
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis,
    yAxis: [
      {
        type: 'value'
      }
    ],
    series
  };
  let myChart = echarts.init(graph.value);
  myChart.setOption(option);
  closer.value = () => {
    myChart.resize();
  }
}

watchEffect(() => {
  if(prop.dataFin&&prop.data) {
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