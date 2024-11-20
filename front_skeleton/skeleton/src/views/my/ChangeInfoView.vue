<script setup>
import {ref} from 'vue'
import { useRouter } from 'vue-router';

const useRou = useRouter()

const changeInfo = () => {
    useRou.push("/main");
}

const changePW = (event) => {
    useRou.push("./changePW");
    event.preventDefault();
}

const cancel = (event) => {
    useRou.push("/main");
    event.preventDefault();
}

</script>

<template>
    <form @submit.prevent="changeInfo">
        <fieldset>
            <div>
                <label for="id">ID</label>
            </div>
            <input id="id" placeholder="아이디?"/>
            <div>
                <label for="nickname">NickName</label>
            </div>
            <input id="nickname" placeholder="닉네임?"/>
            <div>
                <label for="email">Password</label>
            </div>
            <input id="email" placeholder="이메일?"/>
            <div>
                <button @click="changePW"> 비밀번호 변경 </button>
            </div>
            <div>
                <button @click="cancel"> 취소 </button>
                <button type="submit"> submit </button>
            </div>
        </fieldset>
    </form>
</template>

<style scoped>

</style>
