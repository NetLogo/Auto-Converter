<script setup lang="ts">
import Headline from '@/components/layout/Headline.vue';
import FileUploader from '@/components/conversion/FileUploader.vue';
import ConversionPanel from '@/components/conversion/ConversionPanel.vue';
import { useConversions } from '@/composables/useConversions.js';

const { inProgress } = useConversions();
</script>

<template>
  <div class="flex flex-col items-center gap-6">
    <Headline />
    <ConversionPanel v-if="inProgress" />
    <template v-else>
      <FileUploader />
      <p class="-mt-6 flex items-center gap-1.5 text-sm text-muted">
        <UIcon name="mdi:information-outline" class="size-4 shrink-0" />
        <span>New here? See how conversion works and how code changes are highlighted.</span>
        <UButton to="/about" variant="link" color="primary" label="Learn more" class="px-0.5" />
      </p>
    </template>
  </div>
</template>
