<script setup>
import {ref} from 'vue'
import { useRouter } from 'vue-router';

const useRou = useRouter()

const rightPW = (pw) => {
    useRou.push("./info");
}

</script>

<template>
    <form @submit.prevent="rightPW">
        <fieldset>
            <div>
                <label for="pw">Password</label>
            </div>
            <input id="pw" placeholder="비밀번호를 입력하세요"/>
            <div>
                <button @click="cancel"> 취소 </button>
                <button type="submit"> submit </button>
            </div>
        </fieldset>
    </form>
</template>

<style scoped>

</style>
