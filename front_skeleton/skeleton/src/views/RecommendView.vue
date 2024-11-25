<script setup>
import { ref, computed } from "vue";
import axios from "axios";
import showAlert from "@/utils/swal";
import { useRouter } from "vue-router";

// 라우터 사용
const router = useRouter();

// API 인스턴스 생성
const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

api.interceptors.request.use(
  (config) => {
    // sessionStorage에서 JWT 토큰 가져오기
    const token = sessionStorage.getItem("Authorization");
    // 토큰이 존재하면 Authorization 헤더에 추가
    if (token) {
      config.headers["Authorization"] = `${token}`;
    }

    // config 반환 (요청을 보내기 위해)
    return config;
    
  },
  (error) => {
    // 요청 오류 처리
    return Promise.reject(error);
  }
);

// Reactive 데이터 선언
const questions = ref([
  "가장 좋아하는 스포츠는 무엇인가요?",
  "어떤 경기 스타일을 더 좋아하시나요?",
  "응원할 때 당신은 어떤 스타일인가요?",
  "특정 지역 팀을 응원하고 싶으신가요?",
  "팀의 어떤 점이 중요하다고 생각하시나요?",
]);

const optionsData = ref([
  ["⚽️ 축구", "⚾️ 야구", "🏀 농구", "🏐 배구"],
  ["🔥 공격적인 플레이", "🛡️ 안정적인 수비", "🎭 화려한 기술", "🏃‍♂️ 빠른 템포"],
  [
    "🎉 열정적인 응원",
    "📺 집에서 관람",
    "👥 친구들과 함께",
    "🎶 응원 분위기 즐기기",
  ],
  ["서울", "부산", "상관없음", "기타 지역"],
  ["⭐ 스타 플레이어", "📊 팀워크", "🏆 최근 성적", "🕰️ 전통과 역사"],
]);

const currentQuestionIndex = ref(0);
const answerMessage = ref("질문과 답을 바탕으로 프로 스포츠 구단을 추천해줘. ");
const isResultVisible = ref(false);
const resultMessage = ref("");
const isLoading = ref(false);

// 계산 속성
const progressWidth = computed(() => {
  return `${
    ((currentQuestionIndex.value + 1) / questions.value.length) * 100
  }%`;
});

const progressText = computed(() => {
  return `질문 ${currentQuestionIndex.value + 1}/${questions.value.length}`;
});

const currentQuestionText = computed(() => {
  return questions.value[currentQuestionIndex.value];
});

const currentOptions = computed(() => {
  return optionsData.value[currentQuestionIndex.value];
});

// 다음 질문으로 이동
const nextQuestion = (answer) => {
  answerMessage.value += `${questions.value[currentQuestionIndex.value]} ${
    optionsData.value[currentQuestionIndex.value][answer - 1]
  } `;
  console.log(answerMessage.value);

  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
  } else {
    showResult();
  }
};

// 결과를 API로 보내기
const showResult = async () => {
  isLoading.value = true;
  try {
    const expiry = sessionStorage.getItem("expiry")
    const currentTime = new Date().getTime()
    if (currentTime > expiry) {
      sessionStorage.clear()
      useRou.push("/")
    } else {
      const form = new FormData()
      form.append("prompt", answerMessage.value)
      const response = await api.post("/chat/team", form);
      if (response.status === 200) {
        setTimeout(() => {
          resultMessage.value = response.data;
          console.log(resultMessage.value);
          isResultVisible.value = true;
          isLoading.value = false;
        }, 5000);
      } else {
        await showAlert("오류발생", "다시 시도해주세요", "error");
        isLoading.value = false;
      }
    }
  } catch (error) {
    console.error("Error fetching results:", error);
    await showAlert("오류발생", "다시 시도해주세요", "error");
    isLoading.value = false;
  }
};

// 이전 페이지로 이동
const goBack = () => {
  router.push("/favorite"); // 좋아하는 팀 설정 페이지 경로
};
</script>

<template>
  <div class="container">
    <div class="box">
      <header class="header">
        <img
          src="@/assets/everymatch_w.png"
          alt="EVERYMATCH Logo"
          class="logo"
        />
        <p>당신에게 맞는 팀을 찾아드릴게요!</p>
      </header>

      <!-- 로딩 상태 표시 -->
      <div v-if="isLoading" class="loading">
        <div class="spinner"></div>
        <p>결과를 가져오는 중입니다...</p>
        <p>잠시만 기다려 주세요!</p>
      </div>

      <!-- 질문 및 결과 표시 -->
      <div v-else>
        <div class="content">
          <div v-if="!isResultVisible">
            <div class="progress-bar">
              <div class="progress" :style="{ width: progressWidth }"></div>
            </div>
            <p class="progress-text">{{ progressText }}</p>
            <div class="question">
              {{ currentQuestionText }}
            </div>
            <div class="options">
              <div
                v-for="(option, index) in currentOptions"
                :key="index"
                class="option"
                @click="nextQuestion(index + 1)"
              >
                {{ option }}
              </div>
            </div>
          </div>
          <div v-else>
            <h2>추천 결과</h2>
            <p class="result-message">{{ resultMessage }}</p>
            <button @click="goBack">바로 팀 등록하기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  background-color: #f9f9f9;
  min-height: 100vh;
  max-width: 100%;
  box-sizing: border-box;
  overflow: auto;
}

.box {
  max-width: 500px;
  border-radius: 12px;
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
}

.header {
  background-color: #ec493a;
  color: white;
  padding: 20px 30px;
  text-align: center;
  width: 100%;
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
}

.header img {
  margin: 10px;
  max-width: 100%;
}

.header p {
  font-size: 1rem;
}

.progress-bar {
  height: 5px;
  width: 100%;
  background-color: #f0f0f0;
}

.progress {
  height: 100%;
  background-color: #ec493a;
  transition: width 0.4s ease;
}

.content {
  padding: 30px;
  max-width: 100%;
  text-align: center;
}

.question,
h2 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
}

.options {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: center;
}

.option {
  background: #f4f4f9;
  border: 2px solid #eaeaea;
  border-radius: 12px;
  padding: 15px 20px;
  width: calc(50% - 10px);
  text-align: center;
  font-size: 1rem;
  font-weight: 500;
  color: #333;
  cursor: pointer;
  transition: all 0.3s ease;
}

.option:hover {
  background: #ec493a;
  color: white;
  border-color: #ec493a;
}

.progress-text {
  padding-top: 20px;
  color: #777;
}

.loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 30px;
}

.spinner {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #ec493a;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin: 30px;
}

.spinner p {
  padding: 0;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.result-message {
  font-size: 1.2rem;
  margin: 20px 0;
  color: #333;
}

button {
  background-color: #ec493a;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #d84132;
}
</style>
