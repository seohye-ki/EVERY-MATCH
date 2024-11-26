<script setup>
import { ref } from 'vue'

const id = ref()

const emit = defineEmits(['update:modelValue'])
</script>

<template>
    <div>
        <div>
            <label for="input_id">ID</label>
        </div>
        <input id="input_id" type="text" v-model="id" @input="$emit('update:modelValue', id)" placeholder="아이디를 입력하세요" maxlength="10"/>
    </div>
</template>

<style scoped>

</style>
