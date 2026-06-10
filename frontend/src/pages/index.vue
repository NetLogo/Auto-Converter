<script setup lang="ts">
import Headline from '@/components/layout/Headline.vue';
import FileUploader from '@/components/conversion/FileUploader.vue';
import ConversionPanel from '@/components/conversion/ConversionPanel.vue';
import { useConversions } from '@/composables/useConversions.js';

const { inProgress } = useConversions();
</script>

<template>
  <div class="flex flex-col items-center gap-6">
    <Headline class="home-headline rise" />

    <Transition name="home" mode="out-in" appear>
      <ConversionPanel v-if="inProgress" />
      <div v-else class="flex flex-col items-center gap-6">
        <FileUploader />
        <p class="-mt-6 flex items-center gap-1.5 text-xs md:text-sm text-center text-muted">
          <UIcon name="mdi:information-outline" class="size-4 shrink-0" />
          <span>
            New here? See how conversion works<span class="hidden md:inline">
              and how code changes are highlighted</span>.
            <UButton to="/about" variant="link" color="primary" label="Learn more" class="text-xs md:text-sm px-0.5" />
          </span>
        </p>
      </div>
    </Transition>
  </div>
</template>