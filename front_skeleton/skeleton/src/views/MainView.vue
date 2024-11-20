<script setup>
import Calendar from '@/components/Calendar.vue'
</script>

<template>
<main>
    <div>
        <img src="@/assets/SMALL.png"/>
        <div>
            마이페이지
        </div>
        <div>
            Favorite Team
        </div>
        <div>
            Logout
        </div>
    </div>
    <Calendar />
    <div>
        <span>
            안녕
        </span>
    </div>
</main>
</template>

<style scoped>
main {
    display: flex;
    flex-direction: row;
}
</style>
