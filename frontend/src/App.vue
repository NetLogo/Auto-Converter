<script setup lang="ts">
  import { useTemplateRef } from "vue";

  const input = useTemplateRef("input");

  function convert(): void {
    for (const file of input.value?.files ?? new FileList()) {
      const data = new FormData();

      data.append("model", file);

      fetch("http://localhost:4242/convert", {
        method: "POST",
        body: data
      }).catch(console.error).then(async response => {
        const blob: Blob | undefined = await response?.blob()

        if (blob) {
          const link: HTMLAnchorElement = document.createElement("a");

          link.href = URL.createObjectURL(blob);
          link.download = `${file.name.substring(0, file.name.lastIndexOf("."))}.nlogox`;

          link.click();
        }
      })
    }
  }
</script>

<template>
  <div id="container">
    <h1>NetLogo Auto-Converter</h1>
    <button id="select" @click="input?.click">Select Files</button>
    <input ref="input" id="model" type="file" multiple="true" accept=".nlogo,.nlogo3d,.nlogox,.nlogox3d" hidden="true" @change="convert"></input>
  </div>
</template>
