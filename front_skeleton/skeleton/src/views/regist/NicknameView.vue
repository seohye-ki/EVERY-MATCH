<script setup>
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref, onMounted } from 'vue'
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

const progressWidth = ref(0)

onMounted(() => {
  let progress = 0;
  const interval = setInterval(() => {
    progress += 5; // 증가 속도
    progressWidth.value = progress;

    if (progress >= 25) {
      clearInterval(interval); // 25%에 도달하면 애니메이션 종료
    }
  }, 100); // 100ms마다 업데이트
});
</script>

<template>
	<div class="container">
		<!-- Progress Bar -->
	  <div class="progress-container">
		<div class="progress-text">1 / 4</div>
		<div class="progress-bar">
		  <div class="progress-fill" :style="{ width: progressWidth + '%' }"></div>
		</div>
	  </div>

	  <!-- Form Content -->
	  <div class="card">
		<div class="form-content">
		  <div class="left-section">
            <img src="@/assets/small.png" alt="icon" class="icon" />
            <h2>EVERY MATCH 계정 만들기</h2>
          </div>
  
		<!-- Input and Button in Horizontal Layout -->
		<div class="right-section">
			<div class="input-section">
				<label for="nickname">앞으로 이렇게 불러주세요!</label>
				<input
				id="nickname"
				v-model="nickname"
				type="text"
				placeholder="닉네임을 입력하세요"
				/>
			</div>
		    	<button @click="nextStep" class="next-button">다음</button>
			</div>
		</div>
	  </div>
	</div>
</template>

<style scoped>
/* 부모 컨테이너: 중앙 정렬 */
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f9f9f9;
  margin: 0;
  padding: 20px;
  box-sizing: border-box;
}

/* Card 스타일 */
.card {
  background-color: #ffffff;
  padding: 80px 40px;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  width: 100%;
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
  width: 25%; /* 1/4 진행 */
  height: 100%;
  background-color: #ec493a;
  border-radius: 4px;
  transition: width 0.3s ease;
}

/* Form Content: Flexbox Layout */
.form-content {
  display: flex;
  justify-content: space-between;
  align-items: top;
  gap: 20px;
  flex-wrap: wrap;
}

/* Left Section */
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

/* Right Section */
.right-section {
  flex: 1;
}

.input-section {
  margin-bottom: 10px;
  text-align: left;
}

.input-section label {
  display: block;
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.input-section input {
  width: 100%;
  padding: 12px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 6px;
}

/* Button 스타일 */
.next-button {
  width: 100%;
  padding: 12px 20px;
  background-color: #da4537;
  color: #fff;
  font-size: 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.next-button:hover {
  background-color: #ec493a;
}
</style>