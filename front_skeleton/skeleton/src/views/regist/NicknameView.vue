<script setup>
import { useRouter, RouterView } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'


import LeftIcon from '@/components/LeftIcon.vue';

const useRou = useRouter()
const userStore = useUserStore()
const nickname = ref('')
const nextStep = () => {
    userStore.setNickname(nickname.value)
    useRou.push('/regist/id')  
}
</script>

<template>
<form @submit.prevent="nextStep">
    <fieldset>
        <div>
        <div>
            <label for="regist_nick">앞으로 이렇게 불러주세요!</label>
        </div>
        <input id="regist_nick" type="text" v-model="nickname" placeholder="아이디를 입력하세요" maxlength="10"/>
    </div>
    <button type="submit">전송</button>
    </fieldset>
</form>

</template>

<style scoped>

</style>
