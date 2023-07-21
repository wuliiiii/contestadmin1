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
let hasRender = ref(false)  // hasRender：作为一个 ref 变量，它用于记录图表是否已经渲染过。通过监视该变量的值是否为 true，可以避免重复渲染图表
let renderData = ref({})  // renderData：作为一个 ref 变量，它用于存储图表的数据。当 prop.data 发生变化时，将新的数据赋值给 renderData，从而触发对应的图表重新渲染
let data = ref()
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})
/*
prop.data：通过 watchEffect 监听 prop.data 的变化，
在 prop.data 发生变化时，将新的数据赋值给 renderData，
并在下一个刷新周期使用 nextTick 调用 render() 函数重新渲染图表。
*/
const prop = defineProps({
  type:{
    type:String,
    required:true
  },
  data:{
    type:Object,
    required: true
  }

})
let render = () => {
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
    // dataZoom 是 ECharts（百度开源的数据可视化库）中的一个功能，用于对图表中的数据区域进行缩放和漫游操作
    dataZoom: [
      {
        type: 'inside', // 内置的数据区域缩放类型，允许在图表内部进行缩放
        start: 0, // 数据缩放的起始位置，取值范围为 0-100，表示百分比
        end: 20 // 数据缩放的结束位置，取值范围为 0-100，表示百分比
      },
      {
        // 第二个元素定义了一个缩放框（slider）类型的数据区域缩放
        start: 0,
        end: 20
      }
    ],
    yAxis: {
      type: 'value'
    },
  };
  let minTime = new Date().getTime()
  for(let key in renderData.value) {
    renderData.value[key] = renderData.value[key].sort((a,b) => {
      return a.startTimeStamp - b.startTimeStamp
    })
    if(renderData.value[key].length > 0) {
      minTime = Math.min(minTime,renderData.value[key][0].startTimeStamp*1000)
    }
  }
  let arr = simpleUtils.generateMonthArray(minTime,new Date().getTime())

  let xData = []
  for(var item of arr) {
    xData.push(item.year + "-" + item.month)
  }
  let series = [
  ]
  for(let key in renderData.value) {
    let data = []
    let nowRating = 0
    let i = 0
    for(let item of arr) {
      while(i!==renderData.value[key].length) {
        let nt = new Date(renderData.value[key][i].startTimeStamp*1000)
        if(nt.getUTCFullYear() <= item.year && nt.getMonth()+1 <= item.month) {
          if(renderData.value[key][i].diff !== 0) {
            nowRating = renderData.value[key][i].rating
          }
          i++
        }else{
          break
        }
      }
      data.push(nowRating)
    }
    series.push({
      data: data,
      type: 'line',
      smooth:true,
      name: key
    })
  }
  let e = echarts.init(chart.value)
  e.setOption({
    ...option,
    xAxis: {
      type: 'category',
      data: xData
    },
    series: series
  })
  closer.value = () => {
    e.resize();
  }
  loading.value = false
  hasRender.value = true
}
watchEffect(()=>{
  if(prop.data) {
    if (renderData.value !== prop.data) {
      renderData.value = prop.data
      nextTick(() => {
        render()
      })
    }
  }
})
</script>

<style scoped>

</style>