// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFacebookShare",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorFacebookShare",
            targets: ["CapacitorFacebookSharePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0"),
        .package(url: "https://github.com/facebook/facebook-ios-sdk.git", .upToNextMajor(from: "17.4.0"))
    ],
    targets: [
        .target(
            name: "CapacitorFacebookSharePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm"),
                .product(name: "FacebookCore", package: "facebook-ios-sdk"),
                .product(name: "FacebookShare", package: "facebook-ios-sdk")
            ],
            path: "ios/Sources/CapacitorFacebookSharePlugin"),
        .testTarget(
            name: "CapacitorFacebookSharePluginTests",
            dependencies: ["CapacitorFacebookSharePlugin"],
            path: "ios/Tests/CapacitorFacebookSharePluginTests")
    ]
)
