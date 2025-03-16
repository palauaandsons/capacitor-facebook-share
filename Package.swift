// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorFacebookShare",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorFacebookShare",
            targets: ["FacebookSharePlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "FacebookSharePlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/FacebookSharePlugin"),
        .testTarget(
            name: "FacebookSharePluginTests",
            dependencies: ["FacebookSharePlugin"],
            path: "ios/Tests/FacebookSharePluginTests")
    ]
)