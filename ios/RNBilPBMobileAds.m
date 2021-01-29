//
//  RNBilPBMobileAds.m
//  react-native-bilmobileads
//
//  Created by HNL_MAC on 26/01/2021.
//

#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(RNBilPBMobileAds, NSObject)

RCT_EXTERN_METHOD(initialize:(BOOL)testMode)
RCT_EXTERN_METHOD(enableCOPPA)
RCT_EXTERN_METHOD(disableCOPPA)
RCT_EXTERN_METHOD(setYearOfBirth:(double)yearOfBirth)
RCT_EXTERN_METHOD(setGender:(double)gender)

@end
