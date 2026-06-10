import { computed } from 'vue';

import { store } from '@/store/store.js';
import type { Succeeded } from '@/store/status.js';
import { modelName, convertedName } from '@/composables/useNetLogoFile.js';

const SERVICE_HOST = import.meta.env['VITE_AC_SERVICE_HOST'] || 'http://localhost:4242';
const SERVICE_URL = `${SERVICE_HOST}/convert`;

function triggerDownload(blob: Blob, filename: string): void {
  const url = URL.createObjectURL(blob);
  const link = document.createElement('a');

  link.href = url;
  link.download = filename;
  link.click();

  URL.revokeObjectURL(url);
}

/**
 * Orchestrates model conversions on top of the reactive store: uploading
 * selected files to the converter service and downloading the results.
 */
export function useConversions() {
  const conversions = computed(() => store.getConversions());

  const succeeded = computed(() => conversions.value.filter((item): item is Succeeded => item.status === 'succeeded'));

  const inProgress = computed(() => store.inProgress());

  function convert(files: File[]): void {
    if (files.length === 0) {
      return;
    }

    store.beginProgress();

    for (const file of files) {
      const id = store.addConversion(modelName(file), file);

      const data = new FormData();
      data.append('model', file);

      fetch(SERVICE_URL, {
        method: 'POST',
        body: data,
        signal: store.abortSignal(),
      }).then(
        async (response) => {
          if (response.status === 200) {
            store.succeed(id, convertedName(file), await response.blob());
          } else {
            store.fail(id, 'Received invalid response from server.');
          }
        },
        (error) => {
          store.fail(id, `${error}`);
        },
      );
    }
  }

  function download(item: Succeeded): void {
    triggerDownload(item.data, item.file);
  }

  function downloadAll(): void {
    for (const item of succeeded.value) {
      download(item);
    }
  }

  function reset(): void {
    store.endProgress();
  }

  return { conversions, succeeded, inProgress, convert, download, downloadAll, reset };
}
