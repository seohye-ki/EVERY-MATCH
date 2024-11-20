<script setup>
import CenterIcon from '@/components/CenterIcon.vue';

import {ref} from 'vue'
import axios from "axios"
import { useRouter} from 'vue-router'

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
  });

const useRou = useRouter()

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
                sessionStorage.setItem('access_token', response.data['access-token'])
                useRou.push('/main')
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
  <div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card p-4 shadow" style="max-width: 400px; width: 100%;">
      <div class="text-center mb-4">
        <CenterIcon />
      </div>
      <form @submit.prevent="login">
        <div class="mb-3">
          <label for="id" class="form-label">ID</label>
          <input
            type="text"
            class="form-control"
            id="id"
            placeholder="아이디를 입력하세요"
            v-model="id"
            required
          />
        </div>
        <div class="mb-3">
          <label for="password" class="form-label">Password</label>
          <input
            type="password"
            class="form-control"
            id="password"
            placeholder="비밀번호를 입력하세요"
            v-model="pw"
            required
          />
        </div>
        <button type="submit" class="btn btn-danger w-100">Sign In</button>
      </form>
      <div class="d-flex justify-content-between mt-3">
        <a href="./find" class="text-decoration-none text-black-50">비밀번호 찾기</a>
        <a href="./regist/nick" class="text-decoration-none text-black-50">회원가입</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
body {
  background-color: #f9f9f9;
}
</style>
