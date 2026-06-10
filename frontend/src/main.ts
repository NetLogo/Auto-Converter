import './main.css';

import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import { routes } from 'vue-router/auto-routes';
import App from './components/App.vue';
import ui from '@nuxt/ui/vue-plugin';

const app = createApp(App);
const router = createRouter({
  history: createWebHistory(),
  routes,
});

app.use(router);
app.use(ui);

app.mount('#app');
