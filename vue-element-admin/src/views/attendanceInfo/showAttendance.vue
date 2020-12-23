<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
       
      <el-form-item>
        <el-select
          clearable
          v-model="attendanceQueryVo.status"
          placeholder="请选择奖惩类型"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-button
        type="primary"
        icon="el-icon-search"
        @click="getAttendance()"
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
      <el-table-column prop="id" sortable label="编号" width="200" />
      <el-table-column
        prop="status"
        sortable
        label="类型"
        width="100"
      />
      <el-table-column prop="time" sortable label="日期" width="200" />
      <el-table-column prop="audit" label="状态" />
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getAttendance"
    />
  </div>
</template>

<script>
import attendance from "@/api/attendance";
export default {
  data() {
    return {
      options: [
        {
          value: "1",
          label: "事假",
        },
        {
          value: "2",
          label: "迟到",
        },
           {
          value: "3",
          label: "早退",
        },
        {
          value: "4",
          label: "病假",
        },
           {
          value: "5",
          label: "旷工",
        },
        {
          value: "6",
          label: "休补",
        },
      ],
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 6, //每页记录数
      total: 0, //总记录数
      attendanceQueryVo: {},
    };
  },
  created() {
    //页面渲染之前执行，一般用于调用methods中定义的方法
    this.getAttendance();
  },
  methods: {
    resetData() {
      //清空的方法
      //表单数据清空
      this.attendanceQueryVo = {};
      //查询所有
      this.getAttendance();
    },
    //页面渲染之后执行，一般用于创建具体方法
    //列表方法
    getAttendance(page = 1) {
      this.page = page;
      attendance
        .getAttendance(
          this.page,
          this.limit,
          this.attendanceQueryVo
        )
        .then((response) => {
          //请求成功
          //response接口返回的数据
          this.list = response.data.attendanceList;
          this.total = response.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    resetData() {
      this.getAttendance();
    },
    
  },
};
</script>

