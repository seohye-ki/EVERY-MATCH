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

      <div class="progress-bar">
        <div class="progress" :style="{ width: progressWidth }"></div>
      </div>

      <div class="content">
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
    </div>
  </div>
</template>

<script>
import { ref, computed } from "vue";

const answerMessage = ref("");

export default {
  name: "EveryMatch",
  setup() {
    const questions = ref([
      "가장 좋아하는 스포츠는 무엇인가요?",
      "어떤 경기 스타일을 더 좋아하시나요?",
      "응원할 때 당신은 어떤 스타일인가요?",
      "특정 지역 팀을 응원하고 싶으신가요?",
      "팀의 어떤 점이 중요하다고 생각하시나요?",
    ]);

    const optionsData = ref([
      ["⚽️ 축구", "⚾️ 야구", "🏀 농구", "🏐 배구"],
      [
        "🔥 공격적인 플레이",
        "🛡️ 안정적인 수비",
        "🎭 화려한 기술",
        "🏃‍♂️ 빠른 템포",
      ],
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
    const progressWidth = computed(
      () =>
        `${((currentQuestionIndex.value + 1) / questions.value.length) * 100}%`
    );

    const progressText = computed(
      () => `질문 ${currentQuestionIndex.value + 1}/${questions.value.length}`
    );

    const currentQuestionText = computed(
      () => questions.value[currentQuestionIndex.value]
    );

    const currentOptions = computed(
      () => optionsData.value[currentQuestionIndex.value]
    );

    const nextQuestion = (answer) => {
      answerMessage.value += `질문 "${
        questions.value[currentQuestionIndex.value]
      }"에 대한 대답은 ${
        optionsData.value[currentQuestionIndex.value][answer - 1]
      }`;
      console.log(answerMessage.value);
      if (currentQuestionIndex.value < questions.value.length - 1) {
        currentQuestionIndex.value++;
      } else {
        showResult();
      }
    };

    // resultmessage를 api로 보내기
    const showResult = () => {
      currentQuestionIndex.value = -1;
      setTimeout(() => {
        resultMessage.value = "추천 팀은 FC서울입니다!";
        isResultVisible.value = true;
      }, 2000);
    };

    const restart = () => {
      currentQuestionIndex.value = 0;
      isResultVisible.value = false;
      resultMessage.value = "";
    };

    const isResultVisible = ref(false);
    const resultMessage = ref("");

    return {
      currentQuestionText,
      currentOptions,
      nextQuestion,
      progressText,
      progressWidth,
      restart,
      isResultVisible,
      resultMessage,
    };
  },
};
</script>

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
  max-width: 450px;
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
  box-sizing: border-box;
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
  --bs-progress-border-radius: 0;
  transition: width 0.4s ease;
}

.content {
  padding: 30px;
  max-width: 100%;
  text-align: center;
}

.question {
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
  margin-top: 10px;
  color: #777;
}

.footer {
  text-align: center;
  padding: 20px;
  font-size: 0.9rem;
  color: #777;
}

.footer button {
  background-color: #ff6b6b;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.footer button:hover {
  background-color: #e05656;
}
</style>
