<script setup>
import { useRouter, RouterView } from 'vue-router'
import { useUserStore } from '@/stores/counter'
import { ref } from 'vue'


import LeftIcon from '@/components/LeftIcon.vue';

const useRou = useRouter()
const userStore = useUserStore()
const id = ref('')
const prevStep = () => {
    useRou.push('/regist/nick') 
}

const nextStep = () => {
    userStore.setId(id.value)
    useRou.push('/regist/pw')  
}
</script>

<template>
<form @submit.prevent="nextStep">
    <div>
        <div>
            <label for="regist_id">안바뀜</label>
        </div>
        <input id="regist_id" type="text" v-model="id" placeholder="아이디를 입력하세요" maxlength="10"/>
    </div>
    <button type="submit">다음</button>
</form>
<form @submit.prevent="prevStep">
    <button type="submit">이전</button>
</form>
    

</template>

<style scoped>

</style>
