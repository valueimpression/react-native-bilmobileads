//
//  RNBilAdRewarded.swift
//  react-native-bilmobileads
//
//  Created by HNL_MAC on 26/01/2021.
//

import BilMobileAds

@objc(RNBilAdRewarded)
class RNBilAdRewarded: RCTEventEmitter, ADRewardedDelegate {
    
    let REWARDED = "RNBilAdRewarded"
    var hasListeners: Bool = false
    
    private var rewarded: ADRewarded!
    
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
        
        if self.rewarded != nil {
            self.rewarded.destroy()
            self.rewarded = nil
        }
        
        DispatchQueue.main.async {
            self.rewarded = ADRewarded(RNBilPBMobileAds.getUIViewController(), placement: adUnitIdStr)
            self.rewarded.setListener(self)
        }
    }
    
    @objc(preLoad)
    func preLoad() {
        guard let rewarded = self.rewarded else {
            log(mess: "ADRewarded is null. You need init ADInterstitial first.")
            return
        }
        
        DispatchQueue.main.async {
            if (rewarded.isReady()) {
                self.log(mess: "ADRewarded is ready to show")
            } else {
                rewarded.preLoad()
            }
        }
    }
    
    @objc(show)
    func show() {
        guard let rewarded = self.rewarded else {
            log(mess: "ADRewarded is null. You need init ADRewarded first.")
            return
        }
        
        DispatchQueue.main.async {
            if (!rewarded.isReady()) {
                self.log(mess: "ADRewarded currently unavailable, call preload() first.")
            } else {
                rewarded.show()
            }
        }
    }
    
    @objc(destroy)
    func destroy() {
        if self.rewarded == nil {
            log(mess: "ADRewarded is null. You need init ADRewarded first.")
            return
        }
        
        DispatchQueue.main.async {
            self.rewarded.destroy()
            self.rewarded = nil
        }
    }
    
    @objc(isReady:rejecter:)
    func isReady(_ resolve: RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
        guard let rewarded = self.rewarded else {
            resolve(false)
            return
        }
        resolve(rewarded.isReady())
    }
    
    override func supportedEvents() -> [String]! {
        return [self.REWARDED]
    }
    
    override func startObserving() {
        hasListeners = true
    }
    
    override func stopObserving() {
        hasListeners = false
    }
    
    func sendEvent(body: Any) {
        if hasListeners {
            self.sendEvent(withName: self.REWARDED, body: body)
        }
    }
    
    func rewardedDidReceiveAd() {
        sendEvent(body: ["type": "loaded", "message": nil])
    }
    
    func rewardedDidPresent() {
        sendEvent(body: ["type": "opened", "message": nil])
    }
    
    func rewardedDidDismiss() {
        sendEvent(body: ["type": "closed", "message": nil])
    }
    
    func rewardedUserDidEarn(rewardedItem: ADRewardedItem) {
        sendEvent(body: ["type": "rewarded", "typeRewarded": rewardedItem.getType(), "amountRewarded": rewardedItem.getAmount()])
    }
    
    func rewardedFailedToLoad(error: String) {
        sendEvent(body: ["type": "failedToLoad", "message": error])
    }
    
    func rewardedFailedToPresent(error: String) {
        sendEvent(body: ["type": "failedToShow", "message": nil])
    }
}
