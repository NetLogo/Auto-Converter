<script setup lang="ts">
  import { store } from "@/store/store.js";
</script>

<template>
  <button class="round-container button loose-space" @click="store.endProgress">Reset</button>
</template>
