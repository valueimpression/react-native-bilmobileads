import { NativeModules } from 'react-native';
const { RNBilPBMobileAds } = NativeModules;

import type { BilGender } from 'react-native-bilmobileads';

export default class PBMobileAds {
    static initialize(testMode: boolean) {
        RNBilPBMobileAds.initialize(testMode);
    }

    static enableCOPPA() {
        RNBilPBMobileAds.enableCOPPA();
    }

    static disableCOPPA() {
        RNBilPBMobileAds.disableCOPPA();
    }

    static setYearOfBirth(yearOfBirth: number) {
        RNBilPBMobileAds.setYearOfBirth(yearOfBirth);
    }

    static setGender(gender: BilGender) {
        RNBilPBMobileAds.setGender(gender);
    }
}