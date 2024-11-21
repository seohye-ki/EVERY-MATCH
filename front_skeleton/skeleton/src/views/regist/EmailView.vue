<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'
import axios from "axios"
import showAlert from '@/utils/swal';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
});

const router = useRouter()
const userStore = useUserStore()

const email = ref('')

const prevStep = () => {
    router.push('/regist/id') 
}

const isValidEmailFormat = (email) => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  return emailRegex.test(email)
}

const checkEmailDuplicate = async () => {
  try {
    const formData = new FormData()
    formData.append('email', email.value)
    const response = await api.post("user/check/email", formData)

    if (response.status === 200) {
      return true
    }
  } catch (error) {
    if (error.response && error.response.status === 409) {
      await showAlert('이메일 중복', '이 이메일은 이미 사용 중입니다. 다른 이메일을 입력해 주세요.', 'error')
    } else {
      console.error("서버 오류 발생:", error)
      await showAlert('서버 오류', '이메일 확인 중 문제가 발생했습니다. 다시 시도해주세요.', 'error')
    }
    return false
  }
}

const nextStep = async (event) => {
  event.preventDefault()
  if (!isValidEmailFormat(email.value)) {
    await showAlert('입력 오류', '이메일 주소가 올바르지 않아요. 정확한 형식으로 입력해 주세요.', 'warning')
    return
  }

  const isNotDuplicate = await checkEmailDuplicate()
  if (isNotDuplicate) {
    userStore.setEmail(email.value)
    userStore.submitRegist()
    router.push("/regist/fin")
  }
}
</script>

<template>
  <div class="container vh-100 d-flex justify-content-center align-items-center">
    <div class="card shadow-sm p-5 email-card">
      <div class="d-flex align-items-center justify-content-between">

        <div>
          <img src="/src/assets/SMALL.png" alt="Icon" class="mb-3 icon" />
          <h4 class="fw-normal">이메일 입력</h4>
        </div>

        <div class="form-section">
          <form @submit.prevent="nextStep">
            <div class="mb-3">
              <label for="email" class="form-label fw-bold">
                <span class="me-2">🔔</span>경기 시작 1시간 전에 알림을 보내드릴게요
              </label>
              <input
                id="email"
                type="email"
                v-model="email"
                class="form-control"
                placeholder="이메일을 입력하세요"
                required
                @input="isDuplicateEmail = false"
              />
            </div>
            <div class="d-flex justify-content-between mt-4">
              <button type="button" @click="prevStep" class="btn btn-outline-secondary prev-btn">
                &lt; 이전
              </button>
              <button type="submit" class="btn btn-danger next-btn">
                다음
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.email-card {
  max-width: 900px;
  width: 100%;
  border-radius: 15px;
}

.icon {
  width: 40px;
  height: 40px;
}

.form-section {
  width: 40%;
}

.prev-btn, .next-btn {
  border-radius: 10px;
}
</style>
