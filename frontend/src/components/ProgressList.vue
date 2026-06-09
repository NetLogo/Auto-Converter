<script setup lang="ts">
  import { store } from "@/store/store.js";

  import ProgressItem from "./ProgressItem.vue";
</script>

<template>
  <div id="progress-list" class="flex-column">
    <ProgressItem v-for="item of store.getConversions()" :key="item.id" :item="item" />
  </div>
</template>
