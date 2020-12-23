import request from '@/utils/request'

export default {
  // 1.（查询分页）
  getClerkListPage(current, limit, clerkQueryVo) {
    return request({
      url: `/staff/clerk/listPage/${current}/${limit}`,
      method: 'post',
      data: clerkQueryVo
    })
  },
  getClerkListPageLizhi(current, limit, clerkQueryVo) {
    return request({
      url: `/staff/clerk/listPageLiZhi/${current}/${limit}`,
      method: 'post',
      data: clerkQueryVo
    })
  },
  // 2.删除
  deleteClerkById(id) {
    return request({
      url: `/staff/clerk/${id}`,
      method: 'delete'
    })
  },
  // 3.添加
  addClerk(clerk) {
    return request({
      url: `/staff/clerk`,
      method: 'post',
      data: clerk
    })
  },
  // 4.根据id查询
  getClerkById(id) {
    return request({
      url: `/staff/clerk/${id}`,
      method: 'get'
    })
  },
  // 修改
  updateClerk(clerk) {
    return request({
      url: `/staff/clerk`,
      method: 'put',
      data: clerk
    })
  },
  // 查询所有
  getClerkList() {
    return request({
      url: `/staff/clerk/list`,
      method: 'get'
    })
  },
  // 查询个人信息
  getClerkAndUserRole() {
    return request({
      url: `/staff/clerk/getClerkAndUserRole`,
      method: 'post'
    })
  },
  updateUser(user) {
    return request({
      url: `/staff/user`,
      method: 'put',
      data: user
    })
  },
  getPassword(password) {
    return request({
      url: '/staff/user/getPassword/' + password,
      method: 'post'
    })
  },
  putPassword(newPassword) {
    return request({
      url: '/staff/user/putPassword/' + newPassword,
      method: 'put'
    })
  },
  getClerkByDepartmentId(departmentID) {
    return request({
      url: '/staff/clerk/getClerkByDepartmentId/' + departmentID,
      method: 'get'
    })
  }
}
