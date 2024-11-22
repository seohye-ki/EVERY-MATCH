<script setup>
import { useRouter } from 'vue-router';
import { ref } from 'vue'
import axios from 'axios';
import { errorMessages } from 'vue/compiler-sfc';

const api = axios.create({
  baseURL: 'http://localhost:8080/api'
})

api.interceptors.request.use((config) => {
    const token = sessionStorage.getItem('Authorization')
    if (token) {
      config.headers['Authorization'] = `${token}`
    }
    return config
  },
  (error) => Promise.reject(error)
)


const useRout = useRouter();
const oldPassword = ref('')
const newPassword = ref('')
const newPassword2 = ref('')

const rightPW = async () => {
  const form = new FormData()
  form.append('oldPassword',oldPassword.value)
  form.append('newPassword',newPassword.value)
  form.append('newPassword2',newPassword2.value)
  console.log(newPassword.value)
  console.log(newPassword2.value)
  try{
    const response = await api.put('/user/update-password' ,form )
    useRout.push('/main')
  } catch (error) {
    console.log(error.status)
    if (error.status === 401) {
        console.log('인증 오류!')
    } else if (error.status === 400) {
      console.log('비밀번호 인증을 다시 해주세요')
    } else {
      errorMessages.value = '서버 연결 실패'
    }
  }  
};

const cancel = (event) => {
  useRout.push('/info');
  event.preventDefault();
};
</script>

<template>
	<div class="update-password-container">
	  <!-- 비밀번호 변경 폼 -->
	  <div class="password-form">
		<!-- 로고 -->
		<img src="/src/assets/EVERYMATCH.png" alt="EVERYMATCH Logo" class="logo" />
		
		<!-- Old Password 입력 -->
		<div class="form-group">
		  <label for="old-password">Old Password</label>
		  <input id="old-password" v-model="oldPassword" type="password" placeholder="비밀번호를 입력하세요" />
		</div>
  
		<!-- New Password 입력 -->
		<div class="form-group">
		  <label for="new-password">New Password</label>
		  <input id="new-password" v-model="newPassword" type="password" placeholder="새로운 비밀번호를 입력하세요" />
		</div>
  
		<!-- New Password 확인 -->
		<div class="form-group">
		  <label for="confirm-password">New Password Confirmation</label>
		  <input id="confirm-password" v-model="newPassword2" type="password" placeholder="새로운 비밀번호를 다시 입력하세요" />
		</div>
  
		<!-- 버튼 그룹 -->
		<div class="button-group">
		  <button class="cancel-button" @click="cancel">취소</button>
		  <button class="submit-button" @click="rightPW">변경</button>
		</div>
	  </div>
	</div>
</template>

<style scoped>
/* 전체 컨테이너 */
.update-password-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f9f9f9;
  padding: 20px;
  box-sizing: border-box;
}

/* 비밀번호 변경 폼 */
.password-form {
  background-color: #ffffff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  width: 100%;
  text-align: center; /* 로고를 중앙 정렬 */
}

/* 로고 */
.logo {
  width: 70%;
  margin-bottom: 30px; /* 폼 내용과 간격 */
}

/* 폼 그룹 */
.form-group {
  margin-bottom: 20px;
  text-align: left; /* 입력 필드 라벨은 왼쪽 정렬 */
}

label {
  display: block;
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 8px;
  color: #333;
}

input {
  width: 100%;
  padding: 12px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 6px;
  box-sizing: border-box;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  justify-content: center; /* 버튼을 오른쪽 정렬 */
  gap: 10px;
}

/* 취소 버튼 */
.cancel-button {
  padding: 10px 20px;
  background-color: #f0f0f0;
  color: #555;
  font-size: 14px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.cancel-button:hover {
  background-color: #e0e0e0;
}

/* 변경 버튼 */
.submit-button {
  padding: 10px 20px;
  background-color: #da4537;
  color: #ffffff;
  font-size: 14px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-button:hover {
  background-color: #ec493a;
}
</style>
