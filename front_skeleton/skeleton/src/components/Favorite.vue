<script setup>

const props = defineProps({
    teamId : Number,
    teamName : String
})

</script>

<template>
    <div>
        {{ props.teamName }}
        <button @click="$emit('deleteTeam', props.teamId)">X</button>
    </div>
</template>

<style scoped>

</style>