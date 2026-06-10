<script setup lang="ts">
import NetLogoFileIcon from '@/components/ui/NetLogoFileIcon.vue';
import { useSeo } from '@/composables/useSeo.js';

useSeo({
  title: 'NetLogo Now Has a New Model Converter',
  description:
    'NetLogo 7 introduces the XML-based .nlogox model format. The official, free NetLogo Auto-Converter upgrades legacy .nlogo and .nlogo3d models from NetLogo 4, 5, and 6.',
  path: '/news/announcing-netlogo-auto-converter',
});

const litPatches = [
  { top: '2.5rem', left: '12.5rem' },
  { top: '7.5rem', left: '20rem' },
  { top: '15rem', left: '7.5rem' },
  { top: '5rem', left: '57.5rem' },
  { top: '12.5rem', left: '65rem' },
  { top: '20rem', left: '70rem' },
  { top: '22.5rem', left: '17.5rem' },
];

const heroTurtles = [
  { top: '4.5rem', left: '16rem', color: '#e2584d', rotate: '64deg', delay: '0s' },
  { top: '13rem', left: '9.5rem', color: '#58b245', rotate: '152deg', delay: '1.4s' },
  { top: '21rem', left: '21rem', color: '#8b6bc7', rotate: '-38deg', delay: '2.6s' },
  { top: '6.5rem', left: '62rem', color: '#f0a23c', rotate: '-115deg', delay: '0.8s' },
  { top: '15.5rem', left: '69.5rem', color: '#42b8dd', rotate: '23deg', delay: '2s' },
  { top: '22rem', left: '61rem', color: '#e2584d', rotate: '170deg', delay: '3.2s' },
];

const legacyVersions = [
  { version: '4', years: '2007' },
  { version: '5', years: '2012' },
  { version: '6', years: '2016' },
];

interface CodeSegment {
  text: string;
  cls?: string;
}

interface DiffLine {
  kind: 'ctx' | 'del' | 'add';
  segments: CodeSegment[];
}

// NetLogo-style syntax colors: keywords green, commands blue, reporters purple.
const kw = 'text-emerald-700';
const cmd = 'text-blue-700';
const rep = 'text-violet-700';

const diffLines: DiffLine[] = [
  {
    kind: 'ctx',
    segments: [{ text: 'to ', cls: kw }, { text: 'go' }],
  },
  {
    kind: 'del',
    segments: [
      { text: '  ' },
      { text: 'foreach ', cls: cmd },
      { text: 'sort ', cls: rep },
      { text: 'turtles ', cls: rep },
      { text: '[ ' },
      { text: 'ask ', cls: cmd },
      { text: '? [ wiggle ] ]' },
    ],
  },
  {
    kind: 'add',
    segments: [
      { text: '  ' },
      { text: 'foreach ', cls: cmd },
      { text: 'sort ', cls: rep },
      { text: 'turtles ', cls: rep },
      { text: '[ t -> ' },
      { text: 'ask ', cls: cmd },
      { text: 't [ wiggle ] ]' },
    ],
  },
  {
    kind: 'ctx',
    segments: [{ text: '  ' }, { text: 'tick', cls: cmd }],
  },
  {
    kind: 'ctx',
    segments: [{ text: 'end', cls: kw }],
  },
];

const guarantees = [
  {
    icon: 'mdi:layers-triple-outline',
    title: 'Batch conversion',
    body: 'Drop in one model or a whole folder of them, then download everything at once.',
  },
  {
    icon: 'mdi:pencil-outline',
    title: 'Automatic code rewriting',
    body: 'Where the NetLogo language has evolved, your code evolves with it. The converter rewrites old syntax to keep your model working in NetLogo 7 and NetLogo Web.',
  },
  {
    icon: 'mdi:shield-check-outline',
    title: 'Originals untouched',
    body: 'Your .nlogo files are never modified. You always keep the source.',
  },
  {
    icon: 'mdi:gift-outline',
    title: 'Free, official, in-browser',
    body: 'Built by the NetLogo team. No install, no account, no cost.',
  },
];

const ctaTurtles = [
  { top: '1.5rem', left: '8%', rotate: '48deg', delay: '0s' },
  { top: '70%', left: '16%', rotate: '-130deg', delay: '1.8s' },
  { top: '30%', left: '88%', rotate: '-20deg', delay: '0.9s' },
  { top: '75%', left: '80%', rotate: '142deg', delay: '2.7s' },
];
</script>

