<script setup lang="ts">
  import { useTemplateRef } from "vue";

  interface ConvertedFile {
    name: string;
    data: Blob;
  }

  const input = useTemplateRef("input");

  function convert(): void {
    Promise.allSettled(convertFiles(input.value?.files ?? new FileList())).then(results => {
      if (results.some(result => result.status == "rejected")) {
        window.alert("Not all models were successfully auto-converted. Downloading successful conversions.");
      }

      for (const result of results) {
        if (result.status == "fulfilled") {
          downloadBlob(result.value.name, result.value.data);
        }
      }
    }, window.alert);
  }

  function* convertFiles(files: FileList): Generator<Promise<ConvertedFile>> {
    for (const file of files) {
      const data = new FormData();

      data.append("model", file);

      yield fetch("http://localhost:4242/convert", {
        method: "POST",
        body: data
      }).then(async response => {
        if (response.status != 200) {
          return Promise.reject("Failed to auto-convert model.");
        }

        return {
          name: file.name.replace(/.nlogo(3d)?$/, ".nlogox$1"),
          data: await response.blob(),
        };
      }, Promise.reject);
    }
  }

  function downloadBlob(name: string, data: Blob): void {
    const link: HTMLAnchorElement = document.createElement("a");

    link.href = URL.createObjectURL(data);
    link.download = name;

    link.click();
  }
</script>

<template>
  <div id="container">
    <h1>NetLogo Auto-Converter</h1>
    <button id="select" @click="input?.click">Select Files</button>
    <input ref="input" id="model" type="file" multiple="true" accept=".nlogo,.nlogo3d,.nlogox,.nlogox3d" hidden="true" @change="convert"></input>
  </div>
</template>
