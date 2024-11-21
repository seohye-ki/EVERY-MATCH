<script setup>
import team from '@/components/Team.vue'
import { ref } from 'vue';

const teams = ref([
    {src: "LOTTE"},
    {src: "LOTTE"},
])
</script>

<template>

<fieldset>
    <div>
        <button> 전체 </button>
        <button> 야구 </button>
        <button> 축구 </button>
    </div>
    <div>
        <team v-for="team in teams" :src="team.src"/>
    </div>
</fieldset>
<fieldset>

</fieldset>

</template>

<style scoped>

</style>
