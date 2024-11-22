<script setup>
import { watch, ref, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import Match from '@/components/Match.vue'
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

const events = ref([
    {matchId: 1, date: '2024-08-10', time: '18:00:00', homeTeamId: 1, awayTeamId: 2},
])

const can = ref(false)
const selectedDate=ref()
const nickname=sessionStorage.getItem('nickname')
const matchs=ref()
const useRou=useRouter()

onBeforeMount(async () => {
  try {
    const response = await api.get('/match/schedule')
    
    if(response.data.length !== 0) {
      events.value = response.data
      matchs.value = events.value.filter(event => event.date===selectedDate.value)
    } 

    can.value = true
  } catch (error) {
    console.error('Error fetching events:', error)
  }
});


const calendarOptions = ref({
    plugins: [dayGridPlugin, interactionPlugin],
    initialView: 'dayGridMonth',
    weekends: true,
    selectable: true,
    // 날짜 셀이 렌더링된 후, 이벤트 개수에 따라 색상을 변경
    dayCellDidMount: (info) => {
        // 날짜별 이벤트 개수 계산
        const eventCount = getEventCount(formatDate(info.date))
        // 색상의 강도를 이벤트 개수에 따라 조정
        let color = `rgba(0, 0, 0, 0)`; 

        if (eventCount > 0) {
        const intensity = Math.min(eventCount * 0.2, 1); // 이벤트 개수에 따라 색의 진하기
        color = `rgba(255, 99, 132, ${intensity})`; 
        }

        info.el.style.backgroundColor = color;
    },
    dateClick: (info) => {
      selectedDate.value = formatDate(info.date)
    }
})

watch(selectedDate, (newSelectedDate) => {
  matchs.value = events.value.filter(event => event.date === newSelectedDate)
  useRou.push('')
});

// 날짜별 이벤트 개수를 반환하는 메소드
const getEventCount = (dateStr) => {
  if (event.value === null) {
    return 0  
  }
  return events.value.filter(event => event.date === dateStr).length
  
}

// 날짜 객체를 'YYYY-MM-DD' 형식의 문자열로 변환하는 함수
const formatDate = (date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}
selectedDate.value = formatDate(new Date())

const logout = () => {
  sessionStorage.clear()
}

</script>
<template>
	<div class="main-layout">
	  <!-- 헤더 -->
	  <header class="header">
		<img src="@/assets/EVERYMATCH.png" alt="EVERYMATCH Logo" class="logo" />
	  </header>
  
	  <main class="content-container">
		<!-- 왼쪽 사이드 메뉴 -->
		<div class="left-section">
			<div class="user-img">
			  <img src="@/assets/icons/user.png" alt="userImg" class="user" />
			</div>
			<div class="left-menu">
			  <div class="menu-item">
				<a href="myPage/pw">
				  <img src="@/assets/icons/settings.png" alt="My Page Icon" class="icon" />
				  My Page
				</a>
			  </div>
			  <div class="menu-item">
				<a href="favorite">
				<img src="@/assets/icons/like.png" alt="Favorite Team Icon" class="icon" />
				  Favorite Team
			    </a>
			  </div>
			  <div class="menu-item" @click="logout">
				<a href="#">
				  <img src="@/assets/icons/logout.png" alt="Logout Icon" class="icon" />
				  Log Out
				</a>
			  </div>
			</div>

		</div>
  
		<!-- 중앙 달력 -->
		<div class="calendar-section">
		  <p><span>{{ nickname }}</span>님! 오늘도 멋진 경기들이 기다리고 있어요! 🔥</p>
		  <FullCalendar v-if="can" :options="calendarOptions" />
		</div>
  
		<!-- 오른쪽 경기 리스트 -->
		<div class="match-list-section">
		  <h2 class="match-list-title">경기 리스트</h2>
      <Match v-if="matchs && matchs.length  > 0" v-for="match in matchs" :key="match.matchId" :match="match"/>
		  <div v-else class="no-match">선택한 날짜에 경기가 없습니다.</div>
		</div>
	  </main>
	</div>
</template>
  
<style>
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
  grid-template-columns: 130px auto 250px; /* 왼쪽 메뉴, 중앙 달력, 오른쪽 리스트 */
  gap: 20px;
  width: 95%;
  /* max-width: 1200px; */
  margin-top: 30px;
  height: calc(100vh - 200px); /* 헤더 높이를 뺀 나머지 높이 */
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

/* 중앙 달력 */
.calendar-section {
  background-color: #ffffff;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.calendar-section span {
  font-size: 20px;
  font-weight: bold;
}

.calendar-section p {
  border-radius: 12px;
  border: #de7268 solid 2px;
  padding: 15px;
  margin-bottom: 30px;
}

/* 오른쪽 경기 리스트 */
.match-list-section {
  background-color: #de7268;
  border-radius: 12px;
  padding: 20px;
  overflow-y: auto;
  color: #fff;
}

.match-list-title {
  font-size: 18px;
  font-weight: bold;
  margin-top: 10px;
  margin-bottom: 30px;
}

.match-item {
  background-color: #ffffff;
  color: #333;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.match-item .match-time {
  font-weight: bold;
  font-size: 16px;
  color: #de7268;
  margin-bottom: 5px;
}

.match-item .match-info {
  font-size: 14px;
  margin-bottom: 5px;
}

.match-item .match-location {
  font-size: 12px;
  color: #666;
}

.no-match {
  text-align: center;
  font-size: 14px;
  margin-top: 20px;
}

:root {
  --fc-neutral-text-color: #808080;
}
.fc-daygrid-day-number {
    text-decoration: none;
    color: black;
    font-weight: 800;
}
.fc .fc-daygrid-body-unbalanced .fc-daygrid-day-events {
    min-height: 0;
}
.fc .fc-daygrid-day-frame {
    align-content: center;
}
.fc .fc-daygrid-day-top {
    justify-content: center;
}
.fc .fc-col-header-cell-cushion {
    text-decoration: none;
    color: black;
}
.fc .fc-toolbar-title {
    font-weight: 900;
}
</style>