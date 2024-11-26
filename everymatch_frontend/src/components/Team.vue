<script setup>
const props = defineProps({
  teamId: Number,
  teamLogo: String,
});
</script>

<template>
  <img
    :src="'/src/assets/imgs/' + props.teamLogo"
    @click="$emit('plusFavorite', props.teamId)"
    style="object-fit: contain"
  />
</template>

<style scoped></style>
