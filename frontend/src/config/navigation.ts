import type { NavigationMenuItem } from '@nuxt/ui';

/** Builds an avatar pointing at a brand logo in `public/assets/brands`. */
const brand = (file: string, alt: string): NavigationMenuItem['avatar'] => ({
  src: `/assets/brands/${file}-fill.webp`,
  alt,
  class: 'rounded-md bg-transparent',
  loading: 'lazy',
});

/** Recursively flags external (`http`) links to open in a new tab. */
function withExternalTargets(items: NavigationMenuItem[]): NavigationMenuItem[] {
  return items.map((item) => ({
    ...item,
    ...(typeof item.to === 'string' && item.to.startsWith('http')
      ? { target: '_blank', rel: 'noopener noreferrer' }
      : {}),
    ...(item.children ? { children: withExternalTargets(item.children) } : {}),
  }));
}

/** Items rendered in the application header's navigation menu. */
const navItems: NavigationMenuItem[] = [
  { type: 'link', label: 'Converter', to: '/' },
  { type: 'link', label: 'About', to: '/about' },
  {
    type: 'link',
    label: 'NetLogo Products',
    slot: 'products',
    defaultOpen: true,
    children: [
      {
        type: 'link',
        label: 'NetLogo Desktop',
        to: 'https://www.netlogo.org/download',
        avatar: brand('netlogo-desktop', 'NetLogo Desktop'),
        description: 'The original NetLogo application, available for Windows, Mac, and Linux.',
      },
      {
        type: 'link',
        label: 'NetLogo Web',
        to: 'https://www.netlogoweb.org',
        avatar: brand('netlogo-web', 'NetLogo Web'),
        description: 'The web-based version of NetLogo, accessible from any modern browser.',
      },
      {
        type: 'link',
        label: 'Hubnet Web',
        to: 'https://hubnetweb.org/',
        avatar: brand('hubnet-web', 'Hubnet Web'),
        description: 'A web-based platform for authoring and running participatory simulations.',
      },
      {
        type: 'link',
        label: 'NetTango',
        to: 'https://nettango.org/',
        avatar: brand('nettango', 'NetTango'),
        description: 'Block-based coding environment for education, built on top of NetLogo Web.',
      },
      {
        type: 'link',
        label: 'Turtle Universe',
        to: 'https://turtlesim.com/products/turtle-universe/',
        avatar: brand('turtle-sim', 'Turtle Universe'),
        description: 'NetLogo-based smartphone app for iOS and Android.',
      },
      {
        type: 'link',
        label: 'BehaviorSearch',
        to: 'https://www.behaviorsearch.org/',
        avatar: brand('behavior-search', 'BehaviorSearch'),
        description: 'Automated exploration and analysis of NetLogo model behavior.',
      },
    ],
  },
  {
    type: 'link',
    label: 'Community',
    defaultOpen: true,
    children: [
      {
        type: 'link',
        label: 'NetLogo Forum',
        to: 'https://forum.netlogo.org/',
        icon: 'lucide:message-square-more',
        description: 'Ask questions, report bugs, and connect with other NetLogo users.',
      },
      {
        type: 'link',
        label: 'Modeling Commons',
        to: 'https://modelingcommons.org',
        icon: 'lucide:shapes',
        description: 'Share and explore NetLogo models created by the community.',
      },
      {
        type: 'link',
        label: 'Documentation',
        to: 'https://docs.netlogo.org/',
        icon: 'lucide:file-text',
        description: 'Official NetLogo documentation, including guides and API references.',
      },
      {
        type: 'link',
        label: 'Conference',
        to: 'https://conference.netlogo.org/',
        icon: 'lucide:calendar-1',
        description: 'NetLogo Conference announcements and information.',
      },
    ],
  },
  {
    type: 'link',
    label: 'Donate',
    icon: 'i-lucide-heart',
    to: 'https://www.netlogo.org/donate',
  },
];

export const headerNavItems: NavigationMenuItem[] = withExternalTargets(navItems);
