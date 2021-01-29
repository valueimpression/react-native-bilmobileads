//
//  RNBilPBMobileAds.swift
//  react-native-bilmobileads
//
//  Created by HNL_MAC on 26/01/2021.
//

import BilMobileAds

@objc(RNBilPBMobileAds)
class RNBilPBMobileAds: NSObject {
    
    @objc static func getUIViewController() -> UIViewController {
        return (UIApplication.shared.keyWindow?.rootViewController)!
    }
    
    @objc(initialize:)
    func initialize(testMode: Bool) {
        DispatchQueue.main.async {
            PBMobileAds.shared.initialize(testMode: testMode)
        }
    }
    
    @objc
    func enableCOPPA() {
        PBMobileAds.shared.enableCOPPA()
    }
    
    @objc
    func disableCOPPA() {
        PBMobileAds.shared.disableCOPPA()
    }
    
    @objc(setYearOfBirth:)
    func setYearOfBirth(yearOfBirth: Double) {
        PBMobileAds.shared.setYearOfBirth(yob: Int(yearOfBirth))
    }
    
    @objc(setGender:)
    func setGender(gender: Double) {
        switch Int(gender) {
        case 0:
            PBMobileAds.shared.setGender(gender: .unknown);
        case 1:
            PBMobileAds.shared.setGender(gender: .male);
        case 2:
            PBMobileAds.shared.setGender(gender: .female);
        default:
            PBMobileAds.shared.setGender(gender: .unknown);
        }
    }
}
