<script setup>
import team from '@/components/Team.vue'
import favorite from '@/components/Favorite.vue'
import { ref, onBeforeMount, } from 'vue';
import { useRouter } from 'vue-router'
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',  
});

api.interceptors.request.use(
  (config) => {
    // sessionStorage에서 JWT 토큰 가져오기
    const token = sessionStorage.getItem('Authorization');
    // 토큰이 존재하면 Authorization 헤더에 추가
    if (token) {
      config.headers['Authorization'] = `${token}`;
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
    const response = await api.get('/favorite')
    teams.value = response.data.allTeams
    showTeams.value = teams.value
    favoriteTeams.value = response.data.favoriteTeams
  } catch (error) {
    console.error('Error fetching events:', error)
  }
  console.log(showTeams)
});


const useRout = useRouter()
const teams = ref()
const showTeams = ref()
const favoriteTeams = ref()


const deleteTeam = async (teamId) => {
    favoriteTeams.value.splice(favoriteTeams.value.findIndex(team => team.teamId === teamId), 1)
}

const plusTeam = async (teamId) => {
    const teamExists = favoriteTeams.value.some(team => team.teamId == teamId)
    if (!teamExists) {
        const teamToAdd = teams.value.find(team => team.teamId === teamId)
        favoriteTeams.value.push(teamToAdd)
    }
}

const storage = async () => {
    const Ids = favoriteTeams.value.map(team => team.teamId);
    console.log(Ids)
    try {
    const response = await api.put('/favorite', Ids)
    if (response.status === 200) {
        alert("완료")
        useRout.push("main")
    } else {
        alert("오류발생 ㅠㅠ 다시 시도해주세요")
    }

    } catch (error) {
        console.error('Error fetching events:', error)
    }
}

const showAll = () => {
    showTeams.value = teams.value
}
 
const showBaseball = () => {
    showTeams.value = teams.value.filter(team => team.sportName === "Baseball")
}

const showSoccer = () => {
    showTeams.value = teams.value.filter(team => team.sportName === "Soccer" || team.sportName === "Football")
}

const cancel = () => {
    useRout.push("main")
}
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
			  @click="showAll"
			  class="filter-button"
			  :class="{ active: showTeams.length === teams.length }"
			>
			  전체
			</button>
			<button
			  @click="showBaseball"
			  class="filter-button"
			  :class="{ active: showTeams.some(team => team.sportName === 'Baseball') }"
			>
			  야구
			</button>
			<button
			  @click="showSoccer"
			  class="filter-button"
			  :class="{ active: showTeams.some(team => team.sportName === 'Soccer' || team.sportName === 'Football') }"
			>
			  축구
			</button>
		  </div>
  
		  <!-- 팀 목록 -->
		  <div class="team-list">
			<div
			  class="team-logo"
			  v-for="team in showTeams"
			  :key="team.teamId"
			  @click="plusTeam(team.teamId)"
			  :class="{ selected: favoriteTeams.some(fav => fav.teamId === team.teamId) }"
			>
			  <img :src="`/src/assets/teams/${team.teamLogo}`" :alt="team.teamName" />
			</div>
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
				<span>{{ team.teamName }}</span>
				<button @click="deleteTeam(team.teamId)" class="delete-button">×</button>
			</div>
		  </div>
		  <!-- 버튼 그룹 -->
		  <div class="button-group">
			<button @click="cancel" class="cancel-button">닫기</button>
			<button @click="storage" class="save-button">저장</button>
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
  max-width: 1200px;
  background-color: #ffffff;
  height: calc(100vh - 200px);
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
  background-color: #de7268;
  padding: 20px;
  border-radius: 12px;
  color: #ffffff;
  display: flex;
  flex-direction: column;
  align-items: stretch;
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
  gap: 10px;
  overflow-y: auto; /* 스크롤 활성화 */
  padding: 0;
  margin: 0;
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
  margin-top: 10px;
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

.team-list::-webkit-scrollbar,
.favorite-list::-webkit-scrollbar {
  width: 8px;
}

.team-list::-webkit-scrollbar-thumb,
.favorite-list::-webkit-scrollbar-thumb {
  background-color: #de7268; /* 스크롤바 색상 */
  border-radius: 4px;
}

.team-list::-webkit-scrollbar-track,
.favorite-list::-webkit-scrollbar-track {
  background-color: #f0f0f0; /* 스크롤바 배경 */
}
</style>
