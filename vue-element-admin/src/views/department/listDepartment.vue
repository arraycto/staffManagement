<template>
  <div class="app-container">
    <el-table
      :data="list"
      element-loading-text="数据加载中"
      fit
      highlight-current-row
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{
          (page - 1) * limit + scope.$index + 1
        }}</template>
      </el-table-column>
      <el-table-column prop="id" sortable label="部门编号" width="200" />
      <el-table-column prop="name" sortable label="部门名称" width="100" />
      <el-table-column prop="note" label="备注" />
      <el-table-column prop="gmtCreate" sortable label="创建时间" width="160" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/department/updateDepartment/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit"
              >修改</el-button
            >
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="deleteDepartmentById(scope.row.id)"
            >删除</el-button
          >
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
      @current-change="getDepartmentListPage"
    />
  </div>
</template>

<script>
import department from "@/api/department";
export default {
  data() {
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 6, //每页记录数
      total: 0, //总记录数
      search:''
    };
  },
  created() {
    //页面渲染之前执行，一般用于调用methods中定义的方法
    this.getDepartmentListPage();
  },
  methods: {
    //页面渲染之后执行，一般用于创建具体方法
    //列表方法
    getDepartmentListPage(page = 1) {
      this.page = page;
      department
        .getDepartmentListPage(this.page, this.limit)
        .then((response) => {
          //请求成功
          //response接口返回的数据
          this.list = response.data.departmentList;
          this.total = response.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    resetData() {
      this.getDepartmentListPage();
    },
    deleteDepartmentById(id) {
      //删除功能
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        department
          .deleteDepartmentById(id)
          .then((response) => {
            //删除成功
            //提示信息
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            //回到列表
            this.getDepartmentListPage();
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

