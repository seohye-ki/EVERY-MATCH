<script setup>
import Team from "@/components/Team.vue";
import favorite from "@/components/Favorite.vue";
import { ref, onBeforeMount } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import showAlert from "@/utils/swal";

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

onBeforeMount(async () => {
  try {
    const expiry = sessionStorage.getItem("expiry");
    const currentTime = new Date().getTime();
    if (currentTime > expiry) {
      sessionStorage.clear();
      useRout.push("/");
    } else {
      const response = await api.get("/favorite");
      teams.value = response.data.allTeams;
      showTeams.value = teams.value.filter((team) => team.sportName === "야구");
      favoriteTeams.value = response.data.favoriteTeams;
    }
  } catch (error) {
    console.error("Error fetching events:", error);
  }
  console.log(showTeams);
});

const useRout = useRouter();
const teams = ref();
const showTeams = ref();
const favoriteTeams = ref([]);

const deleteTeam = async (teamId) => {
  favoriteTeams.value.splice(
    favoriteTeams.value.findIndex((team) => team.teamId === teamId),
    1
  );
};

const toggleTeamSelection = async (teamId) => {
  const teamExists = favoriteTeams.value.some((team) => team.teamId == teamId);

  if (!teamExists) {
    const teamToAdd = teams.value.find((team) => team.teamId === teamId);
    favoriteTeams.value.push(teamToAdd);
  } else {
    favoriteTeams.value.splice(
      favoriteTeams.value.findIndex((team) => team.teamId === teamId),
      1
    );
  }
};

const storage = async () => {
  const Ids = favoriteTeams.value.map((team) => team.teamId);
  console.log(Ids);
  try {
    const response = await api.put("/favorite", Ids);
    if (response.status === 200) {
      await showAlert("저장완료", null, "success");
      useRout.push("main");
    } else {
      await showAlert("오류발생", "다시 시도해주세요", "error");
    }
  } catch (error) {
    console.error("Error fetching events:", error);
  }
};

const activeFilter = ref("baseball");

const showAll = () => {
  activeFilter.value = "all";
  showTeams.value = teams.value;
};

const showBaseball = () => {
  activeFilter.value = "baseball";
  showTeams.value = teams.value.filter((team) => team.sportName === "야구");
};

const showSoccer = () => {
  activeFilter.value = "soccer";
  showTeams.value = teams.value.filter((team) => team.sportName === "축구");
};

const showBasketball = () => {
  activeFilter.value = "basketball";
  showTeams.value = teams.value.filter((team) => team.sportName === "농구");
};

const showVolleyball = () => {
  activeFilter.value = "volleyball";
  showTeams.value = teams.value.filter((team) => team.sportName === "배구");
};

const cancel = () => {
  useRout.push("main");
};

const goTest = () => {
  useRou.push("/test");
};
</script>
<template>
  <div class="favorite-container">
    <!-- 헤더 -->
    <header class="header">
      <img src="@/assets/EVERYMATCH.png" alt="EVERYMATCH Logo" class="logo" />
    </header>

    <!-- 콘텐츠 영역 -->
    <main class="content">
      <!-- 왼쪽 팀 목록 -->
      <div class="team-selection">
        <!-- 필터 버튼 -->
        <div class="filter-buttons">
          <button
            @click="showBaseball"
            class="filter-button"
            :class="{
              active: activeFilter === 'baseball' || activeFilter === 'all',
            }"
          >
            야구
          </button>
          <button
            @click="showSoccer"
            class="filter-button"
            :class="{
              active: activeFilter === 'soccer' || activeFilter === 'all',
            }"
          >
            축구
          </button>
          <button
            @click="showBasketball"
            class="filter-button"
            :class="{
              active: activeFilter === 'basketball' || activeFilter === 'all',
            }"
          >
            농구
          </button>
          <button
            @click="showVolleyball"
            class="filter-button"
            :class="{
              active: activeFilter === 'volleyball' || activeFilter === 'all',
            }"
          >
            배구
          </button>
          <div class="sep">|</div>
          <button
            @click="showAll"
            class="filter-button"
            :class="{ active: activeFilter === 'all' }"
          >
            전체
          </button>
        </div>

        <!-- 팀 목록 -->
        <div class="team-list">
          <Team
            class="team-logo"
            v-for="team in showTeams"
            :key="team.teamId"
            :teamId="team.teamId"
            :teamLogo="team.teamLogo"
            @click="toggleTeamSelection(team.teamId)"
            :class="{
              selected: favoriteTeams.some((fav) => fav.teamId === team.teamId),
            }"
          />
        </div>
      </div>

      <!-- 오른쪽 선택된 팀 -->
      <div class="favorite-selection">
        <h2 class="favorite-title">선택된 팀</h2>
        <div class="favorite-list">
          <div v-if="favoriteTeams.length === 0" class="empty-message">
            아직 선택된 팀이 없습니다.
          </div>
          <div
            class="favorite-item"
            v-for="team in favoriteTeams"
            :key="team.teamId"
          >
            <span>[{{ team.sportName }}] {{ team.teamName }}</span>
            <button @click="deleteTeam(team.teamId)" class="delete-button">
              ×
            </button>
          </div>
        </div>
        <!-- 버튼 그룹 -->
        <div class="button-group">
          <button @click="cancel" class="cancel-button">닫기</button>
          <button @click="storage" class="save-button">저장</button>
        </div>
        <div class="recommend-button">
          <a href="recommend">
            <img src="@/assets/icons/warning.png" alt="warning" />
            팀 추천을 받을 수 있어요.
          </a>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
