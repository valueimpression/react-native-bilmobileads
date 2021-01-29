import { NativeModules } from 'react-native';

type BilmobileadsType = {
  multiply(a: number, b: number): Promise<number>;
};
const { Bilmobileads } = NativeModules;
export default Bilmobileads as BilmobileadsType;


export { default as PBMobileAds } from './PBMobileAds';
export { default as AdBanner } from './AdBanner';
export { default as AdInterstitial } from './AdInterstitial';
export { default as AdRewarded } from './AdRewarded';
export { default as RewardedItem } from './RewardedItem';

export type AdEventListener = (
  type:
    | BilAdEvents.loaded
    | BilAdEvents.opened
    | BilAdEvents.closed
    | BilAdEvents.clicked
    | BilAdEvents.leftApplication
    | BilAdEvents.rewarded
    | BilAdEvents.failedToLoad
    | BilAdEvents.failedToShow
  ,
  data?: any
) => void;
export enum BilGender { Unknown, Male, Female }
export enum BilAdEvents {
  loaded,
  opened,
  closed,
  clicked,
  leftApplication,
  rewarded,
  failedToLoad,
  failedToShow,
}
export enum BilAdBannerSize {
  Banner320x50,
  Banner320x100,
  Banner300x250,
  Banner468x60,
  Banner728x90,
  SmartBanner
}