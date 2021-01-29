//
//  RNBilAdRewarded.m
//  react-native-bilmobileads
//
//  Created by HNL_MAC on 27/01/2021.
//

#import "React/RCTBridgeModule.h"
#import "React/RCTEventEmitter.h"

@interface RCT_EXTERN_MODULE(RNBilAdRewarded, RCTEventEmitter)

RCT_EXTERN_METHOD(create:(NSString)adUnitID)
RCT_EXTERN_METHOD(preLoad)
RCT_EXTERN_METHOD(show)
RCT_EXTERN_METHOD(destroy)
RCT_EXTERN_METHOD(isReady:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)

@end
