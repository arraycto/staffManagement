<template>
  <div class="app-container">
    <div v-for="(item, index) in contractList" :key="index">
      <el-row>
        <el-col
          :span="8"
          :offset="8"
        >
          <el-card :body-style="{ padding: '0px' }">
            <img :src="item.url" class="image" />
            <div style="padding: 14px">
              <span>{{ item.name }}</span>
              <div class="bottom clearfix">
                <time class="time">{{ item.time }}</time><br>
                <time class="time">{{ item.timeout }}</time>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import contract from "@/api/contract";
export default {
  data() {
    return {
      contractList: null,
    };
  },
  created() {
    contract.getClerkThrough().then((response) => {
      this.contractList = response.data.contractList;
    });
  },
};
</script>
<style>
  .time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 30px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
</style>

