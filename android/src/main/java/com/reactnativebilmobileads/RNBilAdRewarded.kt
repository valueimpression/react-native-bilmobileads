package com.reactnativebilmobileads

import android.os.Handler
import android.os.Looper
import com.bil.bilmobileads.ADRewarded
import com.facebook.react.bridge.*

class RNBilAdRewarded(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private lateinit var rewarded: ADRewarded

  override fun getName(): String {
    return Utils.REWARDED
  }

  @ReactMethod
  fun create(adUnitID: String) {
    if (adUnitID == "") {
      Utils.log("adUnitID expected an string value and not empty")
      return
    }
    if (currentActivity == null) {
      Utils.log("Rewarded ad attempted to load but the current Activity was null.")
      return
    }

    Handler(Looper.getMainLooper()).post {
      run {
        val rewarded = ADRewarded(currentActivity, adUnitID)
        rewarded.setListener(Utils.createAdRewardedListener(reactApplicationContext, Utils.REWARDED))
      }
    }
  }

  @ReactMethod
  fun preLoad() {
    Handler(Looper.getMainLooper()).post {
      run {
        if (rewarded.isReady) {
          Utils.log("AdRewarded is ready to show.")
        } else {
          rewarded.preLoad()
        }
      }
    }
  }

  @ReactMethod
  fun show() {
    Handler(Looper.getMainLooper()).post {
      run {
        if (!rewarded.isReady) {
          Utils.log("AdRewarded currently unavailable, call preload() first.")
        } else {
          rewarded.show()
        }
      }
    }
  }

  @ReactMethod
  fun destroy() {
    Handler(Looper.getMainLooper()).post {
      run {
        rewarded.destroy()
      }
    }
  }

  @ReactMethod
  fun isReady(callBack: Promise) {
    if (rewarded == null) {
      callBack.resolve(false)
      return
    }

    Handler(Looper.getMainLooper()).post {
      run {
        callBack.resolve(rewarded.isReady)
      }
    }
  }
}
