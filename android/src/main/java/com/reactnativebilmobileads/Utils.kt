package com.reactnativebilmobileads

import android.util.Log
import android.view.Gravity
import com.bil.bilmobileads.entity.ADRewardItem
import com.bil.bilmobileads.interfaces.AdDelegate
import com.bil.bilmobileads.interfaces.AdRewardedDelegate
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import javax.annotation.Nullable


class Utils {
  companion object {
    private const val BIL_LOG_TAG = "RNPBMobileAds"

    /**
     * Position constant for top of the screen.
     */
    val TOP_CENTER = 0

    /**
     * Position constant for top-left of the screen.
     */
    val TOP_LEFT = 1

    /**
     * Position constant for top-right of the screen.
     */
    val TOP_RIGHT = 2

    /**
     * Position constant for bottom of the screen.
     */
    val BOTTOM_CENTER = 3

    /**
     * Position constant for bottom-left of the screen.
     */
    val BOTTOM_LEFT = 4

    /**
     * Position constant bottom-right of the screen.
     */
    val BOTTOM_RIGHT = 5

    /**
     * Position constant center of the screen.
     */
    val CENTER = 6

    const val PBM = "RNBilPBMobileAds"
    const val BANNER = "RNBilAdBanner"
    const val INTERSTITIAL = "RNBilAdInterstitial"
    const val REWARDED = "RNBilAdRewarded"

    fun getGravityForPositionCode(positionCode: Int): Int {
      var gravity = 0
      gravity = when (positionCode) {
        TOP_CENTER -> Gravity.TOP or Gravity.CENTER_HORIZONTAL
        TOP_LEFT -> Gravity.TOP or Gravity.LEFT
        TOP_RIGHT -> Gravity.TOP or Gravity.RIGHT
        BOTTOM_CENTER -> Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        BOTTOM_LEFT -> Gravity.BOTTOM or Gravity.LEFT
        BOTTOM_RIGHT -> Gravity.BOTTOM or Gravity.RIGHT
        CENTER -> Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
        else -> throw IllegalArgumentException("Attempted to position ad with invalid ad "
          + "position.")
      }
      return gravity
    }


    fun log(mess: Any) {
      var messStr = mess.toString()
      Log.d(BIL_LOG_TAG, messStr)
    }

    fun sendEvent(reactContext: ReactApplicationContext, listenerName: String, @Nullable params: WritableMap?) {
      reactContext.getJSModule(RCTDeviceEventEmitter::class.java).emit(listenerName, params)
    }

    fun createAdListener(reactContext: ReactApplicationContext, listenerName: String): AdDelegate {
      return object : AdDelegate() {
        override fun onAdLoaded() {
          sendEvent(reactContext, listenerName, setMess("loaded", ""))
        }

        override fun onAdOpened() {
          sendEvent(reactContext, listenerName, setMess("opened", ""))
        }

        override fun onAdClosed() {
          sendEvent(reactContext, listenerName, setMess("closed", ""))
        }

        override fun onAdClicked() {
          sendEvent(reactContext, listenerName, setMess("clicked", ""))
        }

        override fun onAdLeftApplication() {
          sendEvent(reactContext, listenerName, setMess("leftApplication", ""))
        }

        override fun onAdFailedToLoad(errorCode: String) {
          sendEvent(reactContext, listenerName, setMess("failedToLoad", errorCode))
        }
      }
    }

    fun createAdRewardedListener(reactContext: ReactApplicationContext, listenerName: String): AdRewardedDelegate {
      return object : AdRewardedDelegate() {
        override fun onRewardedAdLoaded() {
          sendEvent(reactContext, listenerName, setMess("loaded", ""))
        }

        override fun onRewardedAdOpened() {
          sendEvent(reactContext, listenerName, setMess("opened", ""))
        }

        override fun onRewardedAdClosed() {
          sendEvent(reactContext, listenerName, setMess("closed", ""))
        }

        override fun onUserEarnedReward(rewardedItem: ADRewardItem) {
          val mess: WritableMap = Arguments.createMap()
          mess.putString("type", "rewarded")
          mess.putString("typeRewarded", rewardedItem.getType())
          mess.putDouble("amountRewarded", rewardedItem.getAmount())
          sendEvent(reactContext, listenerName, mess)
        }

        override fun onRewardedAdFailedToLoad(errorMess: String) {
          sendEvent(reactContext, listenerName, setMess("failedToLoad", errorMess))
        }

        override fun onRewardedAdFailedToShow(errorMess: String) {
          sendEvent(reactContext, listenerName, setMess("failedToShow", errorMess))
        }
      }
    }

    fun setMess(type: String, message: String): WritableMap {
      val mess: WritableMap = Arguments.createMap()
      mess.putString("type", type)
      mess.putString("message", message)
      return mess
    }
  }
}
