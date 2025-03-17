package com.faithyapp.capacitor.plugins.facebook.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

public class CapacitorFacebookShare {
    private static final String TAG = "CapacitorFBShare";
    private CallbackManager callbackManager;

    public CapacitorFacebookShare() {
        try {
            callbackManager = CallbackManager.Factory.create();
        } catch (Exception e) {
            Log.e(TAG, "Error creating CallbackManager: " + e.getMessage());
        }
    }

    public void sharePhotoToFeed(Activity activity, String base64Image, CapacitorFacebookSharePlugin.ShareCallback callback) {
        try {
            // Initialize Facebook SDK on-demand
            initializeFacebookSDK(activity);

            // Convert base64 to bitmap
            Bitmap image = base64ToBitmap(base64Image);
            if (image == null) {
                callback.error("Failed to convert base64 to bitmap");
                return;
            }

            // Create Facebook photo
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(image)
                    .setUserGenerated(true)
                    .build();

            // Create content
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();

            // Check if native app is installed
            ShareDialog shareDialog = new ShareDialog(activity);
            if (ShareDialog.canShow(SharePhotoContent.class)) {
                // Register callback
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Log.d(TAG, "Facebook share success");
                        callback.success();
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Facebook share cancelled");
                        callback.error("Share cancelled by user");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e(TAG, "Facebook share error: " + error.getMessage());
                        callback.error(error.getMessage());
                    }
                });

                // Show the dialog
                shareDialog.show(content);
            } else {
                callback.error("Facebook app must be installed to share photos");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error sharing to Facebook: " + e.getMessage(), e);
            callback.error("Error sharing to Facebook: " + e.getMessage());
        }
    }

    // Initialize Facebook SDK on-demand
    private void initializeFacebookSDK(Activity activity) {
        try {
            if (!FacebookSdk.isInitialized()) {
                Log.d(TAG, "Initializing Facebook SDK on-demand");

                // Get app ID from resources
                String appId = activity.getString(
                        activity.getResources().getIdentifier("facebook_app_id", "string", activity.getPackageName())
                );

                // Initialize with explicit app ID
                FacebookSdk.setApplicationId(appId);
                FacebookSdk.sdkInitialize(activity.getApplicationContext());

                // Make sure initialization completes
                FacebookSdk.fullyInitialize();

                Log.d(TAG, "Facebook SDK initialized successfully");

                // Re-create the callback manager after initialization
                callbackManager = CallbackManager.Factory.create();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Facebook SDK: " + e.getMessage(), e);
        }
    }

    private Bitmap base64ToBitmap(String base64Image) {
        try {
            // Handle data URL prefix if present
            String cleanBase64 = base64Image;
            if (base64Image.startsWith("data:image")) {
                cleanBase64 = base64Image.substring(base64Image.indexOf(",") + 1);
            }

            byte[] decodedBytes = Base64.decode(cleanBase64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            Log.e(TAG, "Error decoding base64: " + e.getMessage());
            return null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}