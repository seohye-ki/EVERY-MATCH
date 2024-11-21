<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from "axios"
import showAlert from '@/utils/swal';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    timeout: 5000,
});

const router = useRouter()

const id = ref('')
const pw = ref('')

const login = async () => {
    if (!id.value || !pw.value) {
        await showAlert('입력 오류', '아이디와 비밀번호를 입력해주세요.', 'warning')
        return;
    }
    try {
        const formData = new FormData()
        formData.append('userId', id.value)
        formData.append('password', pw.value)

        const response = await api.post('/user/login', formData)

        if (response.status === 200) {
            console.log('로그인 성공', response.data)
            await showAlert('로그인 성공', '환영합니다!', 'success')
            sessionStorage.setItem("Authorization", response.data['access-token'])
            router.push('/main')
        }
    } catch (error) {
        if (error.response && error.response.status === 401) {
        	await showAlert('로그인 실패', '아이디 또는 비밀번호가 잘못되었습니다.', 'error');
		} else {
			await showAlert('서버 오류', '로그인 중 문제가 발생했습니다. 다시 시도해주세요.', 'error');
		}
		console.error('로그인 오류:', error);
    }
}
</script>

<template>
  <div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card p-4 shadow login-card">
      <div class="text-center mb-4">
        <img src="/src/assets/EVERYMATCH.png" alt="Logo" class="mx-auto logo" />
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
        <a href="./find" class="text-decoration-none text-muted">비밀번호 재발급</a>
        <a href="./regist/nick" class="text-decoration-none text-muted">회원가입</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
body {
  background-color: #f9f9f9;
}

.logo {
  max-width: 100%;
  height: auto;
  max-height: 50px;
}

.login-card {
  max-width: 400px;
  width: 100%;
  border-radius: 15px;
}

.text-muted {
  color: #6c757d !important;
}
</style>
