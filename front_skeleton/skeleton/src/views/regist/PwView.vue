<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'
import showAlert from '@/utils/swal';

const router = useRouter()
const userStore = useUserStore()

const password = ref('')
const confirmPassword = ref('')

const prevStep = () => {
    router.push('/regist/id') 
}

const nextStep = async () => {
    if (password.value.length < 8) {
        await showAlert('입력 오류', '비밀번호는 8자 이상이어야 합니다.', 'warning')
        return;
    }
    if (password.value !== confirmPassword.value) {
        await showAlert('비밀번호 불일치', '비밀번호가 일치하지 않아요. 다시 한 번 확인해 주세요.', 'error')
        return;
    }
    userStore.setPassword(password.value)
    router.push('/regist/email')
}
</script>

<template>
  <div class="container vh-100 d-flex justify-content-center align-items-center">
    <div class="card shadow-sm p-5 password-card">
      <div class="d-flex align-items-center justify-content-between">

        <div>
          <img src="/src/assets/SMALL.png" alt="Icon" class="mb-3 icon" />
          <h4 class="fw-normal">안전한 비밀번호 만들기</h4>
        </div>

        <div class="form-section">
          <form @submit.prevent="nextStep">
            <div class="mb-3">
              <label for="password" class="form-label fw-bold">
                비밀번호는 보안을 위해 8자 이상이어야 합니다
              </label>
              <input
                id="password"
                type="password"
                v-model="password"
                class="form-control"
                placeholder="비밀번호를 입력하세요"
                maxlength="20"
                required
              />
            </div>
            <div class="mb-3">
              <input
                id="confirm-password"
                type="password"
                v-model="confirmPassword"
                class="form-control"
                placeholder="비밀번호 확인"
                maxlength="20"
                required
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
.password-card {
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
