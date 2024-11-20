<script setup>
import { useRouter} from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'
import axios from "axios";


const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
  });

const useRou = useRouter()
const userStore = useUserStore()

const email = ref('')
const isInvalidEmail = ref(false)
const isDuplicateEmail = ref(false)

const prevStep = () => {
    useRou.push('/regist/id') 
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
      isDuplicateEmail.value = false
      return true
    }
  } catch (error) {
    if (error.response && error.response.status === 409) {
      isDuplicateEmail.value = true
    } else {
      console.error("서버 오류 발생:", error)
    }
    return false
  }
}

const nextStep = async (event) => {
	event.preventDefault()
	if (!isValidEmailFormat(email.value)) {
		isInvalidEmail.value = true
		return
	} else {
		isInvalidEmail.value = false
	}

	const isNotDuplicate = await checkEmailDuplicate();
	if (isNotDuplicate) {
		userStore.setEmail(email.value)
		userStore.submitRegist()
		useRou.push("/regist/fin")
	}
}
</script>

<template>
<div class="container vh-100 d-flex justify-content-center align-items-center">
    <div
      class="card shadow-sm p-5"
      style="max-width: 900px; width: 100%; border-radius: 15px;"
    >
      <div class="d-flex align-items-center justify-content-between">
        <!-- 왼쪽 섹션 -->
        <div>
          <img
            src="/src/assets/SMALL.png"
            alt="Icon"
            class="mb-3"
            style="width: 40px; height: 40px;"
          />
          <h4 class="fw-normal">이메일 입력</h4>
        </div>

        <!-- 오른쪽 섹션 -->
        <div style="width: 40%;">
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
                style="border-radius: 10px;"
                @input="isInvalidEmail = false; isDuplicateEmail = false"
              />
            </div>
            <!-- 에러 메시지 -->
            <small v-if="isInvalidEmail" class="text-danger mt-2 d-block">
              * 이메일 주소가 올바르지 않아요. 정확한 형식으로 입력해 주세요.
            </small>
			<small v-if="isDuplicateEmail" class="text-danger mt-2 d-block">
              * 이 이메일은 이미 사용 중입니다. 다른 이메일을 입력해 주세요.
            </small>
            <div class="d-flex justify-content-between mt-4">
              <button
                type="button"
                @click="prevStep"
                class="btn btn-outline-secondary"
                style="border-radius: 10px;"
              >
                &lt; 이전
              </button>
              <button
                type="submit"
                class="btn btn-danger"
                style="border-radius: 10px;"
              >
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

</style>
