export interface FacebookSharePlugin {

    /**
     * Share a photo to Facebook Feed.
     * @param options Object containing the photo as a base64 string.
     */
    sharePhotoToFeed(options: { base64Image: string }): Promise<{ status: string }>;
}
