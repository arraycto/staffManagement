<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="编号">
        <el-input :span="6" :disabled="true" v-model="attendance.id" />
      </el-form-item>
      <el-form-item label="选择员工">
        <el-select
          v-model="attendance.departmentId"
          clearable
          placeholder="请选择部门"
          @change="selectClerk"
        >
          <el-option
            v-for="item in departmentList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
        <el-select
          v-model="attendance.clerkId"
          clearable
          placeholder="请选择员工"
        >
          <el-option
            v-for="item in clerkList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="请选择时间">
        <el-date-picker
          v-model="attendance.time"
          type="date"
          placeholder="请选择时间"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      
      <el-form-item label="考勤类型">
        <el-radio v-model="attendance.status" label="1">事假</el-radio>
        <el-radio v-model="attendance.status" label="2">迟到</el-radio>
        <el-radio v-model="attendance.status" label="3">早退</el-radio>
        <el-radio v-model="attendance.status" label="4">病假</el-radio>
        <el-radio v-model="attendance.status" label="5">旷工</el-radio>
        <el-radio v-model="attendance.status" label="6">休补</el-radio>
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
import attendance from "@/api/attendance";
import department from "@/api/department";
import clerk from "@/api/clerk";
export default {
  data() {
    return {
      attendance: {
        clerkId:'',
        departmentId:''
      },
      saveBtnDisabled: false, // 保存按钮是否禁用,
      departmentList: [],
      imagecropperKey: 0, //上传组件的key值
      clerkList: [],
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
    initClerk() {
      clerk.getClerkList().then((response) => {
        this.clerkList = response.data.clerkList;
      });
    },
    selectClerk() {
      this.attendance.clerkId = '';
      clerk
        .getClerkByDepartmentId(this.attendance.departmentId)
        .then((response) => {
          this.clerkList = response.data.clerkList;
        });
    },
    //初始化
    init() {
      //判断路径中是否有id值
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getAttendanceById(id);
        this.initClerk();
      } else {
        //清空表单
        this.attendance = {};
      }
      this.initDepartment();
    },
    //查询部门
    initDepartment() {
      department.getDepartmentList().then((response) => {
        this.departmentList = response.data.departmentList;
      });
      
    },
    //修改
    updateAttendance() {
      attendance
        .updateAttendance(this.attendance)
        .then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.$router.push({
            path: "/attendance",
          });
        });
    },
    //根据id查询
    getAttendanceById(id) {
      attendance
        .getAttendanceById(id)
        .then((response) => {
          this.attendance = response.data.attendance;
          console.log(this.attendance);
        })
        .catch((error) => {});
    },
    saveOrUpdate() {
      //判断是修改还是添加
      //判断路径中是否有id
      if (this.$route.params && this.$route.params.id) {
        //修改
        this.updateAttendance();
      } else {
        //添加
        this.saveAttendance();
      }
    },
    //添加
    saveAttendance() {
      attendance
        .addAttendance(this.attendance)
        .then((response) => {
          //添加成功
          //提示信息
          this.$message({
            type: "success",
            message: "添加成功",
          });
          //回到列表 路由跳转(重定向)
          this.$router.push({
            path: "/attendance",
          });
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

