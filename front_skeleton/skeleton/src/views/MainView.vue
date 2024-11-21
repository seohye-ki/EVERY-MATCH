<script setup>
import { watch, ref, onBeforeMount } from 'vue'
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',  
});


api.interceptors.request.use(
  (config) => {
    // sessionStorage에서 JWT 토큰 가져오기
    const token = sessionStorage.getItem('access_token');
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

onBeforeMount(async () => {
  try {
    const response = await api.get('/match/schedule/user001')
    events.value = response.data
    can.value = true
  } catch (error) {
    console.error('Error fetching events:', error)
  }
});


const calendarOptions = ref({
    plugins: [dayGridPlugin],
    initialView: 'dayGridMonth',
    weekends: true,
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
    }
})

// 날짜별 이벤트 개수를 반환하는 메소드
const getEventCount = (dateStr) => {
  return events.value.filter(event => event.date === dateStr).length
}

// 날짜 객체를 'YYYY-MM-DD' 형식의 문자열로 변환하는 함수
const formatDate = (date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}


</script>

<template>
<main>
    <div>
        <img src="@/assets/SMALL.png"/>
        <div>
            <a href="myPage/pw">마이페이지</a>
        </div>
        <div>
          <a href="favorite">Favorite Team</a> 
        </div>
        <div>
            Logout
        </div>
    </div>
    <FullCalendar v-if="can" :options="calendarOptions" />
    <div>
        <span>
            안녕
        </span>
    </div>
</main>
</template>

<style scoped>
main {
    display: flex;
    flex-direction: row;
}
</style>
