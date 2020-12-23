<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="请输入原密码">
        <el-input v-model="password" maxlength="10" show-word-limit />
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="validation()"
        >下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import clerk from '@/api/clerk'
export default {
  data() {
    return {
      password: '',
      saveBtnDisabled: false
    }
  },
  created() {},
  methods: {
    validation() {
      if (this.password.length >= 6) {
        clerk
          .getPassword(this.password)
          .then((response) => {
            this.$message({
              type: 'success',
              message: '密码正确'
            })
            this.$router.push({ path: '/savePassword/savePassword/' + '1' })
          })
          .catch((error) => {

          })
      } else {
        this.$message({
          type: 'warning',
          message: '长度必须大于六位'
        })
      }
    }
  }
}
</script>

<style>
</style>

