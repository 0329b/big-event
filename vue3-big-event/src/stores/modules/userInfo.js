import { defineStore } from 'pinia'
import { ref } from 'vue'
export const userInfoStore = defineStore(
  'userInfo',
  () => {
    const userInfo = ref('')
    const setUserInfo = (newToken) => {
      userInfo.value = newToken
    }
    const removeUserInfo = () => {
      userInfo.value = ''
    }
    return {
      userInfo,
      setUserInfo,
      removeUserInfo
    }
  },
  {
    persist: true
  }
)
