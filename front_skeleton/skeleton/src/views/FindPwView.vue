<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import showAlert from "@/utils/swal";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 5000,
});

const router = useRouter();

const id = ref("");
const email = ref("");

const cancel = () => {
  router.push("/main");
};

const sendPassword = async () => {
  if (!id.value.trim() || !email.value.trim()) {
    await showAlert("입력 오류", "아이디와 이메일을 모두 입력해주세요.", "warning");
    return;
  }

  try {
    const formData = new FormData();
    formData.append("userId", id.value);
    formData.append("email", email.value);

    const response = await api.post("/user/reset-password", formData);

    if (response.status === 200) {
      await showAlert("임시 비밀번호 발송", "임시 비밀번호가 등록된 이메일로 발송되었습니다.", "success");
      router.push("/login");
    }
  } catch (error) {
    if (error.response && error.response.status === 400) {
      await showAlert("오류", "입력하신 아이디와 이메일 정보가 일치하지 않습니다.", "error");
    } else {
      console.error("서버 오류:", error);
      await showAlert("서버 오류", "비밀번호 재설정 요청 중 문제가 발생했습니다. 다시 시도해주세요.", "error");
    }
  }
};
</script>

<template>
	<div class="container">	
      <!-- Reset Password Form -->
      <div class="reset-form">
		<!-- 로고 -->
		<img src="/src/assets/EVERYMATCH.png" alt="EVERYMATCH Logo" class="logo" />
		<div class="input-group">
		  <!-- ID 입력 -->
		  <label for="id" class="label">ID</label>
		  <input v-model="id" type="text" placeholder="아이디를 입력하세요" />
		</div>
  
		<div class="input-group">
		  <!-- 이메일 입력 -->
		  <label for="email" class="label">Email</label>
		  <input
			id="email"
			v-model="email"
			type="email"
			placeholder="이메일을 입력하세요"
		  />
		</div>
  
		<!-- 버튼 그룹 -->
		<div class="button-group">
		  <button @click="cancel" class="cancel-button">취소</button>
		  <button @click="sendPassword" class="submit-button">임시 비밀번호 전송</button>
		</div>
	  </div>
	</div>
</template>

<style scoped>
/* 부모 컨테이너 */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  max-width: 100%;
  background-color: #f9f9f9;
  padding: 20px;
  box-sizing: border-box;
}

/* Reset Password Form */
.reset-form {
  background-color: #ffffff;
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 */
  max-width: 400px;
  width: 100%;
  text-align: center;
}

/* 로고 스타일 */
.logo {
  width: 70%;
  margin-bottom: 30px; /* 입력 필드와 간격 */
}

/* 입력 그룹 */
.input-group {
  margin-bottom: 20px; /* 그룹 간격 */
  text-align: left; /* 라벨 정렬 */
}

.label {
  font-size: 14px;
  color: #333;
  font-weight: bold;
  margin-bottom: 8px;
  display: block; /* 블록 형태로 정렬 */
}

.input-group input {
  width: 100%;
  padding: 12px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 6px;
  box-sizing: border-box;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  justify-content: space-between;
  gap: 10px; /* 버튼 간격 */
  margin-top: 20px; /* 입력 필드와 간격 */
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

/* 임시 비밀번호 전송 버튼 */
.submit-button {
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

.submit-button:hover {
  background-color: #ec493a;
}
</style>