/* 전체 컨테이너 */
.favorite-container {
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

/* 콘텐츠 */
.content {
  display: grid;
  grid-template-columns: 2fr 1fr; /* 왼쪽 팀 선택 영역, 오른쪽 즐겨찾기 */
  gap: 20px;
  width: 95%;
  background-color: #ffffff;
  height: calc(100vh - 150px);
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 다른 페이지와 동일한 shadow 적용 */
  overflow: hidden;
  margin-top: 30px;
}

/* 팀 선택 영역 */
.team-selection {
  display: flex;
  flex-direction: column;
  padding: 20px;
}

.sep {
  color: #de7268;
  font-size: 20px;
  padding-top: 5px;
}

/* 필터 버튼 */
.filter-buttons {
  display: flex;
  justify-content: flex-start;
  gap: 10px;
  margin-bottom: 20px;
}

.filter-button {
  background-color: transparent; /* 기본 배경 없음 */
  border: 2px solid #de7268; /* 기본 테두리 */
  color: #de7268; /* 기본 텍스트 색상 */
  border-radius: 6px;
  padding: 8px 16px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.filter-button.active {
  background-color: #de7268; /* 선택된 버튼 배경 */
  color: #ffffff; /* 선택된 버튼 텍스트 색상 */
}

.filter-button:hover {
  background-color: #de7268;
  color: #ffffff;
}

/* 팀 목록 */
.team-list {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  max-height: 600px;
  overflow-y: auto; /* 스크롤 활성화 */
  padding: 10px;
  border-radius: 8px;
}

.team-logo {
  width: 100px;
  height: 100px;
  cursor: pointer;
  position: relative;
  border-radius: 8px;
  overflow: hidden;
  border: 2px solid transparent; /* 선택되지 않은 상태 */
  transition: transform 0.2s ease, border-color 0.2s ease;
}

.team-logo.selected {
  border-color: #de7268; /* 선택된 팀 강조 */
  transform: scale(1.1);
}

.team-logo img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

/* 오른쪽 즐겨찾기 */
.favorite-selection {
  display: flex;
  flex-direction: column;
  padding: 20px;
  background-color: #de7268;
  border-radius: 12px;
  color: #ffffff;
  /* align-items: stretch; */
  height: 100%;
}

.favorite-title {
  font-size: 18px;
  font-weight: bold;
  margin-top: 10px;
  margin-bottom: 30px;
}

.favorite-list {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  max-height: 560px;
  gap: 10px;
  overflow-y: auto; /* 스크롤 활성화 */
  padding: 0;
  margin: 0 0 10px;
}

.favorite-item {
  background-color: #ffffff;
  color: #333;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-radius: 8px;
  font-size: 14px;
  font-weight: bold;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.empty-message {
  text-align: center;
  font-size: 14px;
  color: #fff;
  margin: 20px 0;
}

.delete-button {
  background-color: #de7268;
  color: #ffffff;
  border: none;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.delete-button:hover {
  background-color: #cd685e;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  justify-content: space-between;
  gap: 10px;
  margin-top: auto;
}

.cancel-button,
.save-button {
  flex: 1;
  padding: 8px 16px; /* 크기 조정 */
  font-size: 14px;
  font-weight: bold;
  border: 2px solid #ffffff; /* 흰색 테두리 */
  border-radius: 6px;
  background-color: #ffffff; /* 버튼 배경 흰색 */
  color: #de7268; /* 텍스트 색상 */
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.cancel-button:hover,
.save-button:hover {
  background-color: #f0f0f0;
  color: #cd685e;
}

.recommend-button {
  margin-top: 10px;
  text-align: center;
}

.recommend-button a {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 14px;
  gap: 10px;
  text-decoration: none;
}

.recommend-button:hover a {
  text-decoration: underline;
}

.team-list::-webkit-scrollbar {
  width: 8px;
}

.team-list::-webkit-scrollbar-thumb {
  background-color: #de7268;
  border-radius: 4px;
}

.team-list::-webkit-scrollbar-track {
  background-color: #f0f0f0;
}
</style>
