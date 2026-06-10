<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui';

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

    <UNavigationMenu variant="link" :items="headerNavItems" :ui="{ list: 'gap-2' }">
      <template #products-content="{ item }">
        <ul class="grid grid-cols-2 w-full gap-1 p-2">
          <li v-for="(child, index) in (item as NavigationMenuItem).children ?? []" :key="index">
            <a
              :href="child['to'] as string"
              target="_blank"
              rel="noopener"
              class="flex items-start gap-3 rounded-md p-2 transition-colors hover:bg-elevated/50"
            >
              <img
                :src="child['avatar']?.src"
                :alt="child['label']"
                loading="lazy"
                class="size-9 shrink-0 rounded-md object-contain"
              />
              <span class="flex flex-col gap-0.5">
                <span class="text-sm font-medium text-highlighted">{{ child['label'] }}</span>
                <span class="text-xs text-muted leading-snug">{{ child['description'] }}</span>
              </span>
            </a>
          </li>
        </ul>
      </template>
    </UNavigationMenu>

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
        target="_blank"
        rel="noopener noreferrer"
        icon="simple-icons:github"
        title="GitHub"
        aria-label="GitHub"
      />
    </template>
  </UHeader>
</template>
