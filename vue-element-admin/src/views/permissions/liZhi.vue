<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="clerkQueryVo.name" placeholder="员工名称" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="clerkQueryVo.position" placeholder="员工职位" />
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="clerkQueryVo.departmentName"
          placeholder="部门名称"
        />
      </el-form-item>

      <el-button
        type="primary"
        icon="el-icon-search"
        @click="getClerkListPage()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
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
              <span>{{
                (props.row.sex == 1 ? "男" : "女")
              }}</span> </el-form-item
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

      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{
          (page - 1) * limit + scope.$index + 1
        }}</template>
      </el-table-column>

      <el-table-column prop="id" sortable label="员工编号" width="200" />
      <el-table-column prop="name" sortable label="员工姓名" width="100" />
      <el-table-column prop="position" sortable label="员工职位" />
      <el-table-column
        prop="departmentId"
        sortable
        label="员工部门"
        width="100"
      />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/clerkThrough/showClerkThrough/' + scope.row.id">
            <el-button type="info" size="mini"
              >员工经历</el-button
            >
          </router-link>
          
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getClerkListPage"
    />
  </div>
</template>

<script>
import clerk from "@/api/clerk";
export default {
  data() {
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 6, //每页记录数
      total: 0, //总记录数
      clerkQueryVo: {},
    };
  },
  created() {
    //页面渲染之前执行，一般用于调用methods中定义的方法
    this.getClerkListPage();
  },
  methods: {
    resetData() {
      //清空的方法
      //表单数据清空
      this.clerkQueryVo = {};
      //查询所有
      this.getClerkListPage();
    },
    //页面渲染之后执行，一般用于创建具体方法
    //列表方法
    getClerkListPage(page = 1) {
      this.page = page;
      clerk
        .getClerkListPageLizhi(this.page, this.limit, this.clerkQueryVo)
        .then((response) => {
          //请求成功
          //response接口返回的数据
          this.list = response.data.clerkList;
          this.total = response.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    resetData() {
      this.getClerkListPage();
    },
    deleteClerkById(id) {
      //删除功能
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        clerk
          .deleteClerkById(id)
          .then((response) => {
            //删除成功
            //提示信息
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            //回到列表
            this.getClerkListPage();
          })
          .catch((error) => {
            //提示信息
            this.$message({
              type: "warning",
              message: "删除失败!",
            });
          });
      });
    },
  },
};
</script>

