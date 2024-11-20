<script setup>
import {ref} from 'vue'
import { useRouter } from 'vue-router';

const useRou = useRouter()

const rightPW = (pw) => {
    useRou.push("/main");
}

const cancel = (event) => {
    useRou.push("./info");
    event.preventDefault();
}

</script>

<template>
    <form @submit.prevent="rightPW">
        <fieldset>
            <div>
                <label for="oldPW">Old Password</label>
            </div>
            <input id="oldPW" placeholder="비밀번호를 입력하세요"/>
            <div>
                <label for="newPW">New Password</label>
            </div>
            <input id="newPW" placeholder="비밀번호를 입력하세요"/>
            <div>
                <label for="newPW2">New Password Conf</label>
            </div>
            <input id="newPW2" placeholder="비밀번호를 입력하세요"/>
            <div>
                <button @click="cancel"> 취소 </button>
                <button type="submit"> submit </button>
            </div>
        </fieldset>
    </form>
</template>

<style scoped>

</style>
