<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="contractQueryVo.name" placeholder="合同名称" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="contractQueryVo.clerkName" placeholder="员工姓名" />
      </el-form-item>

      <el-form-item>
        <el-date-picker
          v-model="contractQueryVo.time"
          type="datetime"
          placeholder="在此日期之后签署的合同"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
     <el-form-item>
        <el-date-picker
          v-model="contractQueryVo.timeout"
          type="datetime"
          placeholder="在此日期之前结束的合同"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-button
        type="primary"
        icon="el-icon-search"
        @click="getContractListPage()"
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
            <el-form-item label="合同照片">
              <el-image
                style="width: 100px; height: 100px"
                :src="props.row.url"
                fit="contain"
              /> </el-form-item
            >
          </el-form>
        </template>
      </el-table-column>

      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{
          (page - 1) * limit + scope.$index + 1
        }}</template>
      </el-table-column>
      <el-table-column prop="id" sortable label="合同编号" width="200" />
      <el-table-column prop="name" sortable label="合同名称" width="100" />
      <el-table-column prop="time" sortable label="签约日期" />
      <el-table-column prop="timeout" sortable label="截止日期" />
      <el-table-column prop="clerkId" sortable label="员工名称" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/contract/updateContract/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit"
              >修改</el-button
            >
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="deleteContractById(scope.row.id)"
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
      @current-change="getContractListPage"
    />
  </div>
</template>

<script>
import contract from "@/api/contract";
export default {
  data() {
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 6, //每页记录数
      total: 0, //总记录数
      contractQueryVo: {},
    };
  },
  created() {
    //页面渲染之前执行，一般用于调用methods中定义的方法
    this.getContractListPage();
  },
  methods: {
    resetData() {
      //清空的方法
      //表单数据清空
      this.contractQueryVo = {};
      //查询所有
      this.getContractListPage();
    },
    //页面渲染之后执行，一般用于创建具体方法
    //列表方法
    getContractListPage(page = 1) {
      this.page = page;
      contract
        .getContractListPage(this.page, this.limit, this.contractQueryVo)
        .then((response) => {
          //请求成功
          //response接口返回的数据
          this.list = response.data.contractList;
          this.total = response.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    resetData() {
      this.getContractListPage();
    },
    deleteContractById(id) {
      //删除功能
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        contract
          .deleteContractById(id)
          .then((response) => {
            //删除成功
            //提示信息
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            //回到列表
            this.getContractListPage();
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

