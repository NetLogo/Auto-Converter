<script setup lang="ts">
import { type Ref, ref } from 'vue';

import NetLogoFileIcon from '@/components/ui/NetLogoFileIcon.vue';
import { useConversions } from '@/composables/useConversions.js';
import { ACCEPTED_EXTENSIONS_STR, fileVariant, isValidFormat } from '@/composables/useNetLogoFile.js';

const { convert } = useConversions();

const fileUpload: Ref<File[]> = ref([]);

function filterSelected(_: Event): void {
  fileUpload.value = fileUpload.value.filter((file) => isValidFormat(file));
}
</script>

<template>
  <UFileUpload
    layout="list"
    multiple
    label="Drop your models here or click to select"
    description=".nlogo, .nlogo3d, .nlogox, and .nlogox3d"
    :accept="ACCEPTED_EXTENSIONS_STR"
    class="w-full max-w-xl mb-10"
    :ui="{ base: 'min-h-48 border-primary/50', 'file': 'rise' }"
    v-model="fileUpload"
    @change="filterSelected"
  >
    <template #file-leading="{ file }">
      <NetLogoFileIcon :variant="fileVariant(file)" />
    </template>

    <template #files-bottom="{ removeFile, files }">
      <div class="flex gap-2 justify-end w-full">
        <UButton
          :disabled="!files?.length"
          label="Remove all files"
          color="error"
          variant="ghost"
          icon="mdi:delete"
          @click="removeFile()"
        />
        <UButton
          :disabled="!files?.length"
          label="Convert"
          icon="mdi:update"
          color="primary"
          @click="convert(files ?? [])"
        />
      </div>
    </template>
  </UFileUpload>
</template>
