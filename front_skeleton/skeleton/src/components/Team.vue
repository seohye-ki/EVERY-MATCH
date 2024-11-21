<script setup>
import { defineProps } from 'vue';

const props = defineProps({
    src : String
})

const emit = defineEmits('teamClick')

</script>

<template>
    <img :src="'/src/assets/'+props.src+'.png'" @click="$emit(team, props.src)"/>
</template>

<style scoped>

</style>