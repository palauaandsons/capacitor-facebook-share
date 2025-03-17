package com.faithyapp.capacitor.plugins.facebook.share;

import android.content.Intent;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

@CapacitorPlugin(name = "FacebookShare")
public class CapacitorFacebookSharePlugin extends Plugin {
    private static final String TAG = "FBSharePlugin";
    private CapacitorFacebookShare implementation;

    /**
     * Interface for Facebook sharing callbacks
     */
    interface ShareCallback {
        void success();
        void error(String message);
    }

    @Override
    public void load() {
        implementation = new CapacitorFacebookShare();
    }

    @PluginMethod
    public void sharePhotoToFeed(final PluginCall call) {
        if (!call.hasOption("base64Image")) {
            call.reject("Must provide a base64Image parameter");
            return;
        }

        String base64Image = call.getString("base64Image");
        if (base64Image == null || base64Image.isEmpty()) {
            call.reject("base64Image cannot be empty");
            return;
        }

        getBridge().executeOnMainThread(() -> {
            implementation.sharePhotoToFeed(getActivity(), base64Image, new ShareCallback() {
                @Override
                public void success() {
                    JSObject result = new JSObject();
                    result.put("status", "success");
                    call.resolve(result);
                }

                @Override
                public void error(String message) {
                    call.reject(message);
                }
            });
        });
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        if (implementation != null) {
            implementation.onActivityResult(requestCode, resultCode, data);
        }
    }
}