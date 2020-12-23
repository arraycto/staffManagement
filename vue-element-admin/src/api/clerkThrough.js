import request from '@/utils/request'


export default {
    //1.（查询分页）
    getClerkThrough() {
      return request({
        url: `/staff/clerk_through`,
        method: "post",
      })
    },
    //根据id查询id
    getClerkThroughByClerkId(id) {
      return request({
        url: `/staff/clerk_through/${id}`,
        method: "get",
      })
    },
  }
  
