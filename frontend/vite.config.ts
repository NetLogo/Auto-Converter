import { fileURLToPath, URL } from 'node:url';

import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import vueDevTools from 'vite-plugin-vue-devtools';
import svgLoader from 'vite-svg-loader';
import ui from '@nuxt/ui/vite';
import vueRouter from 'vue-router/vite';

export default defineConfig({
  plugins: [
    vueRouter({
      dts: 'src/route-map.d.ts',
    }),
    vue(),
    ui({
      colorMode: false,
    }),
    vueDevTools(),
    svgLoader(),
  ],

  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
});
