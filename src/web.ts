import { WebPlugin } from '@capacitor/core';

import type { FacebookSharePlugin } from './definitions';

export class FacebookShareWeb extends WebPlugin implements FacebookSharePlugin {

    async sharePhotoToFeed(options: { base64Image: string }): Promise<{ status: string }> {
        if(!options) Promise.reject('Photo sharing is only supported on native platforms.');
        
        console.error('Photo sharing is not supported on web.');
        return Promise.reject('Photo sharing is only supported on native platforms.');
    }
}
