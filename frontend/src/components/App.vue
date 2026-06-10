<script setup lang="ts">
import AppHeader from '@/components/layout/AppHeader.vue';
</script>

<template>
  <UApp>
    <AppHeader />

    <UMain id="container" class="flex flex-col items-center justify-center">
      <RouterView />
    </UMain>
  </UApp>
</template>

<style scoped>
#container {
  background: linear-gradient(to bottom, #edf8ff, #ffffff 50%);
}
</style>
