<script setup>
import { RouterLink, RouterView } from 'vue-router'
import CenterIcon from '@/components/CenterIcon.vue';

</script>

<template>

<CenterIcon />

    <fieldset>
        <router-view></router-view>
    </fieldset>

</template>

<style scoped>

</style>
