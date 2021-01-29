//
//  RNBilAdBanner.swift
//  react-native-bilmobileads
//
//  Created by HNL_MAC on 27/01/2021.
//

@objc(RNBilAdBanner)
class RNBilAdBanner: RCTViewManager {
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    override func view() -> UIView! {
        return RNAdBannerView()
    }
    
    @objc func load(_ node: NSNumber){
        DispatchQueue.main.async {
            let banner = self.bridge.uiManager.view(forReactTag: node) as! RNAdBannerView
            banner.load()
        }
    }
    
    @objc func show(_ node: NSNumber){
        DispatchQueue.main.async {
            let banner = self.bridge.uiManager.view(forReactTag: node) as! RNAdBannerView
            banner.show()
        }
    }
    
    @objc func hide(_ node: NSNumber){
        DispatchQueue.main.async {
            let banner = self.bridge.uiManager.view(forReactTag: node) as! RNAdBannerView
            banner.hide()
        }
    }
    
    @objc func destroy(_ node: NSNumber){
        DispatchQueue.main.async {
            let banner = self.bridge.uiManager.view(forReactTag: node) as! RNAdBannerView
            banner.destroy()
        }
    }
}
