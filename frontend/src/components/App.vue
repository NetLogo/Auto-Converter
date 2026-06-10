<script setup lang="ts">
import AppHeader from '@/components/layout/AppHeader.vue';
import Headline from '@/components/layout/Headline.vue';
import FileUploader from '@/components/conversion/FileUploader.vue';
import ConversionPanel from '@/components/conversion/ConversionPanel.vue';
import { useConversions } from '@/composables/useConversions.js';

const { inProgress } = useConversions();
</script>

<template>
  <UApp>
    <AppHeader />

    <UMain id="container" class="flex flex-col items-center justify-center gap-6">
      <Headline />

        <ConversionPanel v-if="inProgress" />
        <FileUploader v-else />
    </UMain>
  </UApp>
</template>

<style scoped>
#container {
  background: linear-gradient(to bottom, #edf8ff, #ffffff 50%);
}
</style>
