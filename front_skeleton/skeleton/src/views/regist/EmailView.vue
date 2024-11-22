<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref, onMounted } from 'vue'
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

const progressWidth = ref(0)

onMounted(() => {
  let progress = 0;
  const interval = setInterval(() => {
    progress += 5; // 증가 속도
    progressWidth.value = progress;

    if (progress >= 100) {
      clearInterval(interval); // 25%에 도달하면 애니메이션 종료
    }
  }, 100); // 100ms마다 업데이트
});
</script>

<template>
	<div class="container-fluid">
	  <!-- Progress Bar -->
	  <div class="progress-container">
		<div class="progress-text">4 / 4</div>
		<div class="progress-bar">
		  <div class="progress-fill" :style="{ width: progressWidth + '%' }"></div>
		</div>
	  </div>
  
	  <!-- Card: Form Content -->
	  <div class="card">
		<div class="form-content">
		  <!-- Left Section: Icon and Title -->
		  <div class="left-section">
			<img src="@/assets/small.png" alt="icon" class="icon" />
			<h2>이메일 입력</h2>
		  </div>
  
		  <!-- Right Section: Input and Buttons -->
		  <div class="right-section">
			<div class="input-section">
			  <label for="email">
				<span class="alert-icon">🔔</span> 경기 시작 1시간 전에 알림을 보내드릴게요
			  </label>
			  <input
				id="email"
				v-model="email"
				type="email"
				placeholder="이메일을 입력하세요"
			  />
			</div>
  
			<!-- Navigation Buttons -->
			<div class="button-group">
			  <button class="prev-button" @click="prevStep">&lt; 이전</button>
			  <button class="next-button" @click="nextStep">다음</button>
			</div>
		  </div>
		</div>
	  </div>
	</div>
</template>

<style scoped>
/* 부모 컨테이너 */
.container-fluid {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f9f9f9;
  margin: 0;
  padding: 20px;
  box-sizing: border-box;
}

/* Progress Bar 컨테이너 */
.progress-container {
  width: 100%;
  max-width: 800px;
  margin-bottom: 30px;
}

.progress-text {
  font-size: 14px;
  margin-bottom: 8px;
  color: #666;
  text-align: left;
}

.progress-bar {
  height: 8px;
  background-color: #f0f0f0;
  border-radius: 4px;
  position: relative;
  overflow: hidden;
}

.progress-fill {
  width: 100%; /* 4 / 4 진행 */
  height: 100%;
  background-color: #ec493a;
  border-radius: 4px;
  transition: width 0.3s ease;
}

/* Card 스타일 */
.card {
  background-color: #ffffff;
  padding: 50px 40px;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 800px;
}

/* Form Content 스타일 */
.form-content {
  display: flex;
  justify-content: space-between;
  align-items: top;
  gap: 20px;
  flex-wrap: wrap;
}

/* Left Section 스타일 */
.left-section {
  flex: 1;
  text-align: left;
}

.icon {
  width: 40px;
  margin-bottom: 10px;
}

h2 {
  font-size: 22px;
  margin-bottom: 10px;
  color: #333;
}

/* Right Section 스타일 */
.right-section {
  flex: 1;
}

.input-section {
  margin-bottom: 10px;
}

.input-section label {
  display: block;
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.alert-icon {
  margin-right: 8px;
  font-size: 16px;
  color: #ec493a;
}

.input-section input {
  width: 100%;
  margin-bottom: 10px;
  padding: 12px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 6px;
}

/* Button Group */
.button-group {
  display: flex;
  justify-content: space-between;
  gap: 10px;
}

.prev-button,
.next-button {
  padding: 12px 20px;
  font-size: 14px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  flex: 1;
}

.prev-button {
  background-color: #f0f0f0;
  color: #555;
}

.prev-button:hover {
  background-color: #e0e0e0;
}

.next-button {
  background-color: #da4537;
  color: #fff;
}

.next-button:hover {
  background-color: #ec493a;
}
</style>