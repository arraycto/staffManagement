<template>
  <div class="app-container">
    <el-form
      :model="rewardsPunishments"
      ref="rewardsPunishments"
      :rules="rule"
      label-width="120px"
    >
      <el-form-item label="编号">
        <el-input :span="6" :disabled="true" v-model="rewardsPunishments.id" />
      </el-form-item>
      <el-form-item prop="time" label="奖惩日期">
        <el-date-picker
          v-model="rewardsPunishments.time"
          type="datetime"
          
          placeholder="请选择时间"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="选择员工">
        <el-select
          v-model="rewardsPunishments.departmentId"
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
          v-model="rewardsPunishments.clerkId"
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
      <el-form-item prop="type" label="奖惩类型">
        <el-radio v-model="rewardsPunishments.type" label="0">奖励</el-radio>
        <el-radio v-model="rewardsPunishments.type" label="1">惩罚</el-radio>
      </el-form-item>
      <el-form-item prop="reason" label="奖惩原因">
        <el-input v-model="rewardsPunishments.reason" />
      </el-form-item>
      <el-form-item label="奖惩金额">
        <el-input v-model="rewardsPunishments.amount" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="rewardsPunishments.note" />
      </el-form-item>

      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate('rewardsPunishments')"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import rewardsPunishments from "@/api/rewardsPunishments";
import department from "@/api/department";
import clerk from "@/api/clerk";
export default {
  data() {
    return {
      rewardsPunishments: {
        clerkId: "",
        departmentId: "",
      },
      saveBtnDisabled: false, // 保存按钮是否禁用,
      departmentList: [],
      imagecropperKey: 0, //上传组件的key值
      clerkList: [],
      rule: {
        time: [{ required: true, message: "请选择时间", trigger: "change" }],
        clerkId: [{ required: true, message: "请选择员工", trigger: "change" }],
        reason: [
          { required: true, message: "请填写原因", trigger: "change" },
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
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    initClerk() {
      clerk.getClerkList().then((response) => {
        this.clerkList = response.data.clerkList;
      });
    },
    selectClerk() {
      // this.rewardsPunishments.clerkId = "";
      clerk
        .getClerkByDepartmentId(this.rewardsPunishments.departmentId)
        .then((response) => {
          this.clerkList = response.data.clerkList;
        });
    },
    //初始化
    init() {
      //判断路径中是否有id值
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getRewardsPunishmentsById(id);
        this.initClerk();
      } else {
        //清空表单
        this.rewardsPunishments = {};
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
    updateRewardsPunishments() {
      rewardsPunishments
        .updateRewardsPunishments(this.rewardsPunishments)
        .then((response) => {
          this.$message({
            type: "success",
            message: "修改成功!",
          });
          this.$router.push({
            path: "/rewardsPunishments/listRewardsPunishments",
          });
        });
    },
    //根据id查询
    getRewardsPunishmentsById(id) {
      rewardsPunishments
        .getRewardsPunishmentsById(id)
        .then((response) => {
          this.rewardsPunishments = response.data.rewardsPunishments;
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
            this.updateRewardsPunishments();
          } else {
            //添加
            this.saveRewardsPunishments();
          }
        }
      });
    },
    //添加
    saveRewardsPunishments() {
      rewardsPunishments
        .addRewardsPunishments(this.rewardsPunishments)
        .then((response) => {
          //添加成功
          //提示信息
          this.$message({
            type: "success",
            message: "添加成功",
          });
          //回到列表 路由跳转(重定向)
          this.$router.push({
            path: "/rewardsPunishments/listRewardsPunishments",
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

