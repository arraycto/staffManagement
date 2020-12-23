<template>
  <div class="block">
    <el-timeline style="margin:50px 0;">
      <el-timeline-item
        v-for="(item, index) of throughList"
        :key="index"
        :timestamp="item.gmtCreate"
        placement="top"
      >
        <el-card>
          <h4>{{ item.departmentId }}</h4>
          <p>{{ item.position }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import clerkThrough from "@/api/clerkThrough";
export default {
  data() {
    return {
      throughList: [],
    };
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id;
      clerkThrough.getClerkThroughByClerkId(id).then((response) => {
        this.throughList = response.data.throughList;
      });
    } else {
      //清空表单
      this.clerk = {};
    }
  },
};
</script>
