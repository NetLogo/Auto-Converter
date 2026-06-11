<script setup lang="ts">
import Headline from '@/components/layout/Headline.vue';
import FileUploader from '@/components/conversion/FileUploader.vue';
import ConversionPanel from '@/components/conversion/ConversionPanel.vue';
import { useConversions } from '@/composables/useConversions.js';
import { useSeo } from '@/composables/useSeo.js';

const { inProgress } = useConversions();

useSeo({
  title: 'NetLogo Auto-Converter: Upgrade Models to NetLogo 7',
  description:
    'Official NetLogo tool for upgrading NetLogo 4, 5, and 6 .nlogo models to the new NetLogo 7 XML .nlogox format. Free and runs entirely in your browser.',
  path: '/',
});
</script>

<template>
  <div class="flex flex-col items-center gap-6 w-full px-10">
    <Headline class="home-headline rise -mt-20" />

    <Transition name="home" mode="out-in" appear>
      <ConversionPanel v-if="inProgress" />
      <div v-else class="flex flex-col items-center gap-6 w-full">
        <FileUploader />
        <p class="-mt-6 flex items-center gap-1.5 text-xs md:text-sm text-center text-muted">
          <UIcon name="mdi:information-outline" class="size-4 shrink-0" />
          <span>
            New here? See how conversion works.
            <UButton to="/about" variant="link" color="primary" label="Learn more" class="text-xs md:text-sm px-0.5" />
          </span>
        </p>
      </div>
    </Transition>
  </div>
</template>