<template>
  <div class="w-full overflow-x-hidden">
    <!-- Hero: the legacy → modern file morph, on a NetLogo world grid -->
    <section class="relative px-6 pt-16 pb-20 sm:pt-20 sm:pb-24">
      <div class="absolute inset-0 overflow-hidden" aria-hidden="true">
        <div class="hero-canvas absolute left-1/2 top-0 h-full w-7xl -translate-x-1/2">
          <div class="patch-grid absolute inset-0" />
          <div
            v-for="(patch, index) in litPatches"
            :key="`patch-${index}`"
            class="absolute size-10 bg-primary/[0.07]"
            :style="{ top: patch.top, left: patch.left }"
          />
          <div
            v-for="(turtle, index) in heroTurtles"
            :key="`turtle-${index}`"
            class="turtle absolute"
            :style="{
              top: turtle.top,
              left: turtle.left,
              background: turtle.color,
              '--turtle-rotate': turtle.rotate,
              animationDelay: turtle.delay,
            }"
          />
        </div>
      </div>

      <div class="relative mx-auto flex max-w-3xl flex-col items-center gap-8 text-center">
        <p class="rise family-mono text-xs font-medium uppercase tracking-[0.25em] text-primary">
          Announcement · NetLogo 7
        </p>

        <h1 class="rise text-4xl font-bold tracking-tight text-highlighted sm:text-5xl" style="animation-delay: 0.06s">
          NetLogo has a new<br class="hidden sm:inline" />
          model converter.
        </h1>

        <div
          class="rise flex items-end justify-center gap-4 py-2 sm:gap-8"
          style="animation-delay: 0.12s"
        >
          <div class="flex flex-col items-center gap-2">
            <NetLogoFileIcon variant="old" class="float-old h-16! px-0! drop-shadow-md sm:h-24!" />
            <code class="family-mono text-xs text-muted sm:text-sm">.nlogo</code>
          </div>
          <div
            class="conversion-chevrons family-mono pb-8 text-2xl font-bold text-primary select-none sm:pb-12 sm:text-3xl"
            aria-hidden="true"
          >
            <span>&gt;</span><span>&gt;</span><span>&gt;</span>
          </div>
          <div class="flex flex-col items-center gap-2">
            <div class="relative">
              <div class="absolute -inset-3 rounded-full bg-primary/20 blur-xl" aria-hidden="true" />
              <NetLogoFileIcon variant="new" class="float-new relative h-16! px-0! drop-shadow-lg sm:h-24!" />
            </div>
            <code class="family-mono text-xs font-bold text-primary sm:text-sm">.nlogox</code>
          </div>
        </div>

        <p class="rise max-w-xl text-base leading-relaxed text-muted sm:text-lg" style="animation-delay: 0.18s">
          NetLogo 7 introduces a modern, XML-based model format. The official Auto-Converter upgrades your legacy
          <code class="family-mono text-sm text-highlighted">.nlogo</code> and
          <code class="family-mono text-sm text-highlighted">.nlogo3d</code> models to
          <code class="family-mono text-sm text-highlighted">.nlogox</code>: free, in your browser, no install.
        </p>

        <div class="rise flex flex-wrap items-center justify-center gap-3" style="animation-delay: 0.24s">
          <UButton to="/" icon="mdi:open-in-app" size="xl" color="primary" label="Open the converter" />
          <UButton
            to="/about"
            icon="mdi:information-outline"
            size="xl"
            color="neutral"
            variant="subtle"
            label="How it works"
          />
        </div>
      </div>
    </section>

    <!-- Version timeline: two decades of models, one upgrade path -->
    <section class="px-6 pb-20 sm:pb-24" aria-labelledby="timeline-heading">
      <div class="mx-auto flex max-w-3xl flex-col items-center gap-8">
        <h2 id="timeline-heading" class="text-center text-xl font-semibold text-highlighted sm:text-2xl">
          Two decades of models, one upgrade path
        </h2>
        <div class="flex w-full items-center">
          <template v-for="legacy in legacyVersions" :key="legacy.version">
            <div class="flex flex-col items-center gap-1.5">
              <span
                class="family-mono flex size-9 items-center justify-center rounded-full border border-accented bg-default text-sm text-muted sm:size-11 sm:text-base"
              >
                {{ legacy.version }}
              </span>
              <span class="family-mono hidden text-[11px] text-dimmed sm:inline">{{ legacy.years }}</span>
            </div>
            <div class="timeline-connector mx-1 h-px mb-5 flex-1 sm:mx-2" aria-hidden="true" />
          </template>
          <div class="flex flex-col items-center gap-1.5">
            <span
              class="family-mono flex size-11 items-center justify-center rounded-full bg-primary text-base font-bold text-white shadow-lg shadow-primary/30 ring-4 ring-primary/15 sm:size-13 sm:text-lg"
            >
              7
            </span>
            <span class="family-mono hidden text-[11px] font-medium text-primary sm:inline">2025</span>
          </div>
        </div>
        <p class="max-w-xl text-center text-sm leading-relaxed text-muted">
          Models saved in <span class="font-medium text-highlighted">NetLogo 4, 5, or 6</span> convert straight to
          NetLogo 7. Old syntax is rewritten where the language has moved on and the result also runs in
          <span class="font-medium text-highlighted">NetLogo Web</span>.

          <br/> <br/>

          <span class="italic"><span class="text-red-500">*</span>Some features and primitives in NetLogo 7 are not supported in NetLogo Web.</span>
        </p>
      </div>
    </section>

    <section class="border-y border-default bg-default/60 px-6 py-20 sm:py-24" aria-labelledby="changes-heading">
      <div class="mx-auto grid max-w-5xl items-center gap-12 lg:grid-cols-2 lg:gap-16">
        <div class="flex flex-col gap-6">
          <p class="text-xs font-medium uppercase text-primary">What the converter does</p>
          <h2 id="changes-heading" class="text-2xl font-bold tracking-tight text-highlighted sm:text-3xl">
            Old syntax in.<br />
            NetLogo 7 out.
          </h2>
          <ul class="flex flex-col gap-5">
            <li v-for="guarantee in guarantees" :key="guarantee.title" class="flex gap-4">
              <UIcon :name="guarantee.icon" class="mt-0.5 size-6 shrink-0 text-primary" />
              <div class="flex flex-col gap-0.5">
                <span class="font-semibold text-highlighted">{{ guarantee.title }}</span>
                <span class="text-sm leading-relaxed text-muted">{{ guarantee.body }}</span>
              </div>
            </li>
          </ul>
        </div>

        <div class="flex flex-col gap-3">
          <div class="overflow-hidden rounded-xl bg-white shadow-xl ring-1 ring-default">
            <div class="flex items-center gap-1 border-b border-default bg-elevated/60 px-3 pt-2 text-xs">
              <span class="rounded-t-md px-3 py-1.5 text-dimmed">Interface</span>
              <span class="rounded-t-md px-3 py-1.5 text-dimmed">Info</span>
              <span
                class="rounded-t-md border-x border-t border-default bg-white px-3 py-1.5 font-medium text-primary"
              >
                Code
              </span>
              <code class="family-mono ml-auto hidden truncate py-1.5 text-[11px] text-dimmed sm:block">
                flocking.nlogo → flocking.nlogox
              </code>
            </div>
            <pre
              class="family-mono overflow-x-auto py-4 text-[13px] leading-7 text-gray-800"
            ><code><span
              v-for="(line, index) in diffLines"
              :key="index"
              class="block px-4"
              :class="{
                'bg-red-500/10 text-red-900/60 line-through decoration-red-400/60': line.kind === 'del',
                'bg-amber-300/30': line.kind === 'add',
              }"
            ><span class="mr-3 inline-block w-3 select-none text-dimmed">{{ line.kind === 'del' ? '−' : line.kind === 'add' ? '+' : '' }}</span><span
                v-for="(segment, segmentIndex) in line.segments"
                :key="segmentIndex"
                :class="line.kind === 'del' ? undefined : segment.cls"
              >{{ segment.text }}</span></span></code></pre>
          </div>
          <p class="flex items-start gap-2 px-1 text-xs leading-relaxed text-muted sm:text-sm mt-5">
            <UIcon name="mdi:pencil-outline" class="mt-0.5 size-4 shrink-0 text-amber-600" />
            <span>
              Where the language changed, like NetLogo 5's tasks, code is rewritten automatically.
            </span>
          </p>
        </div>
      </div>
    </section>

    <!-- Closing CTA: a NetLogo world, in NetLogo blue -->
    <section class="px-6 py-20 sm:py-24" aria-labelledby="cta-heading">
      <div class="relative mx-auto max-w-5xl overflow-hidden rounded-2xl bg-primary px-6 py-14 shadow-2xl shadow-primary/25 sm:px-12 sm:py-16">
        <div class="cta-grid absolute inset-0" aria-hidden="true" />
        <div
          v-for="(turtle, index) in ctaTurtles"
          :key="`cta-turtle-${index}`"
          class="turtle absolute hidden bg-white/25 sm:block"
          :style="{ top: turtle.top, left: turtle.left, '--turtle-rotate': turtle.rotate, animationDelay: turtle.delay }"
          aria-hidden="true"
        />

        <div class="relative flex flex-col items-center gap-6 text-center">
          <h2 id="cta-heading" class="text-3xl font-bold tracking-tight text-white sm:text-4xl">
            Ready when your models are.
          </h2>
          <p class="max-w-xl text-base leading-relaxed text-blue-100">
            Converted <code class="family-mono text-white">.nlogox</code> files open directly in NetLogo 7 on the
            desktop and in NetLogo Web: convert one model or a hundred.
          </p>
          <UButton
            to="/"
            icon="mdi:upload"
            size="xl"
            color="neutral"
            class="bg-white text-primary hover:bg-blue-50 active:bg-blue-100"
            label="Convert a model now"
          />
          <div class="mt-2 flex items-center gap-6">
            <span class="flex items-center gap-2.5">
              <img
                src="/assets/brands/netlogo-desktop-fill.webp"
                alt="NetLogo 7 desktop application logo"
                class="size-10 rounded-full ring-2 ring-white/40 bg-white p-1"
                loading="lazy"
              />
              <span class="text-sm font-medium text-blue-50">NetLogo 7</span>
            </span>
            <span class="h-5 w-px bg-white/30" aria-hidden="true" />
            <span class="flex items-center gap-2.5">
              <img
                src="/assets/brands/netlogo-web-fill.webp"
                alt="NetLogo Web logo"
                class="size-10 rounded-full ring-2 ring-white/40 bg-white p-1"
                loading="lazy"
              />
              <span class="text-sm font-medium text-blue-50">NetLogo Web</span>
            </span>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
