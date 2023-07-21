<template>
    <el-card v-loading="loading" >
      <div ref="chart" style="width: 100%;height: 30vh">
      </div>
    </el-card>
</template>

<script setup>

import {nextTick, onMounted, ref, watchEffect} from "vue";
import simpleUtils from "../../../utils/simpleUtils";
import {ElMessage} from "element-plus";
import * as echarts from "echarts";
import {useWindowStore} from "../../../store/window";
let chart = ref(null)
let loading = ref(true)
let hasRender = ref(false)
let renderData = ref([])
let data = ref()
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
const prop = defineProps({
  type:{
    type:String,
    required:true
  },
  data:{
    type:Array,
    required: true
  }
})
let render = () => {
  let sortData = renderData.value.sort((a,b) => {
    return a.startTimeStamp - b.startTimeStamp
  })
  if(sortData&&sortData.length !== 0) {
    let arr = simpleUtils.generateMonthArray(sortData[0].startTimeStamp*1000,new Date().getTime())
    let i = 0;
    let data = []
    let xData = []
    let nowRating = sortData[0].rating
    for(var item of arr) {
      xData.push(item.year + "-" + item.month)
      while(i!==sortData.length - 1) {
        let nt = new Date(sortData[i+1].startTimeStamp*1000)
        if(nt.getUTCFullYear() <= item.year && nt.getMonth()+1 <= item.month) {
          if(sortData[i+1].diff !== 0) {
            nowRating = sortData[i+1].rating
          }
          i++
        }else{
          break
        }
      }
      data.push(nowRating)
    }
    var option = {
      tooltip: {
        trigger: 'axis',
        position: function (pt) {
          return [pt[0], '10%'];
        }
      },
      title: {
        left: 'center',
        text: '积分变化'
      },
      dataZoom: [
        {
          type: 'inside',
          start: 0,
          end: 20
        },
        {
          start: 0,
          end: 20
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
          data: data,
          type: 'line',
          smooth: true
        }
      ]
    };

    let e = echarts.init(chart.value)
    e.setOption(option)
    closer.value = () => {
      e.resize();
    }
    loading.value = false
    hasRender.value = true
  }
}
watchEffect(()=>{
  if(renderData.value.length === 0) {
    renderData.value = prop.data
    if (!hasRender.value) {
      nextTick(() => {
        render()
      })
    }
  }

})
</script>

<style scoped lang="scss">

</style>