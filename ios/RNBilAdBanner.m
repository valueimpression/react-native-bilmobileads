//
//  RNBilAdBanner.m
//  react-native-bilmobileads
//
//  Created by HNL_MAC on 27/01/2021.
//

#import "React/RCTBridgeModule.h"
#import "React/RCTViewManager.h"

@interface RCT_EXTERN_MODULE(RNBilAdBanner, RCTViewManager)

RCT_EXPORT_VIEW_PROPERTY(adUnitId, NSString)
RCT_EXPORT_VIEW_PROPERTY(onNativeEvent, RCTBubblingEventBlock);

RCT_EXTERN_METHOD(load:(nonnull NSNumber *)node)
RCT_EXTERN_METHOD(show:(nonnull NSNumber *)node)
RCT_EXTERN_METHOD(hide:(nonnull NSNumber *)node)
RCT_EXTERN_METHOD(destroy:(nonnull NSNumber *)node)

@end

