import request from '@/utils/request'


export default {
  //1.（查询分页）
  getRewardsPunishmentsListPage(current, limit, rewardspunishmentsQueryVo) {
    return request({
      url: `/staff/rewards_punishments/listPage/${current}/${limit}`,
      method: "post",
      data: rewardspunishmentsQueryVo
    })
  },
  getRewardsPunishments(current, limit, rewardspunishmentsQueryVo) {
    return request({
      url: `/staff/rewards_punishments/getRewardsPunishments/${current}/${limit}`,
      method: "post",
      data: rewardspunishmentsQueryVo
    })
  },
  //2.删除
  deleteRewardsPunishmentsById(id) {
    return request({
      url: `/staff/rewards_punishments/${id}`,
      method: "delete"
    })
  },
  //3.添加
  addRewardsPunishments(rewardspunishments) {
    return request({
      url: `/staff/rewards_punishments`,
      method: "post",
      data: rewardspunishments
    })
  },
  //4.根据id查询
  getRewardsPunishmentsById(id) {
    return request({
      url: `/staff/rewards_punishments/${id}`,
      method: "get"
    })
  },
  //修改
  updateRewardsPunishments(rewardspunishments) {
    return request({
      url: `/staff/rewards_punishments`,
      method: "put",
      data: rewardspunishments
    })
  },
  //查询所有
  getRewardsPunishmentsList() {
    return request({
      url: `/staff/rewards_punishments/list`,
      method: "get"
    })
  },


}
