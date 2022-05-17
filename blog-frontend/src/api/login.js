/*
 * @Descripttion: 
 * @Author: AruNi
 * @Date: 2022-04-10 16:33:05
 */
import request from '@/request'

export function login(account, password) {
  const data = {
    account,
    password
  }
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function logout(token) {
  return request({
    // 将请求头token传给后端
    headers: {'Authorization': token},
    url: '/logout',
    method: 'get'
  })
}

export function getUserInfo(token) {
  return request({
    // 将请求头token传给后端
    headers: {'Authorization': token},
    url: '/users/currentUser',
    method: 'get'
  })
}

export function register(account, nickname, password) {
  const data = {
    account,
    nickname,
    password
  }
  return request({
    url: '/register',
    method: 'post',
    data
  })
}
