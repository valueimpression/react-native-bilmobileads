import { NativeModules, NativeEventEmitter, EmitterSubscription } from 'react-native';
import type { AdEventListener } from 'react-native-bilmobileads';
import { BilAdEvents } from 'react-native-bilmobileads';
import RewardedItem from './RewardedItem';

const { RNBilAdRewarded } = NativeModules;

const REWARDED = "RNBilAdRewarded";

export default class AdRewarded {

    eventListener!: EmitterSubscription;

    adUnitID: String = "";

    constructor(adUnitID: String) {
        this.adUnitID = adUnitID;
    }

    static create(adUnitID: String): AdRewarded {
        if (!this.isString(adUnitID)) {
            throw new Error(
                "AdRewarded.create() 'adUnitId' expected an string value and not empty.",
            );
        }

        RNBilAdRewarded.create(adUnitID);
        return new AdRewarded(adUnitID);
    }

    preLoad() {
        RNBilAdRewarded.preLoad();
    }

    show() {
        RNBilAdRewarded.show();
    }

    setListener(handler: AdEventListener) {
        const eventEmitter = new NativeEventEmitter(RNBilAdRewarded);
        this.eventListener = eventEmitter.addListener(REWARDED, (event) => {
            switch (event.type) {
                case "loaded":
                    handler(BilAdEvents.loaded, event.message)
                    break;
                case "opened":
                    handler(BilAdEvents.opened, event.message)
                    break;
                case "closed":
                    handler(BilAdEvents.closed, event.message)
                    break;
                case "clicked":
                    handler(BilAdEvents.clicked, event.message)
                    break;
                case "leftApplication":
                    handler(BilAdEvents.leftApplication, event.message)
                    break;
                case "rewarded":
                    let rewardedItem = new RewardedItem(event.typeRewarded, event.amountRewarded)
                    handler(BilAdEvents.rewarded, rewardedItem)
                    break;
                case "failedToLoad":
                    handler(BilAdEvents.failedToLoad, event.message)
                    break;
                case "failedToShow":
                    handler(BilAdEvents.failedToShow, event.message)
                    break;
                default:
            }
        });
    }

    async isReady() {
        return await RNBilAdRewarded.isReady();
    }

    destroy() {
        if (this.eventListener != null) this.eventListener.remove();
        RNBilAdRewarded.destroy();
    }

    getAdUnitID() {
        return this.adUnitID;
    }

    static isString(value: String) {
        return typeof value === 'string';
    }
}