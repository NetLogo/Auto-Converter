<script setup lang="ts">
  const props = defineProps<{
    file: string,
    data: Blob
  }>();

  function downloadBlob(): void {
    const link: HTMLAnchorElement = document.createElement("a");

    link.href = URL.createObjectURL(props.data);
    link.download = props.file;

    link.click();
  }
</script>

<template>
  <div class="round-container tight-space succeeded button" @click="downloadBlob()">
    <p>Download</p>
  </div>
</template>
