<template>
    <el-container>
      <el-header>
        <el-menu
            :default-active="activeIndex"
            mode="horizontal"
            :ellipsis="false"
            @select="select"
            v-if="forceFlush === true"
        >
          <div class="flex-grow"/>
          <el-menu-item index="/index">首页</el-menu-item>
          <el-menu-item index="/stuList">用户列表</el-menu-item>
          <!-- <el-menu-item index="/monthlyRating">月度积分</el-menu-item> -->
          <el-sub-menu index="codeforces">
            <template #title>
              codeforces
            </template>
            <el-menu-item index="/codeforces/list">比赛列表</el-menu-item>
            <el-menu-item index="/codeforces/rank">账户排名</el-menu-item>
            <el-menu-item index="/codeforces/problemList">题目列表</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="atcoder">
            <template #title>
              atcoder
            </template>
            <el-menu-item index="/atcoder/list">比赛列表</el-menu-item>
            <el-menu-item index="/atcoder/rank">账户排名</el-menu-item>
          </el-sub-menu>
          
          <!-- <el-sub-menu index="graph">
            <template #title>
              图表
            </template>
            <el-menu-item index="/graph/tags">标签</el-menu-item>
            <el-menu-item index="/graph/RatingChange/Codeforces">Codeforces积分变化</el-menu-item>
            <el-menu-item index="/graph/RatingChange/Atcoder">Atcoder积分变化</el-menu-item>
            <el-menu-item index="/graph/GradeCompare">年级对比</el-menu-item>
            <el-menu-item index="/graph/YearCompare">Codeforces积分人数对比</el-menu-item>
            <el-menu-item index="/graph/SolveCompare">Codeforces解题对比</el-menu-item>
            <el-menu-item index="/graph/UserCount">用户年份对比</el-menu-item>
            <el-menu-item index="/graph/RecentContestParticipate">最近比赛参与人数比较</el-menu-item>
          </el-sub-menu> -->

          <!-- <el-sub-menu index="intelligent_train">
            <template #title>
              智能训练
            </template>
            <el-menu-item index="/intelligent_training/list">训练列表</el-menu-item>
            <el-menu-item index="/intelligent_training/generateSuggest">智能推荐</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/versionInfo">更新信息</el-menu-item> -->

          <el-sub-menu v-if="hasLogin" index="login">
            <template #title>
              登录状态
            </template>
            <el-menu-item index="/user/myTrainInfo">我的训练情况</el-menu-item>
            <!-- <el-menu-item index="/user/submitApplication">提交工单</el-menu-item> -->
            <el-menu-item index="/user/profile">个人信息</el-menu-item>
            <el-menu-item index="/user/sighOut" @click="signOut">退出登录</el-menu-item>
          </el-sub-menu>
          <el-menu-item class="unselectable" v-else>
            <router-link to="/login" class="headLink">登录</router-link>
            或
            <router-link to="/register" class="headLink">注册</router-link>
          </el-menu-item>
        </el-menu>
      </el-header>
    
      <el-main class="main">
        <router-view>
        </router-view>
      </el-main>
    </el-container>
</template>

<script setup>
import {useRouter, useRoute} from "vue-router";
import {onMounted, ref, watch, nextTick} from "vue";
import {ElMessage} from "element-plus";
import InfoUtils from "../utils/InfoUtils";

let router = useRouter()
let route = useRoute()
let activeIndex = ref("")
let hasLogin = ref(false)
let forceFlush = ref(true)
onMounted(() => {
  hasLogin.value = InfoUtils.isLogin()
  activeIndex.value = route.path
  console.log(activeIndex.value)
})
watch(()=>route.path,(newVal,_)=>{
  forceFlush.value = false
  activeIndex.value = newVal
  nextTick(()=>{
    forceFlush.value = true
  })
})
const signOut = () => {
  window.localStorage.removeItem("normal_token")
  window.localStorage.removeItem("normal_expire")
  window.localStorage.removeItem("normal_username")
  hasLogin.value = false
  if (route.path.startsWith("/user")) {
    router.push("/")
  }
  ElMessage.info("退出登录成功")
}
const select = (index,_,__)=>{
  if (index === "/user/sighOut") {
    forceFlush.value = false
    nextTick(()=>{
      forceFlush.value = true
    })
    return
  }
  router.push(index)
}
</script>

<style scoped lang="scss">
.flex-grow {
  flex-grow: 1;
}

.unselectable {
  background: 0 0 !important;
  color: #303133 !important;
  cursor: default !important;

  .headLink {
    margin: 0 10%;

    :hover {
      color: #000000 !important;
    }
  }
}

.main{
  width: 100%;
}

</style>