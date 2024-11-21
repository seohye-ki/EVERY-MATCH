<script setup>

const props = defineProps({
    match : Object,
})

const match = props.match

</script>

<template>
    <div>
        <h2>{{ match.time }}  {{ match.homeTeamName }} vs {{ match.awayTeamName }}</h2>
        <h2>{{ match.location }}</h2>
    </div>
</template>

<style scoped>

</style>
