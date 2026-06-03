<script setup lang="ts">
  import { store } from "@/store/store.js";

  import progressItem from "./ProgressItem.vue";
</script>

<template>
  <div id="progress-list" class="flex-column">
    <progressItem v-for="item of store.getConversions()" :key="item.id" :item="item" />
  </div>
</template>
