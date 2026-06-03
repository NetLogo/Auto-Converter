<script setup lang="ts">
  import { store } from "@/store/store.js";

  import progressList from "./ProgressList.vue";
  import resetButton from "./Reset.vue";
  import selectButton from "./Select.vue";
</script>

<template>
  <div id="container" class="flex-column">
    <h1>NetLogo Auto-Converter</h1>
    <progressList v-if="store.inProgress()" />
    <resetButton v-if="store.inProgress()" />
    <selectButton v-else />
  </div>
</template>
