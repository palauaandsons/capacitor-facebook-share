import { WebPlugin } from '@capacitor/core';

import type { FacebookSharePlugin } from './definitions';

export class FacebookShareWeb extends WebPlugin implements FacebookSharePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
