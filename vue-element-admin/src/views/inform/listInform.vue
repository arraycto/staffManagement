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
        @click="getAnnouncementListPageClerk()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

      <el-table :data="list" stripe style="width:100%" >
        <el-table-column prop="title" align="center" label="通知公告">
          <template slot-scope="scope">
            <router-link :to="'/inform/showAnnouncement/' + scope.row.id">{{
              scope.row.title
            }}</router-link>
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
      @current-change="getAnnouncementListPageClerk"
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
    this.getAnnouncementListPageClerk();
  },
  methods: {
    resetData() {
      //清空的方法
      //表单数据清空
      this.announcementQueryVo = {};
      //查询所有
      this.getAnnouncementListPageClerk();
    },
    //页面渲染之后执行，一般用于创建具体方法
    //列表方法
    getAnnouncementListPageClerk(page = 1) {
      this.page = page;
      announcement
        .getAnnouncementListPageClerk(
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
      this.getAnnouncementListPageClerk();
    },
  },
};
</script>

