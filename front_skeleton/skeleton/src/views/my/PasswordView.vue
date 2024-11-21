<script setup>
import { ref } from "vue"
import { useRouter } from "vue-router"
import axios from 'axios'
import showAlert from '@/utils/swal'

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
})

api.interceptors.request.use((config) => {
    const token = sessionStorage.getItem('Authorization')
    if (token) {
      config.headers['Authorization'] = `${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)

const router = useRouter()

const password = ref("")

const validate = async () => {
  try {
    const formData = new FormData()
    formData.append('password', password.value)
    const response = await api.post("/user/validate-password", formData)

    if (response.status === 200) {
      await showAlert('인증 성공', '비밀번호가 확인되었습니다.', 'success')
      router.push("./info")
    }
  } catch (error) {
    if (error.response && error.response.status === 403) {
      await showAlert('인증 실패', '비밀번호가 일치하지 않습니다. 다시 시도해주세요.', 'error')
    } else {
      console.error("서버 오류 발생:", error)
      await showAlert('서버 오류', '서버와의 통신 중 오류가 발생했습니다. 다시 시도해주세요.', 'error')
    }
  }
}

const cancel = () => {
  router.push("/main")
}
</script>

<template>
  <div class="container vh-100 d-flex justify-content-center align-items-center">
    <div class="card shadow-sm text-center p-4" style="max-width: 400px; width: 100%; border-radius: 15px;">
      <div class="text-center mb-4">
        <img src="/src/assets/EVERYMATCH.png" alt="Logo" class="mx-auto logo" style="width: 150px;"/>
      </div>

      <form @submit.prevent="validate">
        <div class="mb-3">
          <input
            id="pw"
            type="password"
            v-model="password"
            class="form-control"
            placeholder="비밀번호를 입력하세요"
            required
            style="border-radius: 10px;"
          />
        </div>
        <div class="d-flex justify-content-between">
          <button
            type="button"
            @click="cancel"
            class="btn btn-outline-secondary"
            style="border-radius: 10px; width: 48%;"
          >
            취소
          </button>
          <button type="submit" class="btn btn-danger" style="border-radius: 10px; width: 48%;">
            확인
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
body {
  background-color: #f9f9f9;
}

.logo {
  max-width: 100%;
  height: auto;
  max-height: 50px;
}

.card {
  border: none;
}
</style>
