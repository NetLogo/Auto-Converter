<script setup lang="ts">
import NetLogoFileIcon from '@/components/ui/NetLogoFileIcon.vue';

const steps = [
  {
    icon: 'mdi:file-upload-outline',
    title: 'Add your models',
    body: 'Drop one or more model files onto the upload area, or click to browse. You can convert several at once.',
  },
  {
    icon: 'mdi:cog-sync-outline',
    title: 'Convert',
    body: 'Each model is sent to the NetLogo conversion service and upgraded to the modern format.',
  },
  {
    icon: 'mdi:download-outline',
    title: 'Download',
    body: 'Download each converted model individually, or grab them all at once. Your original files are never modified.',
  },
];
</script>

<template>
  <div class="mx-auto w-full max-w-2xl px-6 py-12 flex flex-col gap-10">
    <header class="flex flex-col gap-3">
      <h1 class="text-2xl font-bold text-highlighted">About the Auto Converter</h1>
      <p class="text-muted leading-relaxed">
        The NetLogo Auto-Converter upgrades legacy models to the modern XML-based format used by <a class="text-primary hover:underline" href="https://www.netlogo.org/download" target="_blank" rel="noopener">NetLogo 7</a> and
        <a class="text-primary hover:underline" href="https://www.netlogoweb.org" target="_blank" rel="noopener">
          NetLogo Web</a>.
          The Auto Converter will also upgrade models authored in older versions of NetLogo to be compatible with the latest NetLogo release.
      </p>
      <p class="text-muted leading-relaxed">
        Everything runs from your browser. Just upload, convert, and download.
      </p>
    </header>

    <section class="flex flex-col gap-4">
      <h2 class="text-lg font-semibold text-highlighted">Supported formats</h2>
      <div class="flex items-center justify-center gap-4 rounded-lg border border-muted p-6">
        <div class="flex flex-col items-center gap-2">
          <NetLogoFileIcon variant="old" />
          <span class="text-xs">Legacy format</span>
          <code class="family-mono text-xs text-muted">.nlogo, .nlogo3d</code>
        </div>
        <UIcon name="mdi:arrow-right-bold-circle" class="size-8 text-neutral shrink-0" />
        <div class="flex flex-col items-center gap-2">
          <NetLogoFileIcon variant="new" />
          <span class="text-xs">New XML format</span>
          <code class="family-mono text-xs text-muted">.nlogox, .nlogox3d</code>
        </div>
      </div>
    </section>

    <section class="flex flex-col gap-4">
      <h2 class="text-lg font-semibold text-highlighted">How it works</h2>
      <ol class="flex flex-col gap-4">
        <li v-for="(step, index) in steps" :key="index" class="flex gap-4">
          <UIcon :name="step.icon" class="size-6 text-primary shrink-0 mt-0.5" />
          <div class="flex flex-col gap-1">
            <span class="font-medium text-highlighted">{{ step.title }}</span>
            <span class="text-sm text-muted leading-relaxed">{{ step.body }}</span>
          </div>
        </li>
      </ol>
    </section>


    <section class="flex flex-col gap-4">
      <h2 class="text-lg font-semibold text-highlighted">Code changes</h2>
      <div class="flex gap-4 rounded-lg border border-warning/40 bg-warning/10 p-4">
        <UIcon name="mdi:pencil-outline" class="size-6 text-warning shrink-0 mt-0.5" />
        <p class="text-sm text-muted leading-relaxed">
          Some of your model's code may be rewritten where necessary to keep it working in the new version of NetLogo.
          Any lines the converter changes are <span class="font-medium text-highlighted">highlighted in the downloaded
          model</span> so you can review exactly what was modified.
        </p>
      </div>
    </section>

    <section class="flex flex-col gap-3">
      <h2 class="text-lg font-semibold text-highlighted">Need help?</h2>
      <p class="text-muted leading-relaxed">
        If a model fails to convert, the error is shown both on the file and as a notification. Reach out with the
        offending model and we'll take a look.
      </p>
      <div class="flex flex-wrap gap-2">
        <UButton
          as="a"
          href="mailto:feedback@ccl.northwestern.edu"
          icon="mdi:email-outline"
          color="primary"
          variant="subtle"
          label="Email feedback"
        />
        <UButton
          as="a"
          to="https://github.com/NetLogo/Auto-Converter/issues"
          icon="simple-icons:github"
          color="neutral"
          variant="subtle"
          label="Report an issue"
        />
        <UButton
          as="a"
          to="https://docs.netlogo.org/"
          icon="mdi:file-document-outline"
          color="neutral"
          variant="ghost"
          label="Documentation"
        />
      </div>
    </section>

    <UButton to="/" icon="mdi:arrow-left" color="neutral" variant="link" label="Back to the converter" class="self-start" />
  </div>
</template>
