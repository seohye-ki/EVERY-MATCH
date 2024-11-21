<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const rightPW = (pw) => {
  router.push('/main');
};

const cancel = (event) => {
  router.push('/info');
  event.preventDefault();
};
</script>

<template>
  <div class="container vh-100 d-flex justify-content-center align-items-center">
    <div class="card shadow-lg p-4 rounded" style="max-width: 400px; width: 100%;">
      <!-- 로고 -->
      <div class="text-center mb-4">
        <img src="/src/assets/EVERYMATCH.png" alt="Everymatch Logo" class="mx-auto logo" />
      </div>
      <!-- 비밀번호 변경 폼 -->
      <form @submit.prevent="rightPW">
        <div class="mb-3">
          <label for="oldPW" class="form-label">Current Password</label>
          <input
            id="oldPW"
            class="form-control"
            type="password"
            placeholder="현재 비밀번호를 입력하세요"
            required
          />
        </div>
        <div class="mb-3">
          <label for="newPW" class="form-label">New Password</label>
          <input
            id="newPW"
            class="form-control"
            type="password"
            placeholder="새 비밀번호를 입력하세요"
            required
          />
        </div>
        <div class="mb-3">
          <label for="newPW2" class="form-labe">Confirm New Password</label>
          <input
            id="newPW2"
            class="form-control"
            type="password"
            placeholder="새 비밀번호를 다시 입력하세요"
            required
          />
        </div>
        <!-- 버튼 -->
        <div class="d-flex justify-content-between mt-4">
          <button @click="cancel" type="button" class="btn btn-outline-secondary">
            취소
          </button>
          <button type="submit" class="btn btn-danger">비밀번호 변경</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>

.container {
  background-color: #f9f9f9;
}

/* 로고 스타일 */
.logo {
  max-width: 250px;
  height: auto;
}

/* 버튼 스타일 */
button {
  width: 48%;
}

button.btn-danger {
  background-color: #d86b61;
  border-color: #d86b61;
}

button.btn-danger:hover {
  background-color: #ba584f;
  border-color: #ba584f;
}

button.btn-outline-secondary {
  color: #6c757d;
}

button.btn-outline-secondary:hover {
  background-color: #f8f9fa;
}

/* 입력 필드 스타일 */
input {
  border-radius: 5px;
  padding: 10px;
}

input:focus {
  border-color: #d86b61;
  box-shadow: 0 0 5px rgba(216, 107, 97, 0.5);
}
</style>
