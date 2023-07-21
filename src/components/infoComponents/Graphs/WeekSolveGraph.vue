<template>
  <el-card v-loading="loading" style="width: 100%">
      <div ref="chart" style="width: 100%;height: 30vh">
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
  title: {
    text: '近七天提交数'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  legend: {
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

  AxiosUtil.getRecentSubmit().then((res)=>{
    if(res.data.code === 200) {
      let series = [{
        name: '赛时提交未通过数',
        type: 'bar',
        stack: '总量',
        emphasis: {
          focus: 'series'
        },
        data: []
      },{
        name: '补题未通过数',
        type: 'bar',
        stack: '总量',
        emphasis: {
          focus: 'series'
        },
        data: []
      },
        {
          name: '赛时AC数',
          type: 'bar',
          stack: '总量',
          emphasis: {
            focus: 'series'
          },
          data: []
        },
        {
          name: "补题AC数",
          type: 'bar',
          stack: '总量',
          emphasis: {
            focus: 'series'
          },
          data: []
        }
      ]
      let recentDay = []
      for(let i = 0;i<7;i++) {
        let d = new Date(new Date().getTime() - i * 24 * 60 * 60 * 1000);
        recentDay.push(d.getMonth() + 1+ "." +d.getDate() )
        series[0].data.push(res.data.result[i].total - res.data.result[i].after - res.data.result[i].solve + res.data.result[i].aftersolve)
        series[1].data.push(res.data.result[i].after - res.data.result[i].aftersolve)
        series[2].data.push(res.data.result[i].solve - res.data.result[i].aftersolve)
        series[3].data.push(res.data.result[i].aftersolve)
      }
      let xAxis= {
        type: 'category',
        boundaryGap: true,
        data: recentDay
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