.hero-canvas {
  mask-image: radial-gradient(ellipse 75% 80% at 50% 35%, black 25%, transparent 72%);
}

.patch-grid {
  background-image:
    linear-gradient(to right, color-mix(in oklab, var(--ui-primary) 14%, transparent) 1px, transparent 1px),
    linear-gradient(to bottom, color-mix(in oklab, var(--ui-primary) 14%, transparent) 1px, transparent 1px);
  background-size: 2.5rem 2.5rem;
}

.cta-grid {
  background-image:
    linear-gradient(to right, rgb(255 255 255 / 0.09) 1px, transparent 1px),
    linear-gradient(to bottom, rgb(255 255 255 / 0.09) 1px, transparent 1px);
  background-size: 2.5rem 2.5rem;
}

.turtle {
  width: 16px;
  height: 19px;
  clip-path: polygon(50% 0%, 100% 100%, 50% 72%, 0% 100%);
  transform: rotate(var(--turtle-rotate, 0deg));
  animation: turtle-drift 6s ease-in-out infinite alternate;
}

@keyframes turtle-drift {
  from {
    transform: rotate(var(--turtle-rotate, 0deg)) translateY(0);
  }
  to {
    transform: rotate(var(--turtle-rotate, 0deg)) translateY(-14px);
  }
}

/* Gentle float on the hero file icons */
.float-old,
.float-new {
  animation: file-float 5s ease-in-out infinite alternate;
}

.float-new {
  animation-delay: 1.2s;
}

@keyframes file-float {
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(-6px);
  }
}

.timeline-connector {
  background-image: linear-gradient(
    to right,
    var(--ui-border-accented) 0 6px,
    transparent 6px 12px
  );
  background-size: 12px 1px;
  background-repeat: repeat-x;
}

@media (prefers-reduced-motion: reduce) {
  .rise,
  .turtle,
  .conversion-chevrons span,
  .float-old,
  .float-new {
    animation: none;
    opacity: 1;
    transform: rotate(var(--turtle-rotate, 0deg));
  }
}
</style>
