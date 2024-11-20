<script setup>
import Id from '@/components/Id.vue'
import Pw from '@/components/Pw.vue'
import CenterIcon from '@/components/CenterIcon.vue';

import {ref} from 'vue'
import axios from "axios"

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
  });

const id = ref()
const pw = ref()

const login = async () => {
    if (id.value && pw.value) {
        try {
            const formData = new FormData()
            formData.append('userId', id.value)
            formData.append('password', pw.value)
            const response = await api.post('/user/login', formData)

            if (response.data) {
                console.log('로그인 성공', response.data)
                router.push('/main')
            }
            else {
                alert('로그인 실패')
            }
        } catch (error) {
            console.error('로그인 오류')
            alert('로그인 오류')
        } 
    } else {
        alert('아이디 비밀번호 입력')
    }
}
</script>

<template>

<CenterIcon />
<form @submit.prevent="login">
    <fieldset>
        <Id v-model="id"/>
        <Pw v-model="pw"/>
        <button type="submit">Sign In</button>
        <div>
            <a href="./find">비밀번호 찾기</a>
            <a href="./regist/nick">회원가입</a>
        </div>
    </fieldset>
</form>

  
</template>

<style scoped>

</style>
