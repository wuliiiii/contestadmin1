<template>
  <div class="container">
    <el-tabs
             tab-position="left"
             stretch
             style="min-height: 80vh"
    >
<!--      <el-tab-pane label="个人数据" lazy>-->

<!--      </el-tab-pane>-->
      <el-tab-pane label="个人信息" lazy>
        <InfoEdit></InfoEdit>
      </el-tab-pane>
      <el-tab-pane label="我的工单" lazy>
        <application></application>
      </el-tab-pane>
      <el-tab-pane label="修改密码" lazy>
        <ResetPassword></ResetPassword>
      </el-tab-pane>
      <el-tab-pane label="codeforces账户" lazy>
        <CodeforcesAccount></CodeforcesAccount>
      </el-tab-pane>
      <el-tab-pane label="atcoder账户" lazy>
        <AtcoderAccount></AtcoderAccount>
      </el-tab-pane>

    </el-tabs>
  </div>


</template>

<script setup>
import ResetPassword from "../../components/resetPassword.vue";
import CodeforcesAccount from "../../components/CodeforcesAccount.vue";
import Application from "../../components/Application.vue";
import InfoEdit from "../../components/InfoEdit.vue";
import AtcoderAccount from "../../components/AtcoderAccount.vue";
</script>

<style lang="scss">
.container{

}
.el-tabs--left .el-tabs__header.is-left{
  min-height: 80vh;
}
</style>