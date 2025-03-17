# capacitor-facebook-share

Facebook Share plugin for Capacitor

## Install

```bash
npm install capacitor-facebook-share
npx cap sync
```

## API

<docgen-index>

* [`sharePhotoToFeed(...)`](#sharephototofeed)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### sharePhotoToFeed(...)

```typescript
sharePhotoToFeed(options: { base64Image: string; }) => Promise<{ status: string; }>
```

Share a photo to Facebook Feed.

| Param         | Type                                  | Description                                     |
| ------------- | ------------------------------------- | ----------------------------------------------- |
| **`options`** | <code>{ base64Image: string; }</code> | Object containing the photo as a base64 string. |

**Returns:** <code>Promise&lt;{ status: string; }&gt;</code>

--------------------

</docgen-api>
