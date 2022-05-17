/*
 * @Descripttion: 
 * @Author: AruNi
 * @Date: 2022-04-10 16:33:05
 */
import request from '@/request'

export function getAllCategories() {
  return request({
    url: '/categories',
    method: 'get',
  })
}

export function getAllCategoriesDetail() {
  return request({
    url: '/categories/detail',
    method: 'get',
  })
}

export function getCategory(id) {
  return request({
    url: `/categories/${id}`,
    method: 'get',
  })
}

export function getCategoryDetail(id) {
  return request({
    url: `/categories/detail/${id}`,
    method: 'get',
  })
}
