<template>
  <div class="app-container">
    <el-form :model="clerk" ref="clerk" :rules="rule" label-width="120px">
      <el-form-item label="员工编号">
        <el-col :span="6">
          <el-input :span="6" :disabled="true" v-model="clerk.id" />
        </el-col>
      </el-form-item>
      <el-form-item prop="name" label="员工名称">
        <el-col :span="4">
          <el-input maxlength="5" show-word-limit v-model="clerk.name" />
        </el-col>
      </el-form-item>
      <el-form-item prop="sex" label="性别">
        <el-col :span="4">
          <el-radio v-model="clerk.sex" prop="sex" label="0">女</el-radio>
          <el-radio v-model="clerk.sex" prop="sex" label="1">男</el-radio>
        </el-col>
      </el-form-item>
      <el-form-item prop="phoneNumber" label="手机号">
        <el-col :span="6">
          <el-input v-model="clerk.phoneNumber" />
        </el-col>
      </el-form-item>
      <el-form-item prop="idCard" label="身份证号">
        <el-col :span="8">
          <el-input v-model="clerk.idCard" />
        </el-col>
      </el-form-item>
      <el-form-item prop="address" label="地址">
        <el-input v-model="clerk.address" />
      </el-form-item>
      <el-form-item prop="position" label="职务">
        <el-col :span="4">
          <el-input v-model="clerk.position" />
        </el-col>
      </el-form-item>
      <el-form-item prop="departmentId" label="部门">
        <el-select v-model="clerk.departmentId" clearable placeholder="请选择">
          <el-option
            v-for="item in departmentList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="员工照片">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="clerk.photo + ''" />
        <!-- 文件上传按钮 -->
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换照片</el-button
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
          @click="saveOrUpdate('clerk')"
          >保存</el-button
        >
        <el-button @click="resetForm('clerk')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import clerk from "@/api/clerk";
import department from "@/api/department";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";

export default {
  components: { ImageCropper, PanThumb },
  data() {
    var checkPhone = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("手机号不能为空"));
      } else {
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/;
        if (reg.test(value)) {
          callback();
        } else {
          return callback(new Error("请输入正确的手机号"));
        }
      }
    };
    return {
      clerk: {},
      saveBtnDisabled: false, // 保存按钮是否禁用,
      departmentList: null,
      saveBtnDisabled: false, // 保存按钮是否禁用,
      imagecropperShow: false, //上传弹窗组件是否显示
      imagecropperKey: 0, //上传组件的key值
      rule: {
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        address: [{ required: true, message: "请填写地址", trigger: "change" }],
        position: [
          { required: true, message: "请填写职务", trigger: "change" },
        ],
        departmentId: [
          { required: true, message: "请选择部门", trigger: "change" },
        ],
        phoneNumber: [
          { validator: checkPhone, required: true, trigger: "blur" },
        ],
        name: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          {
            min: 2,
            max: 10,
            message: "长度在 2 到 10 个字符",
            trigger: "blur",
          },
          {
            required: true,
            pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9.·-]+$/,
            message: "姓名不支持特殊字符",
            trigger: "blur",
          },
        ],
        idCard: [
          { required: true, message: "请输入身份证号", trigger: "blur" },
          {
            min: 15,
            max: 18,
            message: "请如实填写18位号码，以供核对",
            trigger: "blur",
          },
          {
            required: true,
            pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
            message: "请输入正确的身份证号码",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.init();
    this.initDepartment();
  },
  watch: {
    //监听
    $route(to, from) {
      //路由发生变化 方法就会执行
      this.init();
    },
  },
  methods: {
    resetForm(formName) {
        this.$refs[formName].resetFields();
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
      this.clerk.photo = data.url;
      this.imagecropperKey = this.imagecropperKey + 1;
    },
    //初始化
    init() {
      //判断路径中是否有id值
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getClerkById(id);
      } else {
        //清空表单
        this.clerk = {};
      }
    },
    //查询部门
    initDepartment() {
      department.getDepartmentList().then((response) => {
        this.departmentList = response.data.departmentList;
      });
    },
    //修改
    updateClerk() {
      clerk.updateClerk(this.clerk).then((response) => {
        this.$message({
          type: "success",
          message: "修改成功!",
        });
        this.$router.push({ path: "/clerk/listClerk" });
      });
    },
    //根据id查询
    getClerkById(id) {
      clerk
        .getClerkById(id)
        .then((response) => {
          this.clerk = response.data.clerk;
        })
        .catch((error) => {});
    },
    saveOrUpdate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //判断是修改还是添加
          //判断路径中是否有id
          if (this.$route.params && this.$route.params.id) {
            //修改
            this.updateClerk();
          } else {
            //添加
            this.saveClerk();
          }
        }
      });
    },
    //添加
    saveClerk(formName) {
      clerk
        .addClerk(this.clerk)
        .then((response) => {
          //添加成功
          //提示信息
          this.$message({
            type: "success",
            message: "添加成功",
          });
          //回到列表 路由跳转(重定向)
          this.$router.push({ path: "/clerk/listClerk" });
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

