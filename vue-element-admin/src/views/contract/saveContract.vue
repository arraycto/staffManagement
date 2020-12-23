<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="合同编号">
        <el-input :span="6" :disabled="true" v-model="contract.id" />
      </el-form-item>
      <el-form-item label="合同名称">
        <el-input v-model="contract.name" />
      </el-form-item>
      <el-form-item label="签约日期">
        <el-date-picker
          v-model="contract.time"
          type="datetime"
          placeholder="请选择时间"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
       <el-form-item label="到期时间日期">
        <el-date-picker
          v-model="contract.timeout"
          type="datetime"
          placeholder="请选择时间"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="选择员工">
       <el-select v-model="contract.clerkId" clearable placeholder="请选择">
          <el-option
            v-for="item in clerkList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="合同照片">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="contract.url+''" />
        <!-- 文件上传按钮 -->
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换图片</el-button
        >
        <!--v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调-->
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
          @click="saveOrUpdate"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import contract from "@/api/contract";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";
import clerk from "@/api/clerk"
export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      contract: {},
      saveBtnDisabled: false, // 保存按钮是否禁用,
      saveBtnDisabled: false, // 保存按钮是否禁用,
      imagecropperShow: false, //上传弹窗组件是否显示
      imagecropperKey: 0, //上传组件的key值   
      clerkList:null  
    };
  },
  created() {
    this.init();
    this.initClerk();
  },
  watch: {
    //监听
    $route(to, from) {
      //路由发生变化 方法就会执行
      this.init();
    },
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
      console.log(data);
      this.contract.url = data.url;
      this.imagecropperKey = this.imagecropperKey + 1;
    },
    //初始化
    init() {
      //判断路径中是否有id值
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getContractById(id);
      } else {
        //清空表单
        this.contract = {};
      }
    },
    //查询
    initClerk() {
      clerk.getClerkList().then((response) => {
        this.clerkList = response.data.clerkList;
      });
    },
    //修改
    updateContract() {
      contract.updateContract(this.contract).then((response) => {
        this.$message({
          type: "success",
          message: "修改成功!",
        });
        this.$router.push({ path: "/contract/listContract" });
      });
    },
    //根据id查询
    getContractById(id) {
      contract
        .getContractById(id)
        .then((response) => {
          this.contract = response.data.contract;
        })
        .catch((error) => {});
    },
    saveOrUpdate() {
      //判断是修改还是添加
      //判断路径中是否有id
      if (this.$route.params && this.$route.params.id) {
        //修改
        this.updateContract();
      } else {
        //添加
        this.saveContract();
      }
    },
    //添加
    saveContract() {

        contract
          .addContract(this.contract)
          .then((response) => {
            //添加成功
            //提示信息
            this.$message({
              type: "success",
              message: "添加成功",
            });
            //回到列表 路由跳转(重定向)
            this.$router.push({ path: "/contract/listContract" });
          })
          .catch((error) => {
            //提示信息
            // this.$message({
            //   type: "warning",
            //   message: "添加失败",
            // });
          });
      
    },
  },
};
</script>

