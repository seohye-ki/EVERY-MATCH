<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import showAlert from "@/utils/swal";

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

const useRout = useRouter();

const userId = ref("");
const nickname = ref("");
const email = ref("");
const img = ref("");
const favoriteTeams = ref([]);
const isModalOpen = ref(false);
const choose = ref(false);
// 사용자 정보 불러오기
onMounted(async () => {
  try {
    const expiry = sessionStorage.getItem("expiry");
    const currentTime = new Date().getTime();
    if (currentTime > expiry) {
      sessionStorage.clear();
      useRout.push("/");
    } else {
      const response = await api.get("/user");
      userId.value = response.data.userId;
      nickname.value = response.data.nickname;
      email.value = response.data.email;
      img.value = response.data.userImg;
      const favorite = await api.get("/favorite");
      favoriteTeams.value = favorite.data.favoriteTeams;
      choose.value = true;
    }
  } catch (error) {
    console.error("Failed to fetch user data:", error);
  }
});

// 정보 수정
const changeInfo = async () => {
  try {
    const form = new FormData();
    form.append("nickname", nickname.value);
    form.append("email", email.value);
    form.append("userImg", img.value);
    const response = await api.put("/user", form);

    if (response.status === 200) {
      sessionStorage.setItem("nickname", nickname.value);
      sessionStorage.setItem("userImg", img.value);
      await showAlert("정보가 성공적으로 수정되었습니다.");
      useRout.push("/main");
    }
  } catch (error) {
    console.error("Failed to update user data:", error);
    await showAlert("정보 수정 중 문제가 발생했습니다.");
  }
};

// 비밀번호 변경
const changePW = () => {
  useRout.push("./changePW");
};

// 취소
const cancel = () => {
  useRout.push("/main");
};

// 회원 탈퇴
const deleteAccount = async () => {
  if (confirm("정말로 회원 탈퇴를 진행하시겠습니까?")) {
    try {
      await api.delete("/user");
      await showAlert("회원 탈퇴가 완료되었습니다.");
      sessionStorage.clear();
      useRout.push("/login");
    } catch (error) {
      console.error("Failed to delete user:", error);
      await showAlert("회원 탈퇴 중 문제가 발생했습니다.");
    }
  }
};

const selectTeamImage = (teamImg) => {
  img.value = teamImg; // 선택한 팀 이미지를 프로필 이미지로 설정
  isModalOpen.value = false; // 모달 닫기
};

const openModal = () => {
  if (favoriteTeams.value.length === 0) {
    alert("좋아하는 팀이 없습니다!");
  } else {
    isModalOpen.value = true;
  }
};

const closeModal = () => {
  isModalOpen.value = false;
};
</script>
<template>
  <div class="mypage-container">
    <!-- 로고 -->
    <header class="header">
      <img
        src="/src/assets/EVERYMATCH.png"
        alt="EVERYMATCH Logo"
        class="logo"
      />
    </header>

    <!-- 마이페이지 컨텐츠 -->
    <div class="content">
      <!-- 왼쪽 섹션 -->
      <div class="left-section">
        <div class="change-con">
          <div class="avatar">
            <img
              v-if="choose"
              :src="'/src/assets/imgs/' + img"
              onerror="this.onerror=null; this.src='/src/assets/icons/user.png';"
              alt="Image not found"
            />
          </div>
          <button class="delete-account-button" @click="openModal">
            팀 이미지 선택
          </button>
        </div>
        <button class="delete-account-button" @click="deleteAccount">
          회원탈퇴
        </button>
      </div>

      <!-- 오른쪽 섹션 -->
      <div class="right-section">
        <h2 class="title">마이페이지</h2>

        <!-- 사용자 정보 -->
        <div class="form-group">
          <label for="userId">ID</label>
          <input id="userId" type="text" :value="userId" readonly />
        </div>
        <div class="form-group">
          <label for="nickname">Nickname</label>
          <input
            id="nickname"
            :value="nickname"
            @input="(event) => (nickname = event.target.value)"
            type="text"
          />
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input id="email" v-model="email" type="email" />
        </div>
        <button class="change-password-button" @click="changePW">
          비밀번호 변경
        </button>
        <!-- 버튼 그룹 -->
        <div class="button-group">
          <button class="cancel-button" @click="cancel">X 닫기</button>
          <button class="update-button" @click="changeInfo">정보 수정</button>
        </div>
      </div>
    </div>
    <div v-if="isModalOpen" class="modal-wrap">
      <div class="modal-container">
        <span class="close" @click="closeModal">&times;</span>
        <h3>팀 이미지를 선택하세요</h3>
        <div class="team-images">
          <div v-for="team in favoriteTeams" :key="team.id" class="team-image">
            <img
              :src="'/src/assets/imgs/' + team.teamLogo"
              alt="Team Image"
              @click="selectTeamImage(team.teamLogo)"
              style="object-fit: contain; max-width: 80px; max-height: 80px"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 부모 컨테이너 */
.mypage-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #f9f9f9;
  min-height: 100vh;
  max-width: 100%;
  box-sizing: border-box;
}

/* 헤더 섹션 */
.header {
  width: 95%;
  padding: 10px 0px 0px 0px;
}

.logo {
  width: 230px;
}

/* 콘텐츠 섹션 */
.content {
  display: flex;
  width: 95%;
  height: calc(100vh - 150px);
  margin-top: 30px;
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

/* 왼쪽 섹션 */
.left-section {
  background-color: #de7268;
  width: 250px;
  padding: 40px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
}
.avatar {
  margin-bottom: 20px;
}
.avatar img {
  max-width: 80px;
  max-width: 80px;
  border-radius: 0%;
}

.delete-account-button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #ffffff;
  color: #da4537;
  font-size: 14px;
  font-weight: bold;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.delete-account-button:hover {
  background-color: #f0f0f0;
  color: #c44130;
}

/* 오른쪽 섹션 */
.right-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 40px;
  justify-content: space-between;
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 30px;
}

/* 폼 그룹 */
.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 8px;
}

input {
  width: 80%;
  max-width: 600px;
  padding: 12px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 6px;
  box-sizing: border-box;
}

input#userId {
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  color: #888;
  cursor: not-allowed;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: auto;
  align-self: flex-end;
}

.cancel-button {
  padding: 10px 20px;
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

.update-button {
  padding: 10px 20px;
  background-color: #de7268;
  color: #ffffff;
  font-size: 14px;
  font-weight: bold;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.update-button:hover {
  background-color: #cd685e;
}

/* 비밀번호 변경 버튼 */
.change-password-button {
  padding: 12px 20px;
  color: #555;
  font-size: 14px;
  font-weight: bold;
  border: 1px solid #555;
  border-radius: 6px;
  cursor: pointer;
  width: 140px;
  transition: background-color 0.3s ease;
}

.change-password-button:hover {
  background-color: #e0e0e0;
}
.modal-wrap {
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
}
/* modal or popup */
.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 550px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
}
.change-con {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.team-images {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}
</style>
