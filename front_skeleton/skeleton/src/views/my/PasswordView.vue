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
	  password.value = ''
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
	<div class="container">
	  <!-- 인증 폼 -->
	  <div class="auth-form">
		<!-- 로고 -->
	    <img src="/src/assets/EVERYMATCH.png" alt="EVERYMATCH Logo" class="logo" />
		<form @submit.prevent="validate">
			<label for="password" class="label">Password</label>
			<input
			  id="password"
			  v-model="password"
			  type="password"
			  placeholder="비밀번호를 입력하세요"
			  class="input"
			/>
			<div class="button-group">
			  <button @click="cancel" type="button" class="cancel-button">취소</button>
			  <button type="submit" class="confirm-button">확인</button>
			</div>
		</form>
	  </div>
	</div>
</template>

<style scoped>
/* 부모 컨테이너 */
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f9f9f9;
  padding: 20px;
  box-sizing: border-box;
}

/* 로고 스타일 */
.logo {
  width: 70%;
  margin-bottom: 30px;
}

/* 인증 폼 */
.auth-form {
  background-color: #ffffff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
  text-align: center;
}

/* 라벨 */
.label {
  font-size: 14px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
  display: block;
  text-align: left;
}

/* 입력 필드 */
.input {
  width: 100%;
  padding: 12px;
  margin-bottom: 20px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

/* 확인 버튼 */
.confirm-button {
  flex: 1;
  padding: 12px;
  background-color: #da4537;
  color: #ffffff;
  font-size: 14px;
  font-weight: bold;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.confirm-button:hover {
  background-color: #ec493a;
}

/* 취소 버튼 */
.cancel-button {
  flex: 1;
  padding: 12px;
  background-color: #f0f0f0;
  color: #555;
  font-size: 14px;
  font-weight: bold;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.cancel-button:hover {
  background-color: #e0e0e0;
}
</style>