import request from '@/utils/request'
export const userRegisterService = (fromModel) => {
  const params = new URLSearchParams()
  for (let key in fromModel) {
    params.append(key, fromModel[key])
  }
  return request.post('/user/register', params)
}
export const userLoginService = (fromModel) => {
  const params = new URLSearchParams()
  for (let key in fromModel) {
    params.append(key, fromModel[key])
  }
  return request.post('/user/login', params)
}
//获取用户详细信息
export const userInfoService = () => {
  return request.get('/user/userInfo')
}
//重置密码
export const userResetService = (fromDate) => {
  return request.patch('/user/updatePwd', fromDate)
}
//修改用户信息2
export const userInfService = (fromDate) => {
  return request.post('/user/update', fromDate)
}
//上传头像
export const userImgUrlService = (avatar) => {
  const params = new URLSearchParams()
  params.append('avatarUrl', avatar)
  return request.patch('/user/updateAvatar', params)
}
