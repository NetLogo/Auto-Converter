<script setup lang="ts">
import ConversionItem from '@/components/conversion/ConversionItem.vue';
import { useConversions } from '@/composables/useConversions.js';

const { conversions, succeeded, downloadAll, reset } = useConversions();
</script>

<template>
  <div class="flex flex-col gap-6 justify-end">
    <ConversionItem v-for="item of conversions" :key="item.id" :item="item" />

    <div class="flex w-full gap-2 justify-end">
      <UButton color="neutral" variant="ghost" icon="mdi:restore" @click="reset()">Back to upload</UButton>
      <UButton
        color="primary"
        variant="subtle"
        icon="mdi:box-download"
        :disabled="succeeded.length === 0"
        @click="downloadAll()"
      >
        Download all<template v-if="succeeded.length"> ({{ succeeded.length }})</template>
      </UButton>
    </div>
  </div>
</template>
