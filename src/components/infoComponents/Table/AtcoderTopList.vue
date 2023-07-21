<template>
  <el-card style="width: 100%">
    <template #header>
      <span>atcoder</span>
    </template>
    <el-table :data="data" v-loading="loading" :row-class-name="tableRowClassName">
      <el-table-column label="用户名" prop="atcoderId" width='330pt'>
        <template v-slot="scope" >
          <!-- v-if="scope.$index<=2" -->
          <router-link  :to="'/user/trainInfo/' + scope.row.username">{{
            scope.row.atcoderId
          }}({{scope.row.classname}}-{{scope.row.realname}})</router-link>
        </template>
      </el-table-column>
      <el-table-column label="分数" prop="rating"> </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { onMounted, ref } from "vue";
import AxiosUtil from "../../../utils/axiosUtil";
import { ElMessage } from "element-plus";
let data = ref([]);
let loading = ref(true);
onMounted(() => {
  AxiosUtil.atcoderAPI
    .getTopTenAtcoder()
    .then((res) => {
      if (res.data.code === 200) {
        data.value = res.data.result;
        loading.value = false;
      } else {
        ElMessage.error(res.data.msg);
      }
    })
    .catch((e) => {
      ElMessage.error(e);
    });
});
</script>

<style scoped>
</style>