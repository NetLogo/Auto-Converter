import type { NavigationMenuItem } from '@nuxt/ui';

/** Items rendered in the application header's navigation menu. */
export const headerNavItems: NavigationMenuItem[] = [
  { type: 'link', label: 'Converter', to: '/' },
  { type: 'link', label: 'About', to: '/about' },
  {
    type: 'link',
    label: 'NetLogo Products',
    defaultOpen: true,
    children: [
      {
        type: 'link',
        label: 'NetLogo Desktop',
        to: 'https://www.netlogo.org/download',
        description: 'The original NetLogo application, available for Windows, Mac, and Linux.',
      },
      {
        type: 'link',
        label: 'NetLogo Web',
        to: 'https://www.netlogoweb.org',
        description: 'The web-based version of NetLogo, accessible from any modern browser.',
      },
      {
        type: 'link',
        label: 'Hubnet Web',
        to: 'https://hubnetweb.org/',
        description: 'A web-based platform for authoring and running participatory simulations.',
      },
      {
        type: 'link',
        label: 'NetTango',
        to: 'https://nettango.org/',
        description: 'Block-based coding environment for education, built on top of NetLogo Web.',
      },
      {
        type: 'link',
        label: 'Turtle Universe',
        to: 'https://turtlesim.com/products/turtle-universe/',
        description: 'NetLogo-based smartphone app for iOS and Android.',
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
