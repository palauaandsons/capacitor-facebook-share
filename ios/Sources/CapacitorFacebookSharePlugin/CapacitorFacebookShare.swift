import UIKit

#if canImport(FBSDKShareKit)
import FBSDKCoreKit
import FBSDKShareKit
#else
import FacebookCore
import FacebookShare
#endif

@objc public class CapacitorFacebookShare: NSObject {
    @objc public func sharePhotoToFeed(image: UIImage, completion: @escaping (Bool, String?) -> Void) {
        DispatchQueue.main.async {
            guard let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
                  let viewController = windowScene.windows.first?.rootViewController else {
                completion(false, "No root view controller found")
                return
            }
            
            let photo = SharePhoto(image: image, isUserGenerated: true)
            photo.caption = nil // Optional caption
            
            let content = SharePhotoContent()
            content.photos = [photo]
            
            let dialog = ShareDialog(viewController: viewController, content: content, delegate: nil)
            dialog.mode = .native // Force native Facebook app mode for photos
            
            // Check if dialog can be shown
            if dialog.canShow {
                dialog.show()
                completion(true, nil)
            } else {
                completion(false, "Facebook app must be installed to share photos.")
            }
        }
    }
}
