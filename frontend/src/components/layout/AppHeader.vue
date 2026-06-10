<script setup lang="ts">
import Headline from '@/components/layout/Headline.vue';
import { headerNavItems } from '@/config/navigation.js';
</script>

<template>
  <UHeader :ui="{ left: 'min-w-0 items-end' }" class="flex flex-col" mode="drawer">
    <template #left>
      <RouterLink to="/" aria-label="Back to home">
        <Headline class="block w-auto [&_img]:h-12!" />
      </RouterLink>
    </template>

    <UNavigationMenu variant="link" :items="headerNavItems" :ui="{ 'list': 'gap-2' }"  />

    <template #body>
      <UNavigationMenu variant="link" :items="headerNavItems" orientation="vertical" class="-mx-2.5" />
    </template>

    <template #right>
      <UButton
        variant="ghost"
        color="neutral"
        square
        as="a"
        href="mailto:feedback@ccl.northwestern.edu"
        icon="mdi:email"
        title="Contact Us"
        aria-label="Contact Us"
      />

      <UButton
        variant="ghost"
        color="neutral"
        square
        as="a"
        to="https://github.com/NetLogo/Auto-Converter"
        icon="simple-icons:github"
        title="GitHub"
        aria-label="GitHub"
      />
    </template>
  </UHeader>
</template>
