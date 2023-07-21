<template>
  <div id="graph" ref="graph">
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
  atcoderData : {
    type : Array,
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
  let AtcoderDataMp = new Map()
  let CodeforcesDataMp = new Map()
  for (let data of prop.atcoderData) {
    let year = data.year
    if (yearData.indexOf(year) === -1) {
      yearData.push(year)
    }
    if (!AtcoderDataMp.has(year)) {
      AtcoderDataMp.set(year, [0, 0, 0, 0, 0])
    }
    if(data.rating >= 1000&&data.rating <= 1200) {
      AtcoderDataMp.get(year)[0] += 1
    } else if(data.rating > 1200&&data.rating <= 1400) {
      AtcoderDataMp.get(year)[1] += 1
    } else if(data.rating > 1400&&data.rating <= 1600) {
      AtcoderDataMp.get(year)[2] += 1
    } else if(data.rating > 1600&&data.rating <= 1800) {
      AtcoderDataMp.get(year)[3] += 1
    } else if (data.rating > 1800) {
      AtcoderDataMp.get(year)[4] += 1
    }
  }
  for (let data of prop.codeforcesData) {
    let year = data.year
    if (yearData.indexOf(year) === -1) {
      yearData.push(year)
    }
    if (!CodeforcesDataMp.has(year)) {
      CodeforcesDataMp.set(year, [0, 0, 0, 0, 0])
    }
    if(data.rating >= 1000&&data.rating <= 1200) {
      CodeforcesDataMp.get(year)[0] += 1
    } else if(data.rating > 1200&&data.rating <= 1400) {
      CodeforcesDataMp.get(year)[1] += 1
    } else if(data.rating > 1400&&data.rating <= 1600) {
      CodeforcesDataMp.get(year)[2] += 1
    } else if(data.rating > 1600&&data.rating <= 1800) {
      CodeforcesDataMp.get(year)[3] += 1
    } else if (data.rating > 1800) {
      CodeforcesDataMp.get(year)[4] += 1
    }
  }
  yearData = yearData.sort((a,b) => a - b)
  let option = {
    title:{
      left:'center',
      text:"各年级ACM竞赛队员数量对比分析"
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      type: 'scroll',
      top: '5%'
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
    let row1 = {
      name: "Atcoder " + demensions[i],
      type: 'bar',
      stack: 'Atcoder',
      emphasis: {
        focus: 'series'
      },
      data: []
    }
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
      if(AtcoderDataMp.has(year)) {
        row1.data.push(AtcoderDataMp.get(year)[i])
      } else {
        row1.data.push(0)
      }
      if(CodeforcesDataMp.has(year)) {
        row2.data.push(CodeforcesDataMp.get(year)[i])
      } else {
        row2.data.push(0)
      }
    }
    // option.series.push(row1)
    option.series.push(row2)
  }
  let myChart = echarts.init(graph.value);
  myChart.setOption(option);
  closer.value = () => {
    myChart.resize();
  }
}



watchEffect(() => {
  if(prop.dataFin&&prop.atcoderData&&prop.codeforcesData) {
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