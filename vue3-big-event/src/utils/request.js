import axios from 'axios'
import { userStore } from '@/stores'
import { ElMessage } from 'element-plus'
import router from '@/router'
const baseURL = '/api'
const instance = axios.create({
  //基础地址，超时时间
  baseURL,
  timeout: 10000
})

instance.interceptors.request.use(
  (config) => {
    //携带token
    const userStores = userStore()
    if (userStores.token) {
      config.headers.Authorization = userStores.token
    }
    return config
  },
  (err) => {
    Promise.reject(err)
  }
)
instance.interceptors.response.use(
  (res) => {
    //处理业务失败
    //摘取核心数据
    if (res.data.code == 0) {
      return res.data
    }
    ElMessage.error(res.data.mesg ? res.data.mesg : '服务异常')
    return Promise.reject(res.data)
  },
  (err) => {
    //处理401错误
    //token过期，权限不够
    if (err.response.status === 401) {
      ElMessage.error('请先登录')
      router.push('/login')
    } else {
      ElMessage.error('服务异常')
    }
    //错误默认情况
    return Promise.reject(err)
  }
)
export default instance
export { baseURL }
