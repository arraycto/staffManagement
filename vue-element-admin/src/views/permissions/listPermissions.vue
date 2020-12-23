<template>
  <div class="app-container">
    <el-table
      :data="list"
      element-loading-text="数据加载中"
      fit
      highlight-current-row
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="照片">
              <el-image
                style="width: 100px; height: 100px"
                :src="props.row.photo"
                fit="contain"
              /> </el-form-item
            ><br />
            <el-form-item label="员工名称">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <br />
            <el-form-item label="员工性别">
              <span>{{ props.row.sex == 1 ? "男" : "女" }}</span> </el-form-item
            ><br />
            <el-form-item label="员工手机号码">
              <span>{{ props.row.phoneNumber }}</span> </el-form-item
            ><br />
            <el-form-item label="员工身份证">
              <span>{{ props.row.idCard }}</span> </el-form-item
            ><br />
            <el-form-item label="员工住址">
              <span>{{ props.row.address }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="id" sortable label="员工编号" />
      <el-table-column prop="name" sortable label="员工姓名"  />
      <el-table-column prop="position" sortable label="员工职位" />
      <el-table-column
        prop="departmentId"
        sortable
        label="员工部门"
        width="100"
      />
    </el-table>
  </div>
</template>

<script>
import role from "@/api/role";
export default {
  data() {
    return {
      list: null, //查询之后接口返回集合
    };
  },
  created() {
    //页面渲染之前执行，一般用于调用methods中定义的方法
    this.getClerkListPage();
  },
  methods: {
    //列表方法
    getClerkListPage() {
      role
        .getAdminList()
        .then((response) => {
          //请求成功
          //response接口返回的数据
          this.list = response.data.clerkList;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

