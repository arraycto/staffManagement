import request from '@/utils/request'


export default {
    //1.（查询分页）
    getAnnouncementListPage(current, limit, announcementQueryVo) {
      return request({
        url: `/staff/announcement/listPage/${current}/${limit}`,
        method: "post",
        data: announcementQueryVo
      })
    },
    getAnnouncementListPageClerk(current, limit, announcementQueryVo) {
        return request({
          url: `/staff/announcement/listPageClerk/${current}/${limit}`,
          method: "post",
          data: announcementQueryVo
        })
      },
    //2.删除
    deleteAnnouncementById(id) {
      return request({
        url: `/staff/announcement/${id}`,
        method: "delete"
      })
    },
    //3.添加
    addAnnouncement(announcement) {
      return request({
        url: `/staff/announcement`,
        method: "post",
        data: announcement
      })
    },
    //4.根据id查询
    getAnnouncementById(id) {
      return request({
        url: `/staff/announcement/${id}`,
        method: "get"
      })
    },
    //修改
    updateAnnouncement(announcement) {
      return request({
        url: `/staff/announcement`,
        method: "put",
        data: announcement
      })
    },
    //查询所有
    getAnnouncementList() {
        return request({
          url: `/staff/announcement/list`,
          method: "get"
        })
      },
      //查询首页信息
      numInfo() {
        return request({
          url: `/staff/announcement/numInfo`,
          method: "get"
        })
      },
  }
  
