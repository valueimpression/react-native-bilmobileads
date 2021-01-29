//
//  RNBilAdInterstitial.swift
//  react-native-bilmobileads
//
//  Created by HNL_MAC on 26/01/2021.
//

import BilMobileAds

@objc(RNBilAdInterstitial)
class RNBilAdInterstitial: RCTEventEmitter, ADInterstitialDelegate {
    
    let INTERSTITIAL = "RNBilAdInterstitial"
    var hasListeners: Bool = false
    
    private var interstitial: ADInterstitial!
    
    func log(mess: String) {
        print("RNPBMobileAds -> \(mess)")
    }

    @objc(create:)
    func create(adUnitID: NSString) {
        let adUnitIdStr = String(adUnitID)
        
        if (adUnitIdStr.isEmpty) {
            log(mess: "adUnitID expected an string value and not empty")
            return
        }
        
        if self.interstitial != nil {
            self.interstitial.destroy()
            self.interstitial = nil
        }
        
        DispatchQueue.main.async {
            self.interstitial = ADInterstitial(RNBilPBMobileAds.getUIViewController(), placement: adUnitIdStr)
            self.interstitial.setListener(self)
        }
    }
    
    @objc(preLoad)
    func preLoad() {
        guard let interstitial = self.interstitial else {
            log(mess: "ADInterstitial is null. You need init ADInterstitial first.")
            return
        }
        
        DispatchQueue.main.async {
            if (interstitial.isReady()) {
                self.log(mess: "ADInterstitial is ready to show.")
            } else {
                interstitial.preLoad()
            }
        }
    }
    
    @objc(show)
    func show() {
        guard let interstitial = self.interstitial else {
            log(mess: "ADInterstitial is null. You need init ADInterstitial first.")
            return
        }
        
        DispatchQueue.main.async {
            if (!interstitial.isReady()) {
                self.log(mess: "ADInterstitial currently unavailable, call preload() first.")
            } else {
                interstitial.show()
            }
        }
    }
    
    @objc(destroy)
    func destroy() {
        if self.interstitial == nil {
            log(mess: "ADInterstitial is null. You need init ADInterstitial first.")
            return
        }
        
        DispatchQueue.main.async {
            self.interstitial.destroy()
            self.interstitial = nil
        }
    }
    
    @objc(isReady:rejecter:)
    func isReady(_ resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
        guard let interstitial = self.interstitial else {
            resolve(false)
            return
        }
        resolve(interstitial.isReady())
    }
    
    override func supportedEvents() -> [String]! {
        return [self.INTERSTITIAL]
    }
    
    override func startObserving() {
        hasListeners = true
    }
    
    override func stopObserving() {
        hasListeners = false
    }
    
    func sendEvent(body: Any) {
        if hasListeners {
            self.sendEvent(withName: self.INTERSTITIAL, body: body)
        }
    }
    
    func interstitialDidReceiveAd() {
        sendEvent(body: ["type": "loaded", "message": nil])
    }
    
    func interstitialLoadFail(error: String) {
        sendEvent(body: ["type": "failedToLoad", "message": error])
    }
    
    func interstitialWillPresentScreen() {
        sendEvent(body: ["type": "opened", "message": nil])
    }
    
    func interstitialDidFailToPresentScreen() {
        sendEvent(body: ["type": "failedToShow", "message": nil])
    }
    
    func interstitialWillDismissScreen() {}
    
    func interstitialDidDismissScreen() {
        sendEvent(body: ["type": "closed", "message": nil])
    }
    
    func interstitialWillLeaveApplication() {
        sendEvent(body: ["type": "clicked", "message": nil])
        sendEvent(body: ["type": "leftApplication", "message": nil])
    }
}
