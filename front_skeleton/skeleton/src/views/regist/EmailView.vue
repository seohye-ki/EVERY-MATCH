<script setup>
import { useRouter, RouterView } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'


import LeftIcon from '@/components/LeftIcon.vue';

const useRou = useRouter()
const userStore = useUserStore()
const email = ref('')
const prevStep = () => {
    useRou.push('/regist/id') 
}

const nextStep = () => {
    userStore.setEmail(email.value)
    userStore.submitRegist()
    useRou.push('/regist/fin')  
}
</script>

<template>

<form @submit.prevent="nextStep">
    <fieldset>
        <div>
        <div>
            <label for="regist_email">이메일</label>
        </div>
        <input id="regist_email" type="email" v-model="email" placeholder="아이디를 입력하세요" maxlength="10"/>
    </div>
    <button @submit="nextStep">다음</button>
    </fieldset>
</form>
<form @submit.prevent="prevStep">
    <button type="submit">이전</button>
</form>
</template>

<style scoped>

</style>
