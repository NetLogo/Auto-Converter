<script setup lang="ts">
  import { type ConversionStatus } from "@/store/status.js";

  import Succeeded from "./Succeeded.vue";
  import Failed from "./Failed.vue";
  import Converting from "./Converting.vue";

  const props = defineProps<{
    item: ConversionStatus
  }>();
</script>

<template>
  <div class="round-container progress-item loose-space" :class="props.item.status">
    <p class="progress-item-name">{{ item.name }}</p>
    <Converting v-if="item.status == 'converting'" />
    <Succeeded v-else-if="item.status == 'succeeded'" :file="item.file" :data="item.data" />
    <Failed v-else />
  </div>
</template>
