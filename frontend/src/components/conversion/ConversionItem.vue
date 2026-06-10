<script setup lang="ts">
import NetLogoFileIcon from '@/components/ui/NetLogoFileIcon.vue';
import Loader from '@/components/ui/Loader.vue';
import type { ConversionStatus } from '@/store/status.js';
import { useConversions } from '@/composables/useConversions.js';
import { fileVariant, formatFileSize } from '@/composables/useNetLogoFile.js';

const props = defineProps<{ item: ConversionStatus }>();

const { download } = useConversions();
</script>

<template>
  <div class="flex items-center justify-between w-full lg:w-lg max-w-lg p-2 border-muted rounded-lg border">
    <div class="flex items-center gap-3 min-w-0">
      <NetLogoFileIcon :variant="fileVariant(item.originalFile)" class="h-12 shrink-0" />
      <p class="flex flex-col gap-1 min-w-0">
        <span class="text-xs truncate">{{ item.originalFile.name }}</span>
        <span class="text-xs text-muted">{{ formatFileSize(item.originalFile.size) }}</span>
      </p>
    </div>

    <Loader v-if="item.status === 'converting'" class="h-14!"/>

    <UButton
      v-else-if="item.status === 'succeeded'"
      icon="mdi:download"
      color="success"
      variant="subtle"
      label="Download"
      class="rise my-3"
      @click="download(item)"
    />

    <UTooltip v-else-if="item.status === 'failed'" :text="item.error">
      <UAlert class="max-w-fit py-2 rise" icon="mdi:alert-circle" title="Failed" variant="subtle" color="error"/>
    </UTooltip>
  </div>
</template>
