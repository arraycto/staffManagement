<template>
  <div style="padding: 0 200px" class="app-container">
    <h1>{{ announcement.title }}</h1>
    <h3 class="shijian">{{ announcement.time }}</h3>
    <div v-html="announcement.content"></div>
  </div>
</template>

<script>
import announcement from "@/api/announcement";
export default {
  data() {
    return {
      announcement: {},
    };
  },
  created() {
    this.init();
  },
  watch: {
    //监听
    $route(to, from) {
      //路由发生变化 方法就会执行
      this.init();
    },
  },

  methods: {
    //初始化
    init() {
      //判断路径中是否有id值
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getAnnouncementById(id);
      } else {
        //清空表单
        this.$router.push({ path: "/" });
      }
    },
    //根据id查询
    getAnnouncementById(id) {
      announcement
        .getAnnouncementById(id)
        .then((response) => {
          this.announcement = response.data.announcement;
        })
        .catch((error) => {});
    },
  },
};
</script>
<style>
.shijian {
  color: darkgray;
  margin-left: 68%;
}
</style>
