<script setup>
import { RouterLink, RouterView } from 'vue-router'

import Id from '@/components/Id.vue'
import Email from '@/components/Email.vue'
import CenterIcon from '@/components/CenterIcon.vue';

</script>

<template>

<CenterIcon />
<form >
    <fieldset>
        <Id />
        <Email />
        <div>
            <a href="/">Cancel</a>
            <button type="submit">ResetPassword</button>            
        </div>
    </fieldset>
</form>

  
</template>

<style scoped>

</style>
