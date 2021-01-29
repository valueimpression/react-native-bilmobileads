import { NativeModules, NativeEventEmitter, EmitterSubscription } from 'react-native';
import type { AdEventListener } from 'react-native-bilmobileads';
import { BilAdEvents } from 'react-native-bilmobileads';

const { RNBilAdInterstitial } = NativeModules;

const INTERSTITIAL = "RNBilAdInterstitial";

export default class AdInterstitial {

    eventListener!: EmitterSubscription;

    adUnitID: String = "";

    constructor(adUnitID: String) {
        this.adUnitID = adUnitID;
    }

    static create(adUnitID: String): AdInterstitial {
        if (!this.isString(adUnitID)) {
            throw new Error(
                "AdInterstitial.create() 'adUnitId' expected an string value and not empty.",
            );
        }

        RNBilAdInterstitial.create(adUnitID);
        return new AdInterstitial(adUnitID);
    }

    preLoad() {
        RNBilAdInterstitial.preLoad();
    }

    show() {
        RNBilAdInterstitial.show();
    }

    setListener(handler: AdEventListener) {
        const eventEmitter = new NativeEventEmitter(RNBilAdInterstitial);
        this.eventListener = eventEmitter.addListener(INTERSTITIAL, (event) => {
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
                case "failedToLoad":
                    handler(BilAdEvents.failedToLoad, event.message)
                    break;
                default:
            }
        });
    }

    async isReady() {
        return await RNBilAdInterstitial.isReady();
    }

    destroy() {
        if (this.eventListener != null) this.eventListener.remove();
        RNBilAdInterstitial.destroy();
    }

    getAdUnitID() {
        return this.adUnitID;
    }

    static isString(value: String) {
        return typeof value === 'string';
    }
}