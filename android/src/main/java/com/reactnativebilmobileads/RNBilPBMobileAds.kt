package com.reactnativebilmobileads

import com.bil.bilmobileads.PBMobileAds
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class RNBilPBMobileAds(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private var isPluginInit: Boolean = false

  fun pluginInit(): Boolean {
    if (!isPluginInit) Utils.log("PBMobileAds uninitialized, please call PBMobileAds.initialize() first.")
    return isPluginInit
  }

  override fun getName(): String {
    return Utils.PBM
  }

  @ReactMethod
  fun initialize(testMode: Boolean) {
    currentActivity?.runOnUiThread {
      run {
        PBMobileAds.instance.initialize(currentActivity, testMode)
        isPluginInit = true
      }
    }
  }

  @ReactMethod
  fun enableCOPPA() {
    if (!pluginInit()) return

    currentActivity?.runOnUiThread {
      run {
        PBMobileAds.instance.enableCOPPA()
      }
    }
  }

  @ReactMethod
  fun disableCOPPA() {
    if (!pluginInit()) return

    currentActivity?.runOnUiThread {
      run {
        PBMobileAds.instance.disableCOPPA()
      }
    }
  }

  @ReactMethod
  fun setYearOfBirth(yearOfBirth: Int) {
    if (!pluginInit()) return

    currentActivity?.runOnUiThread {
      run {
        PBMobileAds.instance.setYearOfBirth(yearOfBirth)
      }
    }
  }

  @ReactMethod
  fun setGender(gender: Int) {
    if (!pluginInit()) return

    currentActivity?.runOnUiThread {
      run {
        when (gender) {
          0 -> PBMobileAds.instance.setGender(PBMobileAds.GENDER.UNKNOWN)
          1 -> PBMobileAds.instance.setGender(PBMobileAds.GENDER.MALE)
          2 -> PBMobileAds.instance.setGender(PBMobileAds.GENDER.FEMALE)
          else -> PBMobileAds.instance.setGender(PBMobileAds.GENDER.UNKNOWN)
        }
      }
    }
  }

}
