<template>
  <div ref="graph" id="graph" v-show="fin">
  </div>
</template>

<script setup>
import {nextTick, watchEffect, ref, defineExpose} from "vue";
import * as echarts from "echarts";
import {useWindowStore} from "../../../store/window";
import infoUtils from "../../../utils/InfoUtils";
import simpleUtils from "../../../utils/simpleUtils";
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
let graph = ref(null)
let getOption = () => {
  let yData = []
  let yDataCnt = {}
  for(let item of prop.tags) {
    yDataCnt[item.tagName] = 0
  }
  let sortInfo = prop.okSubmitList.sort((a,b) => {
    return a.firstSubmitTime - b.firstSubmitTime
  })
  let xData = []
  if(sortInfo.length > 0){
    for(let item of simpleUtils.generateDayArray(sortInfo[0].firstSubmitTime*1000,new Date().getTime())) {
      xData.push(item.year + "-" + item.month + "-" + item.day)
    }
  }
  let series = []
  let mp = {}
  for(let row of prop.okSubmitList){
    if(!mp.hasOwnProperty(row.uid)) {
      mp[row.uid] = []
    }
    if(row.tags === "" || row.difficulty ===0) {
      continue
    }
    let date = new Date(row.firstSubmitTime*1000)
    let dateStr = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate()
    for(let tag of String(row.tags).split(",")) {
      mp[row.uid].push([dateStr,tag,row.difficulty,row.cid,row.qIndex])
      yDataCnt[tag]++
    }
    // mp[row.uid].push([dateStr,String(row.tags).split(",")[0],row.difficulty])
    // yDataCnt[String(row.tags).split(",")[0]]++
  }
  for(let key in yDataCnt) {
    if(yDataCnt[key] > 0) {
      yData.push(key)
    }
  }
  // yData.sort((a,b) => {
  //   return yDataCnt[b] - yDataCnt[a]
  // })
  let legendData = []
  for(let key in mp) {
    series.push({
      name: prop.studentMap[key]["realname"],
      type: 'scatter',
      data: mp[key],
      symbolSize: 10,
    })
    legendData.push(prop.studentMap[key]["realname"])
  }
  return {
    grid: {
      left: '20%',
      right: 150,
      top: '10%',
      bottom: '5%'
    },
    tooltip: {
      backgroundColor: 'rgba(255,255,255,0.7)',
      formatter: function (param) {
        var value = param.value;
        // prettier-ignore
        return [
          '题目: ' + value[3] + value[4],
          '时间: ' + value[0],
          '标签: ' + value[1],
          '难度: ' + value[2]
        ].join('<br/>');
      }
    },
    dataZoom: [
      {
        type: 'slider',
        start: 90,
      },
      {
        type: 'slider',
        orient: "vertical",
        left: '0%',
      }
    ],
    xAxis: {
      type: 'category',
      name: '日期',
      nameGap: 16,
      nameTextStyle: {
        fontSize: 16
      },
      splitLine: {
        show: false
      },
      data: xData
    },
    yAxis: {
      type: 'category',
      name: '标签',
      nameLocation: 'end',
      nameGap: 20,
      nameTextStyle: {
        fontSize: 16
      },
      splitLine: {
        show: false
      },
      data:yData
    },
    legend: {
      top: 10,
      data: legendData,
      textStyle: {
        fontSize: 16
      }
    },
    visualMap: [
      {
        left: 'right',
        top: '10%',
        dimension: 2,
        min: 800,
        max: 3800,
        itemWidth: 30,
        itemHeight: 120,
        calculable: true,
        precision: 0.1,
        text: ['圆形大小：Rating'],
        textGap: 30,
        inRange: {
          symbolSize: [10, 70]
        },
        outOfRange: {
          symbolSize: [10, 70],
          color: ['rgba(255,255,255,0.4)']
        },
        controller: {
          inRange: {
            color: ['#c23531']
          },
          outOfRange: {
            color: ['#999']
          }
        }
      }
    ],
    series: series
  }
}
let reRender = ref(()=>{})
let render = ()=>{
  let option = getOption()
  console.log(option)
  let myChart = echarts.init(graph.value);
  myChart.setOption(option);
  closer.value = () => {
    myChart.resize();
  }
  reRender.value = () => {
    myChart.setOption(getOption());
  }
}

defineExpose({
  reload() {
    reRender.value()
  }
})
const prop = defineProps({
  fin: {
    type: Boolean,
    required: true
  },
  tags: {
    type: Array,
    required: true
  },
  okSubmitList: {
    type: Array,
    required: true
  },
  studentMap: {
    type: Object,
    required: true
  }
})
watchEffect(() => {
  if(prop.fin&&prop.tags&&prop.okSubmitList) {
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