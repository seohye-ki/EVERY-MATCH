<script setup>

const props = defineProps({
    teamId : Number,
    teamLogo : String
})

</script>

<template>
    <img :src="'/src/assets/'+props.teamLogo" @click="$emit('plusFavorite', props.teamId)"/>
</template>

<style scoped>

</style>