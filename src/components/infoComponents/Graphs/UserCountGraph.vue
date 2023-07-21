<template>
  <el-card v-loading="loading" style="width: 100%">
    <div ref="chart" style="width: 100%;height: 60vh">
    </div>
  </el-card>
</template>

<script setup>
import {useWindowStore} from "../../../store/window";
import {onMounted, ref, watch, watchEffect} from "vue";
import * as echarts from "echarts";
import AxiosUtil from "../../../utils/axiosUtil";
import {ElMessage} from "element-plus";
const chart = ref(null)
const loading = ref(true)
const store = useWindowStore()
const closer = ref(()=>{})
store.$subscribe((mutation, state)=>{
  if("windowWidth" in mutation.payload) {
    closer.value()
  }
})

const baseOption = {
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },toolbox: {
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
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  yAxis: {
    type: 'value'
  }
}


onMounted(()=>{

  AxiosUtil.getAllUser().then((res)=>{
    if(res.data.code === 200) {
      let data = []
      let recentYear = []
      let tYear = new Date().getMonth() >= 9 ? new Date().getFullYear() : new Date().getFullYear() - 1;
      for(let i = 2015;i<=tYear;i++) {
        let nowCnt = 0;
        for(let j = 0;j<res.data.result.length;j++) {
          if(res.data.result[j].year === i&&res.data.result[j].school === 1) {
            nowCnt += 1;
          }
        }
        data.push(nowCnt)
        recentYear.push(i)
      }
      let series = [{
        name: "学生人数",
        type: 'bar',
        emphasis: {
          focus: 'series'
        },
        label: {
          show: true,
          position: 'top'
        },
        colorBy: 'data',
        data: data
      }]
      let xAxis= {
        type: 'category',
        boundaryGap: true,
        data: recentYear
      }
      let newOption = {xAxis,series,...baseOption};
      let c = echarts.init(chart.value)
      c.setOption(newOption)
      closer.value = () => {
        c.resize()
      }
    }else{
      ElMessage.error(res.data.msg)
    }
  }).catch(e=>{
    ElMessage.error("网络异常")
    console.log(e)
  })
  loading.value = false
})
</script>

<style scoped>

</style>