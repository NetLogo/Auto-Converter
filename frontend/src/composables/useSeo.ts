import { useHead, useSeoMeta } from '@unhead/vue';

export const SITE_URL = 'https://convert.netlogo.com';
export const SITE_NAME = 'NetLogo Auto-Converter';
const DEFAULT_IMAGE = `${SITE_URL}/opengraph.png`;

interface SeoOptions {
  title: string;
  description: string;
  path: string;
  image?: string;
}
export function useSeo({ title, description, path, image = DEFAULT_IMAGE }: SeoOptions): void {
  const url = `${SITE_URL}${path === '/' ? '' : path}`;
  const documentTitle = path === '/' ? title : `${title} · ${SITE_NAME}`;

  useHead({
    htmlAttrs: { lang: 'en' },
    link: [{ rel: 'canonical', href: url }],
  });

  useSeoMeta({
    title: documentTitle,
    description,
    ogTitle: documentTitle,
    ogDescription: description,
    ogType: 'website',
    ogUrl: url,
    ogImage: image,
    ogSiteName: SITE_NAME,
    twitterCard: 'summary_large_image',
    twitterTitle: documentTitle,
    twitterDescription: description,
    twitterImage: image,
  });
}
