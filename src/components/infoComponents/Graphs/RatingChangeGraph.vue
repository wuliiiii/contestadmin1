<template>
  <div id="graph" ref="chart">
  </div>
</template>

<script setup>
import {nextTick, onMounted, ref, watchEffect} from "vue";
import * as echarts from "echarts";
import infoUtils from "../../../utils/InfoUtils";
import {useWindowStore} from "../../../store/window";
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
let schoolMap = ref({})
const chart = ref(null)
let color = ['#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc']
const prop = defineProps({
  renderData: {
    type: Array,
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
const render = () => {
  let TimeData = []
  for(let key in prop.renderData) {
    TimeData.push(key)
  }
  TimeData = TimeData.sort((a,b) => {
    let aYear = Number.parseInt(String(a).split("-")[0])
    let aMonth = Number.parseInt(String(a).split("-")[1])
    let bYear = Number.parseInt(String(b).split("-")[0])
    let bMonth = Number.parseInt(String(b).split("-")[1])
    if(aYear !== bYear) {
      return aYear - bYear
    } else {
      return aMonth - bMonth
    }
  })
  let data = []
  let cMp = new Map;
  for(let i = 0; i < TimeData.length; i++) {
    let row = []
    for(let uid in prop.renderData[TimeData[i]]) {
      if(!cMp.has(uid)) {
        cMp.set(uid,Math.floor(Math.random() * color.length) );
      }
      row.push([prop.renderData[TimeData[i]][uid],prop.userInfo[uid].school +"\n"+ prop.userInfo[uid].classname+"-"+prop.userInfo[uid].realname,cMp.get(uid)])
    }
    data.push(row);
  }
  console.log(TimeData,data)
  let updateFrequency = 1000
  let option = {
    grid: {
      top: 10,
      bottom: 30,
      left: 150,
      right: 80
    },
    xAxis: {
      max: 'dataMax',
      axisLabel: {
        formatter: function (n) {
          return Math.round(n) + '';
        }
      }
    },
    yAxis: {
      type: 'category',
      inverse: true,
      max: 10,
      axisLabel: {
        show: true,
        fontSize: 12,
        formatter: function (value) {
          return value;
        }
      },
      animationDuration: updateFrequency,
      animationDurationUpdate: updateFrequency
    },
    series: [
      {
        realtimeSort: true,
        seriesLayoutBy: 'column',
        type: 'bar',
        itemStyle:{
          color:function (param) {
            return color[param.value[2]];
          }
        },
        encode: {
          x: 0,
          y: 1,
        },
        label: {
          show: true,
          precision: 1,
          position: 'right',
          valueAnimation: true,
          fontFamily: 'monospace'
        }
      }
    ],
    // Disable init animation.
    animationDuration: updateFrequency,
    animationDurationUpdate: updateFrequency,
    animationEasing: 'linear',
    animationEasingUpdate: 'linear',
    graphic: {
      elements: [
        {
          type: 'text',
          right: 0,
          bottom: 0,
          style: {
            text: TimeData[0],
            font: 'bolder 50px monospace',
            fill: 'rgba(100, 100, 100, 0.25)'
          },
          z: 100
        }
      ]
    }
  };
  let myChart = echarts.init(chart.value)
  myChart.setOption(option)
  for(let i = 0;i<TimeData.length;i++) {
    (function(i) {
      setTimeout(()=>{
        update(i)
      },i*updateFrequency)
    })(i)
  }
  let update = (idx) => {
    option.graphic.elements[0].style.text = TimeData[idx]
    option.series[0].data = data[idx]
    myChart.setOption(option)
  }
  closer.value = () => {
    myChart.resize();
  }
}
watchEffect(() => {
  if(prop.dataFinished) {
      nextTick(()=>{
        render()
      })
  }
})
</script>

<style scoped>
 #graph {
   width: 100%;
  height: 100%;
 }
</style>