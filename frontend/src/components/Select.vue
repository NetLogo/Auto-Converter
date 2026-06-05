<script setup lang="ts">
  import { useTemplateRef } from "vue";

  import { store } from "@/store/store.js";

  const input = useTemplateRef("input");

  const service: string = `${import.meta.env["VITE_AC_SERVICE_HOST"]}/convert`;

  function convert(): void {
    store.beginProgress();

    for (const file of input.value?.files ?? new FileList()) {
      const id: number = store.addConversion(file.name.replace(/\.nlogo.*$/, ""));

      const data = new FormData();

      data.append("model", file);

      fetch(service, {
        method: "POST",
        body: data,
        signal: store.abortSignal()
      }).then(async response => {
        if (response.status == 200) {
          store.succeed(id, file.name.replace(/.nlogo(3d)?$/, ".nlogox$1"), await response.blob());
        } else {
          store.fail(id, "Received invalid response from server.");
        }
      }, error => {
        store.fail(id, error);
      });
    }
  }
</script>

<template>
  <button class="round-container button loose-space" @click="input?.click">Select Files</button>
  <input ref="input" id="model" type="file" multiple="true" accept=".nlogo,.nlogo3d,.nlogox,.nlogox3d" hidden="true" @change="convert" />
</template>
