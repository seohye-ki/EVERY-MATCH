<script setup>
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/counter";
import { ref, onMounted } from "vue";
import axios from "axios";
import showAlert from "@/utils/swal";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 5000,
});

const router = useRouter();
const userStore = useUserStore();

const id = ref("");

const prevStep = () => {
  router.push("/regist/nick");
};

const nextStep = async () => {
  if (!id.value.trim()) {
    await showAlert("입력 오류", "아이디를 입력해주세요.", "warning");
    return;
  }
  try {
    const formData = new FormData();
    formData.append("userId", id.value);
    const response = await api.post("user/check/id", formData);

    if (response.data) {
      userStore.setId(id.value);
      router.push("/regist/pw");
    } else {
      await showAlert(
        "아이디 설정 실패",
        "이 아이디는 이미 사용 중이에요. 다른 아이디를 시도해 보세요!",
        "error"
      );
    }
  } catch (error) {
    if (error.response && error.response.status === 409) {
      await showAlert(
        "아이디 중복",
        "이 아이디는 이미 사용 중이에요. 다른 아이디를 시도해 보세요!",
        "error"
      );
    } else {
      console.error("아이디 확인 중 오류 발생:", error);
      await showAlert(
        "서버 오류",
        "아이디 확인 중 문제가 발생했습니다. 다시 시도해주세요.",
        "error"
      );
    }
  }
};

const progressWidth = ref(25);

onMounted(() => {
  let progress = 25;
  const interval = setInterval(() => {
    progress += 5; // 증가 속도
    progressWidth.value = progress;

    if (progress >= 50) {
      clearInterval(interval);
    }
  }, 100); // 100ms마다 업데이트
});
</script>

<template>
  <div class="container-fluid">
    <!-- Progress Bar -->
    <div class="progress-container">
      <div class="progress-text">2 / 4</div>
      <div class="progress-bar">
        <div
          class="progress-fill"
          :style="{ width: progressWidth + '%' }"
        ></div>
      </div>
    </div>

    <!-- Card: Form Content -->
    <div class="card">
      <div class="form-content">
        <!-- Left Section: Icon and Title -->
        <div class="left-section">
          <img src="@/assets/small.png" alt="icon" class="icon" />
          <h2>계정 ID 만들기</h2>
        </div>

        <!-- Right Section: Input and Buttons -->
        <div class="right-section">
          <form @submit.prevent="nextStep">
            <div class="input-section">
              <label for="id">아이디를 입력하세요!</label>
              <p>아이디는 나중에 변경할 수 없어요.</p>
              <input
                id="id"
                v-model="id"
                type="text"
                placeholder="아이디를 입력하세요"
              />
            </div>

            <!-- Navigation Buttons -->
            <div class="button-group">
              <button class="prev-button" @click="prevStep" type="button">
                &lt; 이전
              </button>
              <button class="next-button" @click="nextStep" type="submit">
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
  width: 50%; /* 2 / 4 진행 */
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

.input-section p {
  font-size: 12px;
  color: #999;
  margin-bottom: 10px;
}

.input-section input {
  width: 100%;
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
