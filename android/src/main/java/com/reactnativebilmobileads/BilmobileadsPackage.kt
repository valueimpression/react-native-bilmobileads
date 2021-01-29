package com.reactnativebilmobileads

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager


class BilmobileadsPackage : ReactPackage {
  override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
    return listOf(
      RNBilPBMobileAds(reactContext),
      RNBilAdInterstitial(reactContext),
      RNBilAdRewarded(reactContext),
      BilmobileadsModule(reactContext)
    )
  }

  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return listOf(RNBilAdBanner(reactContext))
  }
}
