import Foundation
import Capacitor
import UIKit

@objc(CapacitorFacebookSharePlugin)
public class CapacitorFacebookSharePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CapacitorFacebookSharePlugin"
    public let jsName = "FacebookShare"  // This should match the name in index.ts
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "sharePhotoToFeed", returnType: CAPPluginReturnPromise)
    ]

    private let implementation = CapacitorFacebookShare()

    @objc func sharePhotoToFeed(_ call: CAPPluginCall) {
        guard let base64Image = call.getString("base64Image") else {
            call.reject("Must provide a valid base64 image string as base64Image parameter")
            return
        }
        
        // Process the base64 string to handle data URLs
        var cleanBase64 = base64Image
        if base64Image.hasPrefix("data:image") {
            if let commaIndex = base64Image.firstIndex(of: ",") {
                cleanBase64 = String(base64Image[base64Image.index(after: commaIndex)...])
            }
        }
        
        guard let imageData = Data(base64Encoded: cleanBase64),
              let image = UIImage(data: imageData) else {
            call.reject("Failed to decode base64 image string")
            return
        }

        implementation.sharePhotoToFeed(image: image) { success, error in
            if success {
                call.resolve(["status": "success"])
            } else {
                call.reject(error ?? "Unknown error occurred")
            }
        }
    }
}
