<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const goToLogin = () => {
  router.push("/");
};
</script>
<template>
	<div class="container">
	  <div class="card">
		<!-- 로고 -->
		<div class="logo-container">
          <img src="@/assets/EVERYMATCH.png" alt="EVERYMATCH Logo" class="logo" />
        </div>
  
		<!-- Welcome Message -->
		<div class="welcome-message">
		  <p class="highlight">🎉 환영합니다 🎉</p>
		  <p class="bold">회원가입이 완료되었습니다.</p>
		  <p class="description">오늘의 경기를 놓치지 말고 바로 시작하세요!</p>
		</div>
  
		<!-- Button to Login -->
		<button @click="goToLogin" class="login-button">로그인하러 가기</button>
	  </div>
	</div>
</template>

<style scoped>
/* 부모 컨테이너: 중앙 정렬 */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f9f9f9;
  margin: 0;
  padding: 20px;
  box-sizing: border-box;
}

/* 카드 스타일 */
.card {
  background-color: #ffffff;
  padding: 60px 40px;
  border-radius: 16px;
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1); /* 강조된 그림자 */
  width: 100%;
  max-width: 500px;
  text-align: center; /* 모든 콘텐츠 가운데 정렬 */
  animation: fadeIn 0.8s ease; /* 등장 애니메이션 */
}

/* 로고 컨테이너 */
.logo-container {
  margin-bottom: 30px;
}

.logo {
  width: 70%;
}

/* 환영 메시지 */
.welcome-message {
  margin-bottom: 40px;
  font-size: 16px;
  line-height: 1.6;
  color: #333;
}

.welcome-message .highlight {
  font-size: 24px;
  font-weight: bold;
  color: #da4537; /* 강조 색상 */
  margin-bottom: 12px;
  animation: bounce 1.5s infinite; /* 강조 애니메이션 */
}

.welcome-message .bold {
  font-weight: bold;
  font-size: 20px;
  margin-bottom: 10px;
}

.welcome-message .description {
  font-size: 16px;
  color: #666;
}

/* 로그인 버튼 */
.login-button {
  display: inline-block;
  width: 100%;
  padding: 14px 20px;
  font-size: 16px;
  font-weight: bold;
  color: #ffffff;
  background-color: #da4537;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.login-button:hover {
  background-color: #ec493a;
  transform: translateY(-2px); /* 버튼 호버 시 위로 살짝 이동 */
}

.login-button:active {
  transform: translateY(0); /* 클릭 시 원래 위치 */
}

/* 애니메이션 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.95);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-8px);
  }
}
</style>
