<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="announcementQueryVo.title" placeholder="公告名称" />
      </el-form-item>
      <el-button
        type="primary"
        icon="el-icon-search"
        @click="getAnnouncementListPage()"
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
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{
          (page - 1) * limit + scope.$index + 1
        }}</template>
      </el-table-column>

      <el-table-column prop="id" sortable label="公告编号" width="200" />
      <el-table-column prop="title" sortable label="公告标题" width="100" />
      <el-table-column prop="time" sortable label="公告发布时间" />
      <el-table-column prop="gmtCreate" sortable label="创建时间" width="160" />
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <router-link :to="'/announcement/updateAnnouncement/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit"
              >修改</el-button
            >
          </router-link>
          <router-link :to="'/inform/showAnnouncement/' + scope.row.id">
            <el-button type="info" size="mini" icon="el-icon-edit"
              >查看</el-button
            >
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="deleteAnnouncementById(scope.row.id)"
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
      @current-change="getAnnouncementListPage"
    />
  </div>
</template>

<script>
import announcement from "@/api/announcement";
export default {
  data() {
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 6, //每页记录数
      total: 0, //总记录数
      announcementQueryVo: {},
    };
  },
  created() {
    //页面渲染之前执行，一般用于调用methods中定义的方法
    this.getAnnouncementListPage();
  },
  methods: {
    resetData() {
      //清空的方法
      //表单数据清空
      this.announcementQueryVo = {};
      //查询所有
      this.getAnnouncementListPage();
    },
    //页面渲染之后执行，一般用于创建具体方法
    //列表方法
    getAnnouncementListPage(page = 1) {
      this.page = page;
      announcement
        .getAnnouncementListPage(
          this.page,
          this.limit,
          this.announcementQueryVo
        )
        .then((response) => {
          //请求成功
          //response接口返回的数据
          this.list = response.data.announcementList;
          this.total = response.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    resetData() {
      this.getAnnouncementListPage();
    },
    deleteAnnouncementById(id) {
      //删除功能
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        announcement
          .deleteAnnouncementById(id)
          .then((response) => {
            //删除成功
            //提示信息
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            //回到列表
            this.getAnnouncementListPage();
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

