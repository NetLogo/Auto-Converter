<script setup lang="ts">
import NetLogoFileIcon from '@/components/ui/NetLogoFileIcon.vue';
import { useConversions } from '@/composables/useConversions.js';
import { ACCEPTED_EXTENSIONS, fileVariant } from '@/composables/useNetLogoFile.js';

const { convert } = useConversions();
</script>

<template>
  <UFileUpload
    layout="list"
    multiple
    label="Drop your models here or click to select"
    description=".nlogo, .nlogo3d, .nlogox, and .nlogox3d"
    :accept="ACCEPTED_EXTENSIONS"
    class="w-full max-w-lg mb-10"
    :ui="{ base: 'min-h-48 border-primary/50' }"
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
