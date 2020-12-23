<template>
  <div class="app-container">
    <el-row>
      <el-col :span="8" :offset="8">
        <span v-for="(item, index) in roleList" :key="index">
          <el-tag v-if="item.name == '职员'" type="info">职员</el-tag>
          <el-tag v-if="item.name == '人事经理'" type="success">人事经理</el-tag>
        </span>
        <el-card :body-style="{ padding: '0px' }">
          <img :src="clerk.photo" class="image" />
          <div style="padding: 14px">
            <span>姓名:{{ clerk.name }}</span>
            <div class="bottom clearfix">
              <div>编号:{{ clerk.id }}</div>
              <div>性别:{{ (clerk.sex = 0 ? "女" : "男") }}</div>
              <div>手机号:{{ clerk.phoneNumber }}</div>
              <div>身份证号:{{ clerk.idCard }}</div>
              <div>家庭住址:{{ clerk.address }}</div>
              <div>部门:{{ clerk.departmentId }}</div>
              <div>职位:{{ clerk.position }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- 权限 -->
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
      roleList: [],
      clerk: {},
      saveBtnDisabled: false, // 保存按钮是否禁用,
      saveBtnDisabled: false, // 保存按钮是否禁用,
      imagecropperShow: false, //上传弹窗组件是否显示
      imagecropperKey: 0, //上传组件的key值
    };
  },
  created() {
    clerk.getClerkAndUserRole().then((response) => {
      console.log(response);
      this.roleList = response.data.roleList;
      this.clerk = response.data.clerk;
    });
  },
  methods: {
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
      this.clerk.photo = data.url;
      this.imagecropperKey = this.imagecropperKey + 1;
    },
  },
};
</script>

<style>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 30px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>

