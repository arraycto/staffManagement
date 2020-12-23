import request from '@/utils/request'


export default {
    //1.（查询分页）
    getContractListPage(current, limit, contractQueryVo) {
      return request({
        url: `/staff/contract/listPage/${current}/${limit}`,
        method: "post",
        data: contractQueryVo
      })
    },
    //2.删除
    deleteContractById(id) {
      return request({
        url: `/staff/contract/${id}`,
        method: "delete"
      })
    },
    //3.添加
    addContract(contract) {
      return request({
        url: `/staff/contract`,
        method: "post",
        data: contract
      })
    },
    //4.根据id查询
    getContractById(id) {
      return request({
        url: `/staff/contract/${id}`,
        method: "get"
      })
    },
    //修改
    updateContract(contract) {
      return request({
        url: `/staff/contract`,
        method: "put",
        data: contract
      })
    },
    //查询所有
    getContractList() {
        return request({
          url: `/staff/contract/list`,
          method: "get"
        })
      },
    //查询自身
    getClerkThrough() {
        return request({
          url: `/staff/contract/getContract`,
          method: "post",
        })
      },
  }
  
