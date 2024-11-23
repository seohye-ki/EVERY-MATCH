<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import showAlert from "@/utils/swal"; // 커스텀 Alert 기능

// Axios 설정
const api = axios.create({
  baseURL: "http://localhost:8080/api", // API 기본 URL
  timeout: 5000, // 요청 타임아웃
});

// Router 사용
const router = useRouter();

// Reactive 변수 선언
const id = ref("");
const pw = ref("");

// 로그인 함수
const login = async () => {
  // 필수 입력값 검증
  if (!id.value || !pw.value) {
    await showAlert(
      "입력 오류",
      "아이디와 비밀번호를 입력해주세요.",
      "warning"
    );
    return;
  }

  try {
    // FormData 생성
    const formData = new FormData();
    formData.append("userId", id.value);
    formData.append("password", pw.value);

    // API 요청
    const response = await api.post("/user/login", formData);

    // 로그인 성공 처리
    if (response.status === 200) {
      console.log("로그인 성공", response.data);

      // JWT 토큰 저장
      sessionStorage.setItem("Authorization", response.data["access-token"]);
      sessionStorage.setItem("nickname", response.data["nickname"]);

      // 메인 페이지로 이동
      router.push("/main");
    }
  } catch (error) {
    // 에러 상태에 따른 처리
    if (error.response && error.response.status === 401) {
      await showAlert(
        "로그인 실패",
        "아이디 또는 비밀번호가 잘못되었습니다.",
        "error"
      );
    } else {
      await showAlert(
        "서버 오류",
        "로그인 중 문제가 발생했습니다. 다시 시도해주세요.",
        "error"
      );
    }
    console.error("로그인 오류:", error);
  }
};
</script>
<template>
  <div class="container">
    <div class="login-form">
      <!-- Logo -->
      <img src="/src/assets/EVERYMATCH.png" alt="logo" class="logo" />

      <!-- Login Form -->
      <form @submit.prevent="login">
        <div class="input-section">
          <input v-model="id" type="text" placeholder="아이디를 입력하세요" />
          <input
            v-model="pw"
            type="password"
            placeholder="비밀번호를 입력하세요"
          />
        </div>
        <button @click="login" type="submit">로그인</button>
      </form>

      <!-- Links -->
      <div class="links">
        <a href="./find">비밀번호 재설정</a>
        <a href="./regist/nick">회원가입</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 부모 컨테이너: 중앙 정렬 */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  max-width: 100%;
  background-color: #f9f9f9;
  box-sizing: border-box;
}

/* 로그인 폼 */
.login-form {
  background-color: #ffffff;
  padding: 50px 40px;
  border-radius: 16px;
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1); /* 강조된 그림자 */
  width: 100%;
  max-width: 400px;
  text-align: center;
}

/* 로고 스타일 */
.logo {
  margin-bottom: 30px;
  width: 70%;
}

/* 입력 섹션 */
.input-section input {
  width: 100%;
  padding: 12px;
  margin-bottom: 15px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

/* 로그인 버튼 */
.login-form button {
  width: 100%;
  padding: 14px;
  background-color: #da4537;
  color: #ffffff;
  font-size: 16px;
  font-weight: bold;
  border: none;
  border-radius: 8px;
}

.login-form button:hover {
  background-color: #ec493a;
}

/* 링크 섹션 */
.links {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  font-size: 14px;
}

.links a {
  text-decoration: none;
  color: #888;
  transition: color 0.2s ease;
}

.links a:hover {
  color: #da4537;
  text-decoration: underline;
}
</style>
