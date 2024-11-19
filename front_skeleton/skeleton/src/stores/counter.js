import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

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
      const response = await axios.post('/regist', {
        nickname: user.value.nickname,
        id: user.value.id,
        password: user.value.password,
        email: user.value.email,
      })
      console.log('회원가입 성공:', response.data)
    } catch (error) {
      console.error('회원가입 실패:', error.response?.data || error.message)
    }
  }
  return { user, setId, setNickname, setPassword, setEmail, submitRegist }
})
