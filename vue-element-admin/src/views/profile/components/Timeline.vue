<template>
  <div class="block">
    <el-timeline>
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
      throughList: [
      ],
    };
  },
  created() {
    clerkThrough.getClerkThrough().then((response) => {
      this.throughList = response.data.throughList;
    });
  },
};
</script>
