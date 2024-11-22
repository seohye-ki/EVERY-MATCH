<script setup>

const props = defineProps({
    match : Object,
})

const match = props.match

</script>

<template>
    <div class="match-item">
        <div class="match-time">{{ match.time.slice(0, 5) }}</div>
        <div class="match-info">{{ match.homeTeamName }} vs {{ match.awayTeamName }}</div>
        <div class="match-location">{{ match.location}}</div>
    </div>
</template>

<style scoped>

</style>
