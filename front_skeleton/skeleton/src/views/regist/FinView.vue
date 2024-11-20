<script setup>
import { useRouter } from "vue-router";

const useRou = useRouter();

const Login = () => {
	useRou.push("/");
};
</script>

<template>
  <div class="container vh-100 d-flex justify-content-center align-items-center">
    <div
      class="card shadow-sm text-center p-5"
      style="max-width: 500px; width: 100%; border-radius: 15px;"
    >
	  <div class="text-center">
		  <img
			src="/src/assets/EVERYMATCH.png"
			alt="Logo"
			class="mb-4 mx-auto"
			style="width: 250px; display: block;"
		  />
	  </div>
      <h4 class="fw-bold mb-3">🎉 환영합니다</h4>
      <p class="mb-4">
        회원가입이 완료되었습니다.<br />
        오늘의 경기를 놓치지 말고 바로 시작하세요!
      </p>
      <button
        @click="Login"
        class="btn btn-danger"
        style="border-radius: 10px;"
      >
        로그인하러 가기
      </button>
    </div>
  </div>
</template>

<style scoped>

</style>
