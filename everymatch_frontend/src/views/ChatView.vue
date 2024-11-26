<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

api.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem("Authorization");
    if (token) {
      config.headers["Authorization"] = `${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

const nickname = sessionStorage.getItem("nickname") || "유저"; // 닉네임 가져오기
const messages = ref([]); // 채팅 메시지 저장
const newMessage = ref(""); // 새로운 메시지 입력값
const useRou = useRouter();

// 메시지 전송 로직
const sendMessage = async () => {
  if (newMessage.value.trim() === "") return; // 빈 메시지는 무시
  const message = {
    answer: false,
    sender: nickname,
    text: newMessage.value,
    timestamp: new Date().getTime(),
  };
  try {
    const expiry = sessionStorage.getItem("expiry");
    const currentTime = new Date().getTime();
    if (currentTime > expiry) {
      sessionStorage.clear();
      useRou.push("/");
    } else {
      const form = new FormData();
      form.append("prompt", message.text);
      const response = await api.post("/chat/ask", form); // 가상의 API
      if (response.status === 200) {
        const resMessage = {
          answer: true,
          sender: "파트너",
          text: response.data
            .replaceAll("*", "")
            .replaceAll(". ", ".\n")
            .replaceAll("! ", "!\n")
            .replaceAll(", ", ",\n"),
          timestamp: new Date().getTime(),
        };
        messages.value.push(message); // 성공 시 메시지 추가
        messages.value.push(resMessage);
        newMessage.value = ""; // 입력창 초기화
      }
    }
  } catch (error) {
    console.error("Error sending message:", error);
  }
};

const logout = () => {
  sessionStorage.clear();
  useRou.push("/login");
};

const goMain = () => {
  useRou.push("/");
};
const updateMessage = (event) => {
  newMessage.value = event.target.value;
};
</script>

<template>
  <div class="main-layout">
    <!-- 헤더 -->
    <header class="header">
      <img src="@/assets/EVERYMATCH.png" alt="EVERYMATCH Logo" class="logo" />
    </header>

    <!-- 메인 콘텐츠 -->
    <main class="content-container">
      <!-- 왼쪽 사이드 메뉴 -->
      <div class="left-section">
        <div class="user-img">
          <img src="@/assets/icons/user.png" alt="userImg" class="user" />
        </div>
        <div class="left-menu">
          <div class="menu-item">
            <a href="myPage/pw">
              <img
                src="@/assets/icons/settings.png"
                alt="My Page Icon"
                class="icon"
              />
              My Page
            </a>
          </div>
          <div class="menu-item">
            <a href="favorite">
              <img
                src="@/assets/icons/like.png"
                alt="Favorite Team Icon"
                class="icon"
              />
              Favorite Team
            </a>
          </div>
          <div class="menu-item" @click="logout">
            <a href="#">
              <img
                src="@/assets/icons/logout.png"
                alt="Logout Icon"
                class="icon"
              />
              Log Out
            </a>
          </div>
        </div>
      </div>

      <!-- 채팅 섹션 -->
      <div class="chat-section">
        <p class="title">
          <span>{{ nickname }}</span
          >님! 무엇이 궁금하신가요?🤔
        </p>

        <!-- 채팅 메시지 리스트 -->
        <div class="chat-messages">
          <div
            v-for="(message, index) in messages"
            :key="index"
            class="message"
            :class="{ 'answer-message': message.answer === true }"
          >
            <p
              class="sender"
              :class="{ 'answer-sender': message.answer === true }"
            >
              {{ message.sender }}
            </p>
            <p class="text" :class="{ 'answer-text': message.answer === true }">
              {{ message.text }}
            </p>
            <p
              class="timestamp"
              :class="{ 'answer-timestamp': message.answer === true }"
            >
              {{ new Date(message.timestamp).toLocaleTimeString() }}
            </p>
          </div>
        </div>

        <!-- 채팅 입력창 -->
        <div class="chat-input-container">
          <input
            :value="newMessage"
            @input="updateMessage"
            @keyup.enter="sendMessage"
            type="text"
            placeholder="메시지를 입력하세요..."
            class="chat-input"
          />
          <button @click="sendMessage" class="send-button">보내기</button>
        </div>
      </div>
    </main>
    <div class="chat-button" @click="goMain">🥰</div>
  </div>
</template>

<style scoped>
/* 전체 컨테이너 */
.main-layout {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #f9f9f9;
  min-height: 100vh;
  max-width: 100%;
  box-sizing: border-box;
}

/* 헤더 */
.header {
  width: 95%;
  padding: 10px 0px 0px 0px;
}

.logo {
  width: 230px;
}

/* 콘텐츠 컨테이너 */
.content-container {
  display: grid;
  grid-template-columns: 130px auto; /* 왼쪽 메뉴, 채팅 영역 */
  gap: 20px;
  width: 95%;
  margin-top: 30px;
  height: calc(100vh - 150px); /* 헤더 높이를 뺀 나머지 높이 */
  box-sizing: border-box;
}

/* 왼쪽 메뉴 */
.left-section {
  background-color: #de7268;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 12px;
  justify-content: space-between;
}

.user {
  margin-top: 30px;
}

.left-menu {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}

.menu-item {
  display: flex;
  align-items: center;
  flex-direction: column;
  margin-bottom: 25px;
  color: #fff;
  text-decoration: none;
}

.menu-item img {
  width: 30px;
  margin-bottom: 3px;
}

.menu-item a {
  display: flex;
  align-items: center;
  flex-direction: column;
  color: #fff;
  font-size: 14px;
  text-decoration: none;
}

.menu-item:hover a {
  text-decoration: underline;
}

span {
  font-size: 20px;
  font-weight: bold;
}

.title {
  display: inline-block;
  border-radius: 12px;
  border: #de7268 solid 2px;
  padding: 15px;
  margin-bottom: 30px;
}

/* 채팅 섹션 */
.chat-section {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: calc(100vh - 150px);
}

/* 채팅 메시지 리스트 */
.chat-messages {
  flex-grow: 1;
  overflow-y: auto;
  margin-bottom: 20px;
  padding-right: 15px;
}

.message {
  margin-bottom: 10px;
}

.sender {
  font-weight: bold;
  color: #de7268;
}

.text {
  margin: 5px 0;
  background-color: #de7268;
  color: #ffffff;
  padding: 10px;
  border-radius: 12px;
  width: fit-content;
}

.timestamp {
  font-size: 12px;
  color: #999;
  margin: 5px 0;
  text-align: left;
}

.answer-message {
  text-align: right;
}

.answer-sender {
  text-align: right;
}

.answer-text {
  background-color: #ffffff;
  border: #de7268 solid 2px;
  color: #666;
  margin-left: auto;
}

.answer-timestamp {
  text-align: right;
}

.chat-messages::-webkit-scrollbar {
  width: 8px;
}

.chat-messages::-webkit-scrollbar-thumb {
  background-color: #de7268;
  border-radius: 4px;
}

.chat-messages::-webkit-scrollbar-track {
  background-color: #f0f0f0;
}

/* 채팅 입력창 */
.chat-input-container {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.chat-input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
}

.send-button {
  background-color: #de7268;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 6px;
  cursor: pointer;
}

.send-button:hover {
  background-color: #c65a50;
}

.chat-button {
  position: fixed;
  bottom: 40px;
  right: 20px;
  width: 70px;
  height: 70px;
  background-color: #de7268;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  font-size: 36px;
  color: #fff;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}
</style>
