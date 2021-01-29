package com.reactnativebilmobileads

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RelativeLayout
import com.bil.bilmobileads.ADBanner
import com.bil.bilmobileads.interfaces.AdDelegate
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.PixelUtil
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.google.android.gms.ads.AdSize


class RNBilAdBanner(private val reactApplicationContext: ReactApplicationContext) : SimpleViewManager<RelativeLayout>() {

  private val allAds: MutableMap<Int, ADBanner> = HashMap()
  private val allView: MutableMap<Int, RelativeLayout> = HashMap()

  private var eventName: String = "onNativeEvent"

  private val loadFUNC = 1
  private val showFUNC = 2
  private val hideFUNC = 3
  private val destroyFUNC = 4

  override fun getName(): String {
    return Utils.BANNER
  }

  override fun createViewInstance(reactContext: ThemedReactContext): RelativeLayout {
    val reactViewGroup = RelativeLayout(reactContext)
    reactViewGroup.setBackgroundColor(Color.TRANSPARENT)
    reactViewGroup.visibility = View.VISIBLE
    reactViewGroup.gravity = Utils.getGravityForPositionCode(Utils.CENTER)
    return reactViewGroup
  }

  @ReactProp(name = "adUnitId")
  fun setAdUnitId(reactViewGroup: RelativeLayout?, adUnitId: String) {
    Handler(Looper.getMainLooper()).post {
      run {
        if (reactViewGroup != null) {
          val banner = ADBanner(reactViewGroup, adUnitId)
          banner.setListener(object : AdDelegate() {
            override fun onAdLoaded() {
              var top: Int
              var left: Int
              var width: Int
              var height: Int
              if (banner.amBanner.adSize == AdSize.SMART_BANNER || banner.amBanner.adSize == AdSize.FLUID) {
                top = 0
                left = 0
                width = banner.width
                height = banner.height
              } else {
                top = banner.amBanner.top
                left = banner.amBanner.left
                width = banner.widthInPixels
                height = banner.heightInPixels
              }
              banner.amBanner.measure(width, height)
              banner.amBanner.layout(left, top, left + width, top + height)

              val payload = Arguments.createMap()
              if (banner.amBanner.adSize != AdSize.SMART_BANNER || banner.amBanner.adSize != AdSize.FLUID) {
                payload.putInt("width", PixelUtil.toDIPFromPixel(width.toFloat()).toInt() + 1)
                payload.putInt("height", PixelUtil.toDIPFromPixel(height.toFloat()).toInt() + 1)
              } else {
                payload.putInt("width", PixelUtil.toDIPFromPixel(width.toFloat()).toInt())
                payload.putInt("height", PixelUtil.toDIPFromPixel(height.toFloat()).toInt())
              }


              payload.putInt("viewID", reactViewGroup.id)
              val event: WritableMap = Utils.setMess("loaded", "")
              if (payload != null) {
                event.merge(payload)
              }

              sendEvent(reactViewGroup.id, event)
            }

            override fun onAdOpened() {
              if (reactViewGroup != null) {
                sendEvent(reactViewGroup.id, Utils.setMess("opened", ""))
              }
            }

            override fun onAdClosed() {
              if (reactViewGroup != null) {
                sendEvent(reactViewGroup.id, Utils.setMess("closed", ""))
              }
            }

            override fun onAdClicked() {
              if (reactViewGroup != null) {
                sendEvent(reactViewGroup.id, Utils.setMess("clicked", ""))
              }
            }

            override fun onAdLeftApplication() {
              if (reactViewGroup != null) {
                sendEvent(reactViewGroup.id, Utils.setMess("leftApplication", ""))
              }
            }

            override fun onAdFailedToLoad(errorCode: String) {
              if (reactViewGroup != null) {
                sendEvent(reactViewGroup.id, Utils.setMess("failedToLoad", ""))
              }
            }
          })
          allAds[reactViewGroup.id] = banner
          allView[reactViewGroup.id] = reactViewGroup
        }
      }
    }
  }

  private fun load(viewID: Int) {
    val banner: ADBanner = allAds[viewID] ?: return

    Handler(Looper.getMainLooper()).post {
      run {
        banner.load()
      }
    }
  }

  private fun show(viewID: Int) {
    val adPlaceHolder = allView[viewID] ?: return
    val banner = allAds[viewID] ?: return

    Handler(Looper.getMainLooper()).post {
      run {
        adPlaceHolder.visibility = View.VISIBLE
        banner.startFetchData()
      }
    }
  }

  private fun hide(viewID: Int) {
    val adPlaceHolder = allView[viewID] ?: return
    val banner = allAds[viewID] ?: return

    Handler(Looper.getMainLooper()).post {
      run {
        adPlaceHolder.visibility = View.GONE
        banner.stopFetchData()
      }
    }
  }

  private fun destroy(viewID: Int) {
    val adPlaceHolder = allView[viewID] ?: return
    val banner = allAds[viewID] ?: return

    Handler(Looper.getMainLooper()).post {
      run {
        adPlaceHolder.removeAllViews();
        adPlaceHolder.visibility = View.GONE
        banner.destroy()
      }
    }
  }

  private fun sendEvent(id: Int, event: WritableMap?) {
    reactApplicationContext.getJSModule(RCTEventEmitter::class.java)
      .receiveEvent(id, eventName, event)
  }

  override fun getExportedCustomDirectEventTypeConstants(): MutableMap<String, Any> {
    val builder = MapBuilder.builder<String, Any>()
    builder.put(eventName, MapBuilder.of("registrationName", eventName))
    return builder.build()
  }

  override fun getCommandsMap(): MutableMap<String, Int> {
    val builder = MapBuilder.builder<String, Int>()
    builder.put("load", 1)
    builder.put("show", 2)
    builder.put("hide", 3)
    builder.put("destroy", 4)
    return builder.build()
  }

  override fun receiveCommand(root: RelativeLayout, commandId: Int, args: ReadableArray?) {
    when (commandId) {
      loadFUNC -> load(root.id)
      showFUNC -> show(root.id)
      hideFUNC -> hide(root.id)
      destroyFUNC -> destroy(root.id)
    }
  }
}
