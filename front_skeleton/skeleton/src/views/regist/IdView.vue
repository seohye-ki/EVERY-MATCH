<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'
import axios from "axios";
import showAlert from '@/utils/swal';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
});

const router = useRouter()
const userStore = useUserStore()

const id = ref('')

const prevStep = () => {
    router.push('/regist/nick') 
}

const nextStep = async () => {
    if (!id.value.trim()) {
        await showAlert('입력 오류', '아이디를 입력해주세요.', 'warning')
        return;
    }
    try {
        const formData = new FormData()
        formData.append('userId', id.value)
        const response = await api.post("user/check/id", formData)

        if (response.data) {
            console.log('아이디 중복 아님', response.data)
            userStore.setId(id.value)
            router.push("/regist/pw")
        } else {
            await showAlert('아이디 설정 실패', '이 아이디는 이미 사용 중이에요. 다른 아이디를 시도해 보세요!', 'error')
        }
    } catch (error) {
        if (error.response && error.response.status === 409) {
            await showAlert('아이디 중복', '이 아이디는 이미 사용 중이에요. 다른 아이디를 시도해 보세요!', 'error')
        } else {
            console.error("아이디 확인 중 오류 발생:", error)
            await showAlert('서버 오류', '아이디 확인 중 문제가 발생했습니다. 다시 시도해주세요.', 'error')
        }
    }
}
</script>

<template>
  <div class="container vh-100 d-flex justify-content-center align-items-center">
    <div class="card shadow-sm p-5 id-card">
      <div class="d-flex align-items-center justify-content-between">
        
        <div>
          <img src="/src/assets/SMALL.png" alt="Icon" class="mb-3 icon" />
          <h4 class="fw-normal">계정 ID 만들기</h4>
        </div>

        <div class="form-section">
          <form @submit.prevent="nextStep">
            <div class="mb-3">
              <label for="id" class="form-label fw-bold">아이디를 입력하세요!</label>
              <small class="d-block text-muted mb-2">아이디는 나중에 변경할 수 없어요.</small>
              <input
                id="id"
                type="text"
                v-model="id"
                class="form-control"
                placeholder="아이디를 입력하세요"
                maxlength="10"
                required
              />
            </div>
            <div class="d-flex justify-content-between">
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
.id-card {
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
