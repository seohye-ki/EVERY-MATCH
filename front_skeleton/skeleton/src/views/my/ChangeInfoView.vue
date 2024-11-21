<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

api.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem('Authorization');
    if (token) {
      config.headers['Authorization'] = `${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

const router = useRouter();

const userId = ref('');
const nickname = ref('');
const email = ref('');

// 사용자 정보 불러오기
onMounted(async () => {
  try {
    const response = await api.get('/user');
    userId.value = response.data.userId;
    nickname.value = response.data.nickname;
    email.value = response.data.email;
  } catch (error) {
    console.error('Failed to fetch user data:', error);
  }
});

// 정보 수정
const changeInfo = async () => {
  try {
    const response = await api.put('/user/update', {
      userId: userId.value,
      nickname: nickname.value,
      email: email.value,
    });

    if (response.status === 200) {
      alert('정보가 성공적으로 수정되었습니다.');
      router.push('/main');
    }
  } catch (error) {
    console.error('Failed to update user data:', error);
    alert('정보 수정 중 문제가 발생했습니다.');
  }
};

// 비밀번호 변경
const changePW = () => {
  router.push('/changePW');
};

// 취소
const cancel = () => {
  router.push('/main');
};

// 회원 탈퇴
const deleteAccount = async () => {
  if (confirm('정말로 회원 탈퇴를 진행하시겠습니까?')) {
    try {
      await api.delete('/user/delete');
      alert('회원 탈퇴가 완료되었습니다.');
      router.push('/login');
    } catch (error) {
      console.error('Failed to delete user:', error);
      alert('회원 탈퇴 중 문제가 발생했습니다.');
    }
  }
};
</script>

<template>
  <div class="container vh-100 d-flex align-items-center justify-content-center" style="max-width: 100%; height: 100vh; margin: 0 auto; padding: 40px;">
    <div class="position-absolute top-0 start-1 p-3" style="width: 350px;">
      <img src="/src/assets/EVERYMATCH.png" alt="Logo" style="width: 100%; height: auto;">
    </div>
	<div class="row shadow-lg rounded w-100" style="height: 90%; overflow: auto;">
      <!-- 사이드바 -->
      <div class="col-12 col-md-4 bg-danger text-white d-flex flex-column align-items-center py-5 justify-content-between" style="height: auto;">
        <div class="d-flex flex-column align-items-center">
          <img
            src="/src/assets/user.png"
            alt="User Avatar"
            class="rounded-circle"
            style="width: 150px; height: 150px;"
          />
        </div>
        <button
          class="btn btn-light text-danger fw-bold px-4 mt-5"
          @click="deleteAccount"
        >
          회원탈퇴
        </button>
      </div>
      <!-- 메인 카드 -->
      <div class="col-12 col-md-8 bg-white p-5" style="height: auto;">
        <h1 class="mb-4 fw-bold">마이페이지</h1>
        <form @submit.prevent="changeInfo">
          <!-- ID -->
          <div class="mb-4">
            <label for="id" class="form-label">ID</label>
            <input
              type="text"
              id="id"
              class="form-control"
              v-model="userId"
              readonly
            />
          </div>
  
          <!-- Nickname -->
          <div class="mb-4">
            <label for="nickname" class="form-label">Nickname</label>
            <input
              type="text"
              id="nickname"
              class="form-control"
              v-model="nickname"
            />
          </div>
  
          <!-- Email -->
          <div class="mb-4">
            <label for="email" class="form-label">Email</label>
            <input
              type="email"
              id="email"
              class="form-control"
              v-model="email"
            />
          </div>
  
          <!-- 비밀번호 변경 버튼 -->
          <button
            type="button"
            class="btn btn-outline-secondary btn-lg mb-3 change-pw-btn"
            @click="changePW"
          >
            비밀번호 변경
          </button>
  
          <!-- 수정 및 취소 버튼 -->
          <div class="d-flex justify-content-end">
            <button type="button" class="btn" @click="cancel">
              X 취소
            </button>
            <button type="submit" class="btn btn-danger">정보 수정</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  background-color: #ffffff;
  padding: 20px;
  height: 100vh;
}

.bg-danger {
  background-color: #D86B61 !important; /* 사이드바 배경색 */
}

.card {
  border: none;
}

button {
  font-weight: bold;
}

button.btn-danger {
  background-color: #D86B61;
  border: none;
}

button.btn-danger:hover {
  background-color: #BA584F; /* 호버 시 색상 변경 */
}

button.btn-outline-secondary {
  border-color: #dcdcdc;
  color: #333;
  font-size: 1rem; /* 텍스트 크기 조정 */
  padding: 10px 20px; /* 버튼 크기 */
  border-radius: 5px; /* 둥근 버튼 */
}

button.btn-outline-secondary:hover {
  background-color: #e0e0e0;
  color: #000;
}

h1 {
  font-size: 1.8rem;
}

input {
  border-radius: 5px;
  padding: 10px;
}

input:focus {
  border-color: #D86B61;
  box-shadow: 0 0 5px rgba(216, 107, 97, 0.5);
}

.col-md-4 {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

button.change-pw-btn {
  width: 50%;
  height: 48px; /* 높이를 그림과 동일하게 설정 */
  font-size: 1rem;
}

.d-flex.justify-content-end button {
  margin-left: 10px; /* 버튼 간 간격 */
}

button.btn-outline-secondary:hover {
  background-color: #dcdcdc; /* 연한 회색 배경 */
  color: #555; /* 어두운 회색 텍스트 */
}


</style>
