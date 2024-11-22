<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref, onMounted } from 'vue'
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

const progressWidth = ref(0)

onMounted(() => {
  let progress = 0;
  const interval = setInterval(() => {
    progress += 5; // 증가 속도
    progressWidth.value = progress;

    if (progress >= 75) {
      clearInterval(interval); // 25%에 도달하면 애니메이션 종료
    }
  }, 100); // 100ms마다 업데이트
});
</script>

<template>
	<div class="container">
	  <!-- Progress Bar -->
	  <div class="progress-container">
		<div class="progress-text">3 / 4</div>
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
			<h2>안전한 비밀번호 만들기</h2>
		  </div>
  
		  <!-- Right Section: Input and Buttons -->
		  <div class="right-section">
			<div class="input-section">
			  <label for="password">비밀번호는 보안을 위해 8자 이상이어야 합니다</label>
			  <input
				id="password"
				v-model="password"
				type="password"
				placeholder="비밀번호를 입력하세요"
			  />
			  <input
				id="confirm-password"
				v-model="confirmPassword"
				type="password"
				placeholder="비밀번호 확인"
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
.container {
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
  width: 75%; /* 3 / 4 진행 */
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

.input-section label {
  display: block;
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
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