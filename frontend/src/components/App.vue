<script setup lang="ts">
  import { store } from "@/store/store.js";

  import ProgressList from "./ProgressList.vue";
  import ResetButton from "./Reset.vue";
  import SelectButton from "./Select.vue";
</script>

<template>
  <div id="container" class="flex-column">
    <h1>NetLogo Auto-Converter</h1>
    <ProgressList v-if="store.inProgress()" />
    <ResetButton v-if="store.inProgress()" />
    <SelectButton v-else />
  </div>
</template>
