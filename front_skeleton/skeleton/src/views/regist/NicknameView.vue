<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'
import showAlert from '@/utils/swal'

const router = useRouter()
const userStore = useUserStore()

const nickname = ref('')

const nextStep = () => {
    if (!nickname.value.trim()) {
        showAlert('닉네임을 입력해주세요', '닉네임을 반드시 입력해야 합니다.', 'error')
        return
    }
    userStore.setNickname(nickname.value)
    router.push('/regist/id')  
}
</script>

<template>
  <div class="container vh-100 d-flex justify-content-center align-items-center">
    <div
      class="card shadow-sm p-5"
      style="max-width: 900px; width: 100%; border-radius: 15px;"
    >
      <div class="d-flex align-items-center justify-content-between">
        
		<div>
          <img
            src="/src/assets/SMALL.png"
            alt="Icon"
            class="mb-3"
            style="width: 40px; height: 40px;"
          />
          <h4 class="fw-normal">EVERY MATCH 계정 만들기</h4>
        </div>

        <div style="width: 40%;">
          <form @submit.prevent="nextStep">
            <div class="mb-4">
              <label for="nickname" class="form-label fw-bold">
                앞으로 이렇게 불러주세요!
              </label>
              <input
                id="nickname"
                type="text"
                v-model="nickname"
                class="form-control"
                placeholder="닉네임을 입력하세요"
                maxlength="10"
                style="border-radius: 10px;"
                required
              />
            </div>
            <button
              type="submit"
              class="btn btn-danger w-100"
              style="border-radius: 10px;"
            >
              다음
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.container {
  background-color: #f8f9fa;
}
.card {
  border: none;
}
.btn {
  transition: background-color 0.3s;
}
.btn:hover {
  background-color: #c82333;
}
</style>
