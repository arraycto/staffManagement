<template>
  <div class="app-container">
    <el-form
      :model="department"
      ref="department"
      :rules="rule"
      label-width="120px"
    >
      <el-form-item label="部门id">
        <el-input :span="6" :disabled="true" v-model="department.id" />
      </el-form-item>
      <el-form-item prop="name" label="部门名称">
        <el-input maxlength="10" show-word-limit v-model="department.name" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="department.note" />
      </el-form-item>

      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate('department')"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import department from "@/api/department";

export default {
  data() {
    return {
      department: {},
      saveBtnDisabled: false, // 保存按钮是否禁用,
      BASE_API: process.env.BASE_API, //获取dev.env.js中的BASE_API
      rule: {
        name: [
          { required: true, message: "请填写部门名称", trigger: "change" },
        ],
      },
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
        this.getDepartmentById(id);
      } else {
        //清空表单
        this.department = {};
      }
    },
    //修改
    updateDepartment() {
      department.updateDepartment(this.department).then((response) => {
        this.$message({
          type: "success",
          message: "修改成功!",
        });
        this.$router.push({ path: "/department/listDepartment" });
      });
    },
    //根据id查询
    getDepartmentById(id) {
      department
        .getDepartmentById(id)
        .then((response) => {
          this.department = response.data.department;
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
            this.updateDepartment();
          } else {
            //添加
            this.saveDepartment();
          }
        }
      });
    },
    //添加
    saveDepartment() {
      department
        .addDepartment(this.department)
        .then((response) => {
          //添加成功
          //提示信息
          this.$message({
            type: "success",
            message: "添加成功",
          });
          //回到列表 路由跳转(重定向)
          this.$router.push({ path: "/department/listDepartment" });
        })
        .catch((error) => {
          //提示信息
          this.$message({
            type: "warning",
            message: "添加失败",
          });
        });
    },
  },
};
</script>

