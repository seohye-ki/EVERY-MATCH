import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',  // 백엔드 API 기본 URL
  timeout: 5000,
});

export const useUserStore = defineStore('user', () => {
  const user = ref({
    nickname: '',
    id: '',
    password:'',
    email: '',
  })

  const setNickname = (nickname) => {
    user.value.nickname=nickname
  }
  const setId = (id) => {
    user.value.id=id
  }
  const setPassword = (password) => {
    user.value.password=password
  }
  const setEmail = (email) => {
    user.value.email=email
  }

  const submitRegist = async () => {
    try {
      const send = {
        nickname: user.value.nickname,
        userId: user.value.id,
        password: user.value.password,
        email: user.value.email,
      }
      const response = await api.post('/user/regist', send)
      console.log('회원가입 성공:', response.data)
    } catch (error) {
      console.error('회원가입 실패:', error.response?.data || error.message)
    }
  }
  return { user, setId, setNickname, setPassword, setEmail, submitRegist }
})
