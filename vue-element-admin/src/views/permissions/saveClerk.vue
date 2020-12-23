<template>
  <div class="app-container">
    <el-form label-width="140px">
      <el-form-item label="员工编号">
        <el-input :span="6" :disabled="true" v-model="clerk.id" />
      </el-form-item>
      <el-form-item label="员工名称">
        <el-input
          :disabled="true"
          maxlength="10"
          show-word-limit
          v-model="clerk.name"
        />
      </el-form-item>
      <el-form-item label="备注">
        <el-radio :disabled="true" v-model="clerk.sex" label="0">女</el-radio>
        <el-radio :disabled="true" v-model="clerk.sex" label="1">男</el-radio>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input :disabled="true" v-model="clerk.phoneNumber" />
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input :disabled="true" v-model="clerk.idCard" />
      </el-form-item>
      <el-form-item label="地址">
        <el-input :disabled="true" v-model="clerk.address" />
      </el-form-item>
      <el-form-item label="职务">
        <el-input :disabled="true" v-model="clerk.position" />
      </el-form-item>
      <el-form-item label="部门">
        <el-select
          :disabled="true"
          v-model="clerk.departmentId"
          clearable
          placeholder="请选择"
        >
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
      </el-form-item>
      <el-form-item label="是否激活管理员权限">
        <el-radio-group v-model="managementInfo">
          <el-radio-button label="是"></el-radio-button>
          <el-radio-button label="否"></el-radio-button>
        </el-radio-group>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="save"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import clerk from "@/api/clerk";
import role from "@/api/role";
import department from "@/api/department";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      managementInfo: "",
      clerk: {},
      saveBtnDisabled: false, // 保存按钮是否禁用,
      departmentList: null,
      saveBtnDisabled: false, // 保存按钮是否禁用,
      imagecropperShow: false, //上传弹窗组件是否显示
      imagecropperKey: 0, //上传组件的key值
      clerkId: "",
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
    //初始化
    init() {
      //判断路径中是否有id值
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.clerkId = this.$route.params.id;
        this.getClerkById(id);
        this.initRole();
      } else {
        //清空表单
        this.clerk = {};
      }
    },
    initRole() {
      role.getRoleByClerkId(this.clerkId).then((response) => {
        if (response.data.role) {
          this.managementInfo = "是";
        } else {
          this.managementInfo = "否";
        }
      });
    },
    //查询部门
    initDepartment() {
      department.getDepartmentList().then((response) => {
        this.departmentList = response.data.departmentList;
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
    //添加
    save() {
      role
        .uodateRoleByClerkId(this.clerkId, this.managementInfo)
        .then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.$router.push({ path: "/permissions/listPermissions" });
        });
    },
  },
};
</script>

