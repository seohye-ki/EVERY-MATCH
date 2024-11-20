<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'

const useRou = useRouter()
const userStore = useUserStore()
const password = ref('')
const confirmPassword = ref('')
const isMismatch = ref(false)

const prevStep = () => {
    useRou.push('/regist/id') 
}

const nextStep = () => {
	if(password.value.length < 8) {
		alert('비밀번호는 8자 이상이어야 합니다')
	}
	else if(password.value === confirmPassword.value) {
		isMismatch.value = false
		userStore.setPassword(password.value)
		useRou.push('/regist/email')
    } else {
    	isMismatch.value = true
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
          <h4 class="fw-normal">안전한 비밀번호 만들기</h4>
        </div>

        <!-- 오른쪽 섹션 -->
        <div style="width: 40%;">
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
                style="border-radius: 10px;"
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
                style="border-radius: 10px;"
              />
            </div>
            <!-- 에러 메시지 -->
            <small v-if="isMismatch" class="text-danger mt-2 d-block">
              * 비밀번호가 일치하지 않아요. 다시 한 번 확인해 주세요.
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
