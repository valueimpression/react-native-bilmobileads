package com.reactnativebilmobileads

import android.os.Handler
import android.os.Looper
import com.bil.bilmobileads.ADInterstitial
import com.facebook.react.bridge.*

class RNBilAdInterstitial(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private lateinit var interstitial: ADInterstitial

  override fun getName(): String {
    return Utils.INTERSTITIAL
  }

  @ReactMethod
  fun create(adUnitID: String) {
    if (adUnitID == "") {
      Utils.log("adUnitID expected an string value and not empty")
      return
    }

    Handler(Looper.getMainLooper()).post {
      run {
        interstitial = ADInterstitial(adUnitID)
        interstitial.setListener(Utils.createAdListener(reactApplicationContext, Utils.INTERSTITIAL))
      }
    }
  }

  @ReactMethod
  fun preLoad() {
    Handler(Looper.getMainLooper()).post {
      run {
        if (interstitial.isReady) {
          Utils.log("ADInterstitial is ready to show.")
        } else {
          interstitial.preLoad()
        }
      }
    }
  }

  @ReactMethod
  fun show() {
    Handler(Looper.getMainLooper()).post {
      run {
        if (!interstitial.isReady) {
          Utils.log("ADInterstitial currently unavailable, call preload() first.")
        } else {
          interstitial.show()
        }
      }
    }
  }

  @ReactMethod
  fun destroy() {
    Handler(Looper.getMainLooper()).post {
      run {
        interstitial.destroy()
      }
    }
  }

  @ReactMethod
  fun isReady(callBack: Promise) {
    Handler(Looper.getMainLooper()).post {
      run {
        callBack.resolve(interstitial.isReady)
      }
    }
  }
}
