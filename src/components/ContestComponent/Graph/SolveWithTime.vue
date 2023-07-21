<template>
  <el-card style="width: 100%" >
    <div ref="graph" style="height: 80vh"></div>
  </el-card>
</template>

<script setup>
import {computed, watchEffect, ref, onMounted, nextTick} from "vue";
import * as echarts from "echarts";
import simpleUtils from "../../../utils/simpleUtils";
import {useWindowStore} from "../../../store/window";
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
let graph = ref(null)
let updateFrequency = 1000
let timeSpan = 3 * 60
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
let OwnerMap = ref({})
let usernameToOwner = (id) => {
  return OwnerMap.value[id]
}
onMounted(() => {
  if (prop.type == 'ac') {
    for (let submit of prop.rootData) {
      OwnerMap.value[submit.atcoderId] = submit.realname
    }
  } else if (prop.type == 'cf') {
    for (let submit of prop.rootData) {
      OwnerMap.value[submit.codeforcesId] = submit.realname
    }
  }
  let timeArr = []
  for (var i = prop.contestData.startTimeStamp; i < prop.contestData.endTimeStamp; i+=timeSpan) {
    timeArr.push({})
  }
  let usernameSet = new Set()
  for (var submit of prop.rootData.filter(v=>v.isAfter === 0)) {
    var offset = 0
    if (prop.type === 'ac') {
      offset = submit.submitTime - prop.contestData.startTimeStamp
    } else if (prop.type === 'cf') {
      offset = submit.submitTime - prop.contestData.startTimeStamp
    }
    let idx = Math.floor(offset / timeSpan)
    if (prop.type === 'ac' && submit.status === 'AC') {
      usernameSet.add(submit.atcoderId)
      if (!timeArr[idx].hasOwnProperty(submit.atcoderId)) {
        timeArr[idx][submit.atcoderId] = 0;
      }
      timeArr[idx][submit.atcoderId]++;
    } else if (prop.type === 'cf' && submit.status === 'OK') {
      usernameSet.add(submit.codeforcesId)
      if (!timeArr[idx].hasOwnProperty(submit.codeforcesId)) {
        timeArr[idx][submit.codeforcesId] = 0;
      }
      timeArr[idx][submit.codeforcesId]++;
    }
  }
  let data = {}
  for(let i = 0;i<timeArr.length;i++){
    data[prop.contestData.startTimeStamp + i*timeSpan] = []
    for(let username of usernameSet) {
      if (i > 0) {
        let last = 0
        if (timeArr[i - 1].hasOwnProperty(username)){
          last = timeArr[i-1][username]
        }
        if(timeArr[i].hasOwnProperty(username)) {
          timeArr[i][username] += last;
        }else{
          timeArr[i][username] = last;
        }
      }
      if (timeArr[i].hasOwnProperty(username)){
        data[prop.contestData.startTimeStamp + i*timeSpan].push([timeArr[i][username],username])
      }
    }
  }

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
        fontSize: 14,
        formatter: function (value) {
          return usernameToOwner(value) + '(' + value + ')';
        },
        rich: {
          flag: {
            fontSize: 25,
            padding: 5
          }
        }
      },
      animationDuration: 300,
      animationDurationUpdate: 300
    },
    series: [
      {
        realtimeSort: true,
        seriesLayoutBy: 'column',
        type: 'bar',
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
    animationDuration: 0,
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
            text: simpleUtils.timeStampToLocalTime(prop.contestData.startTimeStamp*1000),
            font: 'bolder 40px monospace',
            fill: 'rgba(100, 100, 100, 0.25)'
          },
          z: 100
        }
      ]
    }
  };
  let myChart = echarts.init(graph.value)
  closer.value = () => {
    myChart.resize();
  }
  myChart.setOption(option);
  for (let i = 0; i < timeArr.length; ++i) {
    (function (i) {
      setTimeout(function () {
        updateYear(i*timeSpan + prop.contestData.startTimeStamp);
      }, i  * updateFrequency);
    })(i);
  }
  function updateYear(time) {
    let source = data[time]
    option.series[0].data = source;
    option.graphic.elements[0].style.text = simpleUtils.timeStampToLocalTime(time*1000);
    myChart.setOption(option);
  }
})

</script>

<style scoped>

</style>