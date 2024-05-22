import request from '@/utils/request'
export const articleCategoryListService = () => {
  return request.get('/category')
}
export const articleCategoryAddService = (categoryDate) => {
  return request.post('/category', categoryDate)
}
export const articleCategoryUpdateService = (categoryDate) => {
  return request.put('/category', categoryDate)
}
export const articleCategoryDeleteService = (id) => {
  return request.delete('/category?id=' + id)
}
//获取文章列表
export const articleListService = (params) => {
  return request.get('/article', { params: params })
}
//发布文章
export const articleAddService = (articleDate) => {
  return request.post('/article', articleDate)
}
//删除文章
export const articleDeleteService = (id) => {
  return request.delete('/article?id=' + id)
}
export const articleUpdateService = (articleDate) => {
  return request.put('/article', articleDate)
}
