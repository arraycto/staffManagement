<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input
          v-model="rewardsPunishmentsQueryVo.reason"
          placeholder="奖惩原因"
        />
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="rewardsPunishmentsQueryVo.amount"
          placeholder="奖惩金额"
        />
      </el-form-item>
      <el-form-item>
        <el-select
          clearable
          v-model="rewardsPunishmentsQueryVo.type"
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
        @click="getRewardsPunishments()"
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
        prop="type"
        sortable
        label="类型"
        width="100"
      />
      <el-table-column prop="time" sortable label="日期" width="200" />
      <el-table-column prop="reason" label="原因" />
      <el-table-column prop="amount" sortable label="金额" /> 
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getRewardsPunishments"
    />
  </div>
</template>

<script>
import rewardsPunishments from "@/api/rewardsPunishments";
export default {
  data() {
    return {
      options: [
        {
          value: "0",
          label: "奖励",
        },
        {
          value: "1",
          label: "惩罚",
        },
      ],
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 6, //每页记录数
      total: 0, //总记录数
      rewardsPunishmentsQueryVo: {},
    };
  },
  created() {
    //页面渲染之前执行，一般用于调用methods中定义的方法
    this.getRewardsPunishments();
  },
  methods: {
    resetData() {
      //清空的方法
      //表单数据清空
      this.rewardsPunishmentsQueryVo = {};
      //查询所有
      this.getRewardsPunishments();
    },
    //页面渲染之后执行，一般用于创建具体方法
    //列表方法
    getRewardsPunishments(page = 1) {
      this.page = page;
      rewardsPunishments
        .getRewardsPunishments(
          this.page,
          this.limit,
          this.rewardsPunishmentsQueryVo
        )
        .then((response) => {
          //请求成功
          //response接口返回的数据
          this.list = response.data.rewardsPunishmentsList;
          this.total = response.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    resetData() {
      this.getRewardsPunishments();
    },
    
  },
};
</script>

