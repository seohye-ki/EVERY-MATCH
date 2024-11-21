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
    alert('팀 삭제'+teamId)
    favoriteTeams.value.splice(favoriteTeams.value.findIndex(team => team.teamId === teamId), 1)
}

const plusTeam = async (teamId) => {
    const teamExists = favoriteTeams.value.some(team => team.teamId == teamId)
    if (!teamExists) {
        alert('팀 추가'+teamId)
        const teamToAdd = teams.value.find(team => team.teamId === teamId)
        favoriteTeams.value.push(teamToAdd)
    } else {
        alert('이미 있음')
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

<fieldset>
    <div>
        <button @click="showAll"> 전체 </button>
        <button @click="showBaseball"> 야구 </button>
        <button @click="showSoccer"> 축구 </button>
    </div>
    <div>
        <team v-for="team in showTeams" :team-id="team.teamId" :team-logo="team.teamLogo" @plus-favorite="plusTeam"/>
    </div>
</fieldset>
<fieldset>
    
</fieldset>
    <favorite v-for="team in favoriteTeams" :team-id="team.teamId" :team-name="team.teamName" @delete-team="deleteTeam"/>
    <div>
        <button @click="cancel">취소</button>
        <button @click="storage">저장</button>
    </div>
</template>

<style scoped>

</style>
