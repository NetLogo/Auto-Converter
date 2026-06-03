<script setup lang="ts">
  import { type ConversionStatus } from "@/store/status.js";

  import succeeded from "./Succeeded.vue";
  import failed from "./Failed.vue";
  import converting from "./Converting.vue";

  const props = defineProps<{
    item: ConversionStatus
  }>();
</script>

<template>
  <div class="round-container progress-item loose-space" :class="props.item.status">
    <p class="progress-item-name">{{ item.name }}</p>
    <converting v-if="item.status == 'converting'" />
    <succeeded v-else-if="item.status == 'succeeded'" :file="item.file" :data="item.data" />
    <failed v-else />
  </div>
</template>
