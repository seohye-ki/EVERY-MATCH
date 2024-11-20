<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'
import axios from "axios";

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
  });

const useRou = useRouter()
const userStore = useUserStore()
const id = ref()
const isDuplicate = ref(false);

const prevStep = () => {
    useRou.push('/regist/nick') 
}

const nextStep = async () => {
	try {
		const formData = new FormData()
        formData.append('userId', id.value)
		const response = await api.post("user/check/id", formData)

		if (response.data) {
			console.log('아이디 중복 아님', response.data)
			userStore.setId(id.value)
			useRou.push("/regist/pw")
		} else {
			alert('아이디 설정 실패')
			isDuplicate.value = true
		}
	} catch (error) {
		if(error.response && error.response.status === 409){
			isDuplicate.value = true
		} else {
			console.error("아이디 확인 중 오류 발생:", error)
		}
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
          	<h4 class="fw-nomal">계정 ID 만들기</h4>
        </div>

        <!-- 오른쪽 섹션 -->
        <div style="width: 40%;">
          <form @submit.prevent="nextStep">
            <div class="mb-3">
              <label for="id" class="form-label fw-bold">
                아이디를 입력하세요!
              </label>
              <small class="d-block text-muted mb-2">
                아이디는 나중에 변경할 수 없어요.
              </small>
              <input
                id="id"
                type="text"
                v-model="id"
                class="form-control"
                placeholder="아이디를 입력하세요"
                maxlength="10"
                required
                style="border-radius: 10px;"
              />
              <small v-if="isDuplicate" class="text-danger mt-2 d-block">
                * 이 아이디는 이미 사용 중이에요. 다른 아이디를 시도해 보세요!
              </small>
            </div>
            <div class="d-flex justify-content-between">
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
