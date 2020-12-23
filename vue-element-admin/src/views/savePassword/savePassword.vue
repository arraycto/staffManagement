<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="请输入密码">
        <el-input type="password" v-model="newPasswordone" />
      </el-form-item>
      <el-form-item label="请再次输入密码">
        <el-input type="password" v-model="newPasswordtwo" />
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="updateUserPassword()"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import clerk from "@/api/clerk";
export default {
  data() {
    return {
      newPasswordone: "",
      newPasswordtwo: "",
      saveBtnDisabled: false,
    };
  },
  created() {
     if (this.$route.params && this.$route.params.id) {
      } else {
        //清空表单
        this.$router.push({ path: "savePassword/validationPassword" });
      }
  },
  methods: {
    updateUserPassword() {
      if (this.newPasswordone.length >= 6) {
        if (this.newPasswordone === this.newPasswordtwo) {
          clerk
            .putPassword(this.newPasswordone)
            .then((response) => {
              this.$message({
                type: "success",
                message: "修改成功!",
              });
              this.$router.push({ path: "/" });
            })
            .catch((error) => {});
        } else {
          this.$message({
            type: "warning",
            message: "两次输入密码必须相同",
          });
        }
      } else {
        this.$message({
          type: "warning",
          message: "长度必须大于六位",
        });
      }
    },
  },
};
</script>

<style>
</style>

