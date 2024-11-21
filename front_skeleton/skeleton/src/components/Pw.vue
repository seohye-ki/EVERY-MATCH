<script setup>
import { ref } from 'vue'

const pw = ref()

const emit = defineEmits(['update:modelValue'])
</script>

<template>
    <div>
        <div>
            <label for="input_pw">Password</label>
        </div>
        <input id="input_pw" type="password" v-model="pw" @input="$emit('update:modelValue', pw)" placeholder="비밀번호를 입력하세요"/>
    </div>
</template>

<style scoped>

</style>
