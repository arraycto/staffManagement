<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="账号">
        <el-input :span="6" :disabled="true" v-model="userInfo.username" />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input maxlength="10" show-word-limit v-model="userInfo.name" />
      </el-form-item>
      <el-form-item label="个人签名">
        <el-input v-model="userInfo.introduction" />
      </el-form-item>
      <el-form-item label="头像">
        <pan-thumb :image="userInfo.avatar + ''" />
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换照片</el-button
        >
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          url="http://localhost:8001/oss/fileoss/uploadOssFile"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="updateUserP()"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import clerk from "@/api/clerk";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";
export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      userInfo: {},
      saveBtnDisabled: false, // 保存按钮是否禁用,
      imagecropperShow: false, //上传弹窗组件是否显示
      imagecropperKey: 0, //上传组件的key值
    };
  },
  created() {
    this.initClerk();
  },
  methods: {
    initClerk() {
      clerk.getClerkAndUserRole().then((response) => {
        this.userInfo = response.data.user;
      });
    },
    close() {
      //关闭上传弹框的方法
      this.imagecropperShow = false;
      //上传组件初始化
      this.imagecropperKey = this.imagecropperKey + 1;
    },
    //上传成功方法
    cropSuccess(data) {
      this.imagecropperShow = false;
      //上传之后接口返回图片地址
      this.userInfo.avatar = data.url;
      this.imagecropperKey = this.imagecropperKey + 1;
    },
    //修改
    updateUserP() {
      clerk
        .updateUser(this.userInfo)
        .then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.$router.push({ path: "/profile/index" });
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style>
</style>

