<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="公告标题">
        <el-input :span="6" v-model="announcement.title" />
      </el-form-item>
      <el-form-item label="发布时间">
        <el-date-picker
          v-model="announcement.time"
          type="datetime"
          placeholder="选择日期时间"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="公告具体内容">
        <tinymce-editor
          id="editor"
          ref="editor"
          v-model="announcement.content"
          :height="realHeight"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import TinymceEditor from "@/components/tinymce";

import announcement from "@/api/announcement";
import oss from "@/api/oss";
export default {
  components: {
    //注册TinymceEditor组件
    TinymceEditor,
  },
  data() {
    return {
      announcement: {},
      saveBtnDisabled: false, // 保存按钮是否禁用,
    };
  },
  computed: {
    realHeight() {
      return (
        (window.innerHeight ||
          document.documentElement.clientHeight ||
          document.body.clientHeight) - 200
      );
    },
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
        this.announcement = {};
      }
    },
    //修改
    updateAnnouncement() {
      announcement.updateAnnouncement(this.announcement).then((response) => {
        this.$message({
          type: "success",
          message: "修改成功!",
        });
        this.$router.push({ path: "/announcement/listAnnouncement" });
      });
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
    saveOrUpdate() {
      //判断是修改还是添加
      //判断路径中是否有id
      if (this.$route.params && this.$route.params.id) {
        //修改
        this.updateAnnouncement();
      } else {
        //添加
        this.saveAnnouncement();
      }
    },
    //添加
    saveAnnouncement() {
      if (this.announcement.title == null) {
        this.$message({
          type: "warning",
          message: "请添加标题",
        });
      } else {
        announcement
          .addAnnouncement(this.announcement)
          .then((response) => {
            //添加成功
            //提示信息
            this.$message({
              type: "success",
              message: "添加成功",
            });
            //回到列表 路由跳转(重定向)
            this.$router.push({ path: "/announcement/listAnnouncement" });
          })
          .catch((error) => {
            //提示信息
            // this.$message({
            //   type: "warning",
            //   message: "添加失败",
            // });
          });
      }
    },
  },
};
</script>

