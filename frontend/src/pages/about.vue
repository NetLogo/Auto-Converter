<script setup lang="ts">
import NetLogoFileIcon from '@/components/ui/NetLogoFileIcon.vue';
import { useSeo } from '@/composables/useSeo.js';

useSeo({
  title: 'About',
  description:
    'How the NetLogo Auto-Converter upgrades legacy .nlogo models to the NetLogo 7 .nlogox format, what it changes, and its limitations.',
  path: '/about',
});

const steps = [
  {
    icon: 'mdi:file-upload-outline',
    title: 'Add your models',
    body: 'Drop one or more model files onto the upload area, or click to browse. You can convert several at once.',
  },
  {
    icon: 'mdi:cog-sync-outline',
    title: 'Convert',
    body: 'Each model is upgraded one major version at a time via the NetLogo Auto-Converter service.',
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
      <h1 class="text-2xl font-bold text-highlighted">About the Auto-Converter</h1>
      <p class="text-muted leading-relaxed">
        The NetLogo Auto-Converter upgrades legacy models to the modern XML-based format used by
        <a class="text-primary hover:underline" href="https://www.netlogo.org/download" target="_blank" rel="noopener"
          >NetLogo 7</a
        >
        and
        <a class="text-primary hover:underline" href="https://www.netlogoweb.org" target="_blank" rel="noopener">
          NetLogo Web</a
        >. The Auto-Converter will also upgrade models authored in older versions of NetLogo to be compatible with the
        latest NetLogo release.
      </p>
      <p class="text-muted leading-relaxed">Everything runs from your browser. Just upload, convert, and download.</p>
    </header>

    <section class="flex flex-col gap-4">
      <h2 class="text-lg font-semibold text-highlighted">Supported formats</h2>
      <div class="flex items-center justify-center gap-4 rounded-lg border border-muted p-6">
        <div class="flex flex-col items-center gap-2">
          <NetLogoFileIcon variant="old" />
          <span class="text-xs">Legacy format</span>
          <code class="family-mono text-xs text-muted">.nlogo, .nlogo3d</code>
        </div>
        <div
          class="conversion-chevrons family-mono pb-8 text-2xl font-bold text-primary select-none sm:pb-12 sm:text-3xl mt-5"
          aria-hidden="true"
        >
          <span>&gt;</span><span>&gt;</span><span>&gt;</span>
        </div>
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
        </p>
      </div>
    </section>

    <section class="flex flex-col gap-4">
      <h2 class="text-lg font-semibold text-highlighted">Limitations</h2>
      <div class="flex gap-4 rounded-lg border border-muted p-4">
        <UIcon name="mdi:alert-outline" class="size-6 text-muted shrink-0 mt-0.5" />
        <p class="text-sm text-muted leading-relaxed">
          The converter cannot upgrade models older than
          <span class="font-medium text-highlighted">NetLogo 4</span>: these need to be opened and re-saved in NetLogo 4 before they can be converted here. Models from NetLogo 4 can be upgraded, but the process
          is <span class="font-medium text-highlighted">more error-prone</span>, so review the converted output
          carefully.
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
          target="_blank"
          rel="noopener noreferrer"
          icon="simple-icons:github"
          color="neutral"
          variant="subtle"
          label="Report an issue"
        />
        <UButton
          as="a"
          to="https://docs.netlogo.org/"
          target="_blank"
          rel="noopener noreferrer"
          icon="mdi:file-document-outline"
          color="neutral"
          variant="ghost"
          label="Documentation"
        />
      </div>
    </section>

    <div class="flex flex-wrap items-center justify-between gap-2 border-t border-muted pt-6">
      <UButton to="/" icon="mdi:arrow-left" color="neutral" variant="link" label="Back to the converter" />
      <UButton
        to="https://docs.netlogo.org/netlogo7intro"
        trailing-icon="mdi:arrow-top-right"
        color="primary"
        variant="link"
        external
        rel="noopener noreferrer"
        target="_blank"
        label="What's new in NetLogo 7"
      />
    </div>
  </div>
</template>
