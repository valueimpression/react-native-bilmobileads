//
//  RNAdBannerView.swift
//  react-native-bilmobileads
//
//  Created by HNL_MAC on 29/01/2021.
//

import React
import BilMobileAds

class RNAdBannerView: UIView, ADBannerDelegate {
    
    private var _bannerView: ADBanner!
    
    @objc var adUnitId: String = "" {
        didSet {
            self.create()
        }
    }
    @objc var onNativeEvent: RCTBubblingEventBlock?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func create() {
        _bannerView = ADBanner(RNBilPBMobileAds.getUIViewController(), view: self, placement: adUnitId)
        _bannerView.setListener(self)
    }
    
    public func load() {
        _bannerView?.load()
    }
    
    public func show() {
        self.isHidden = false
//        _bannerView.getADView().isHidden = false
        _bannerView?.startFetchData()
    }
    
    public func hide() {
        self.isHidden = true
//        _bannerView.getADView().isHidden = true
        _bannerView?.stopFetchData()
    }
    
    public func destroy() {
        _bannerView?.destroy()
    }
    
    // MARK: - ADBannerDelegate
    func bannerDidReceiveAd() {
        let bannerSize = _bannerView.getADView().bounds.size
        self.onNativeEvent?(["type": "loaded", "message": "", "width": bannerSize.width, "height": bannerSize.height])
    }
    
    func bannerWillPresentScreen() {
        self.onNativeEvent?(["type": "opened", "message": ""])
    }
    
    func bannerWillDismissScreen() {}
    
    func bannerDidDismissScreen() {
        self.onNativeEvent?(["type": "closed", "message": ""])
    }
    
    func bannerWillLeaveApplication() {
        self.onNativeEvent?(["type": "clicked", "message": ""])
        self.onNativeEvent?(["type": "leftApplication", "message": ""])
    }
    
    func bannerLoadFail(error: String) {
        self.onNativeEvent?(["type": "failedToLoad", "message": error])
    }
}
