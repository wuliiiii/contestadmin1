<template>
  <el-row type="flex" justify="space-around">

    <el-col :span="10">
    <div>
      <el-row class="bot">
        <LastOneMonthContestTable></LastOneMonthContestTable>
      </el-row>
      <el-row class="bot">
        <FutureContest></FutureContest>
      </el-row>
    </div>
    </el-col>

    <el-col :span="10">
      <el-row class="bot">
        <CodeforcesTopList></CodeforcesTopList>
      </el-row>
      <el-row class="bot">
        <AtcoderTopList></AtcoderTopList>
      </el-row>
    </el-col>

  </el-row>
</template>

<script setup>

import WeekSolveGraph from "../components/infoComponents/Graphs/WeekSolveGraph.vue";
import FutureContest from "../components/infoComponents/Table/FutureContest.vue";
import CodeforcesTopList from "../components/infoComponents/Table/CodeforcesTopList.vue";
import AtcoderTopList from "../components/infoComponents/Table/AtcoderTopList.vue";
import LastOneMonthContestTable from "../components/infoComponents/Table/LastOneMonthContestTable.vue";

</script>

<style scoped lang="scss">
  .bot {
    margin: 0 0 2vh 0;
  }
</style>
