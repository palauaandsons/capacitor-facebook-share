import { registerPlugin } from '@capacitor/core';

import type { FacebookSharePlugin } from './definitions';

const FacebookShare = registerPlugin<FacebookSharePlugin>('FacebookShare', {
  web: () => import('./web').then((m) => new m.FacebookShareWeb()),
});

export * from './definitions';
export { FacebookShare };
