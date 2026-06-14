<script setup lang="ts">
import { computed } from 'vue';

import type { NetLogoFileVariant } from '@/composables/useNetLogoFile.js';

const props = withDefaults(defineProps<{ variant?: NetLogoFileVariant }>(), {
  variant: 'old',
});

const icon = computed(() => {
  if (props.variant === 'new') {
    return {
      src: '/assets/file-icons/new-icon-3x.webp',
      srcset: '/assets/file-icons/new-icon-3x.webp',
      label: 'Modern NetLogo model file',
    };
  }

  return {
    src: '/assets/file-icons/old-icon.webp',
    srcset:
      '/assets/file-icons/old-icon.webp 1x, /assets/file-icons/old-icon-2x.webp 2x, /assets/file-icons/old-icon-3x.webp 3x',
    label: 'Legacy NetLogo model file',
  };
});
</script>

<template>
  <img :src="icon.src" :srcset="icon.srcset" :alt="icon.label" :title="icon.label" loading="lazy" class="h-12 px-1" />
</template>
