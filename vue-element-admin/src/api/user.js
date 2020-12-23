import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/staff/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/staff/user/info',
    method: 'get',
    params: {
      token
    }
  })
}

export function logout() {
  return request({
    url: '/staff/user/logout',
    method: 'post'
  })
}
