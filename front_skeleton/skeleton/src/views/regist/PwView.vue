<script setup>
import { useRouter, RouterView } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'


import LeftIcon from '@/components/LeftIcon.vue';

const useRou = useRouter()
const userStore = useUserStore()
const password = ref('')
const prevStep = () => {
    useRou.push('/regist/id') 
}

const nextStep = () => {
    userStore.setPassword(password.value)
    useRou.push('/regist/email')  
}
</script>

<template>

<form @submit.prevent="nextStep">
    <div>
    <div>
            <label for="regist_pw">비번</label>
        </div>
        <input id="regist_pw" type="password" v-model="password" placeholder="아이디를 입력하세요" maxlength="10"/>
    </div>    
    <button type="submit">다음</button>
</form>
<form @submit.prevent="prevStep">
    <button type="submit">이전</button>
</form>

</template>

<style scoped>

</style>
