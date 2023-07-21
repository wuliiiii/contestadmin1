<template>
  <div v-for="(item, i) in versions" :key="'version' + i">
    <h3 id="65">
      <b>{{ item.vid }} [{{ parseTime(item.pTime, "{y}-{m}-{d}") }}]</b>
    </h3>
    <ul>
      <li v-for="(one, j) in item.content" :key="'one' + j">
        {{ map[one.type] }}:{{ one.contant }}
      </li>
    </ul>
  </div>
</template>
<script>
import axiosUtil from "../../utils/axiosUtil";
import { ElMessage } from "element-plus";
import { parseTime } from "../../utils/timeUtils";
export default {
  name: "content",
  data() {
    return {
      versions: [],
      map: ["功能", "优化", "BUG"],
    };
  },
  methods: {
    parseTime,
  },
  created() {
    axiosUtil
      .getAllversionContent()
      .then((res) => {
        console.log(res);
        if (res.data.code === 200) {
          this.versions = res.data.result;
        } else {
          // ElMessage.error(res.data.msg)
        }
      })
      .catch((err) => {
        console.log(err);
        ElMessage.error("网络异常");
      });
  },
};
</script>
<style scoped>
li {
  display: list-item;
  line-height: 1.42857143;
}
</style>