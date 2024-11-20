<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'

const useRou = useRouter()
const userStore = useUserStore()
const nickname = ref('')
const nextStep = () => {
    userStore.setNickname(nickname.value)
    useRou.push('/regist/id')  
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
          <h4 class="fw-nomal">EVERY MATCH 계정 만들기</h4>
        </div>

        <!-- 오른쪽 섹션 -->
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
                required
                style="border-radius: 10px;"
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

</style>
