import * as React from 'react';

import { StyleSheet, View, Text, Button, ScrollView, SafeAreaView, Dimensions } from 'react-native';
import { PBMobileAds, AdBanner, AdInterstitial, AdRewarded, RewardedItem, BilGender, BilAdEvents } from 'react-native-bilmobileads';


const adUnitIdSmartBanner = "replace_with_your_adunitid"
const adUnitIdBanner320x50 = "replace_with_your_adunitid"
const adUnitIdBanner300x250 = "replace_with_your_adunitid"
const adUnitIdFull = "replace_with_your_adunitid"
const adUnitIdRewarded = "replace_with_your_adunitid"

PBMobileAds.initialize(false);

let banner: AdBanner;
let interstitial: AdInterstitial;
let rewarded: AdRewarded;

function handleEvents(adType: String, type: BilAdEvents, data: any) {
  switch (type) {
    case BilAdEvents.loaded:
      console.log('*** ' + adType + ' Ad loaded!');
      break;
    case BilAdEvents.opened:
      console.log('*** ' + adType + ' Ad opened!');
      break;
    case BilAdEvents.closed:
      // adInterstitial.preLoad();
      console.log('*** ' + adType + ' Ad closed!');
      break;
    case BilAdEvents.clicked:
      console.log('*** ' + adType + ' Ad clicked!');
      break;
    case BilAdEvents.leftApplication:
      console.log('*** ' + adType + ' Ad left Application!');
      break;
    case BilAdEvents.failedToLoad:
      console.log('*** ' + adType + ' Ad failed to load. :(');
      break;
    case BilAdEvents.failedToShow:
      console.log('*** ' + adType + ' Ad failed to show. :(');
      break;
    case BilAdEvents.rewarded:
      let rewardedItem = data as RewardedItem
      console.log(
        '*** ' + adType + ' rewarded: type - ' + rewardedItem.type + '| amount - ' + rewardedItem.amount);
      break;
    default:
  }
}

export default function App() {
  const [hSmart, setSmartHeight] = React.useState<number | undefined>();
  const [h250, set250Height] = React.useState<number | undefined>();
  React.useEffect(() => {
    // Unsubscribe from events on unmount
    return () => {
      if (interstitial) interstitial.destroy()
      if (rewarded) rewarded.destroy()
    };
  }, []);

  return (
    <SafeAreaView>
      <ScrollView>
        <View style={styles.container}>

          {/* PBMobileAds */}
          <Text>PBMobileAds</Text>
          <View style={styles.item}>
            {/* PBMobileAds */}
            <Button title="init" onPress={() => {
              PBMobileAds.initialize(false);
            }} />
            <View style={styles.space}></View>
            <Button title="enableCOPPA" onPress={() => {
              PBMobileAds.enableCOPPA();
            }} />
            <View style={styles.space}></View>
            <Button title="disableCOPPA" onPress={() => {
              PBMobileAds.disableCOPPA();
            }} />
            <View style={styles.space}></View>
            <Button title="setYearOfBirth" onPress={() => {
              PBMobileAds.setYearOfBirth(1991);
            }} />
            <View style={styles.space}></View>
            <Button title="setGender" onPress={() => {
              PBMobileAds.setGender(BilGender.Male);
            }} />
          </View>

          <View style={styles.space}></View>

          {/* Banner */}
          <Text>Banner</Text>
          <View style={styles.item}>
            <Button title="Load" onPress={() => {
              banner.show()
            }} />
            <View style={styles.space}></View>
            <Button title="Show" onPress={() => {
              banner.show()
            }} />
            <View style={styles.space}></View>
            <Button title="Hide" onPress={() => {
              banner.hide()
            }} />
            <View style={styles.space}></View>
          </View>
          <View style={[styles.item]}>
            <AdBanner adUnitId={adUnitIdSmartBanner}
              ref={(el) => { if (el) { banner = el } }}
              style={{
                width: Dimensions.get('screen').width,
                height: hSmart,
                backgroundColor: 'rgba(0, 0, 0, 0.1)',
              }}
              onAdLoaded={(w, h) => {
                setSmartHeight(h)
                console.log("Smart Banner is loaded")
              }}
              onAdFailedToLoad={(error) => {
                console.log("Smart Banner is loaded fail: " + error)
              }}
            />
            <View style={styles.space}></View>
            <AdBanner adUnitId={adUnitIdBanner300x250}
              style={{
                width: Dimensions.get('screen').width,
                height: h250,
                backgroundColor: 'rgba(0, 0, 0, 0.1)'
              }}
              onAdLoaded={(w, h) => {
                set250Height(h)
                console.log("Banner300x250 is loaded")
              }}
              onAdClicked={()=>{
                console.log("Banner300x250 is clicked")
              }}
            />
          </View>

          <View style={styles.space}></View>

          {/* Interstitial */}
          <Text>Interstitial</Text>
          <View style={styles.item}>
            <Button title="Create" onPress={() => {
              interstitial = AdInterstitial.create(adUnitIdFull);
              interstitial.setListener((type, data) => {
                handleEvents("interstitial", type, data)
              });
            }} />
            <View style={styles.space}></View>
            <Button title="Preload" onPress={async () => {
              if (interstitial == null) return
              interstitial.preLoad();
            }} />
            <View style={styles.space}></View>
            <Button title="Show" onPress={() => {
              if (interstitial == null) return
              interstitial.show();
            }} />
            <View style={styles.space}></View>
            <Button title="Destroy" onPress={() => {
              if (interstitial == null) return
              interstitial.destroy();
            }} />
          </View>

          <View style={styles.space}></View>

          {/* Rewarded */}
          <Text>Rewarded</Text>
          <View style={styles.item}>
            <Button title="Create" onPress={() => {
              rewarded = AdRewarded.create(adUnitIdRewarded);
              rewarded.setListener((type, data) => {
                handleEvents("rewarded", type, data)
              });
            }} />
            <View style={styles.space}></View>
            <Button title="Preload" onPress={() => {
              if (rewarded == null) return
              rewarded.preLoad();
            }} />
            <View style={styles.space}></View>
            <Button title="Show" onPress={() => {
              if (rewarded == null) return
              rewarded.show();
            }} />
            <View style={styles.space}></View>
            <Button title="Destroy" onPress={() => {
              if (rewarded == null) return
              rewarded.destroy();
            }} />
          </View>

          <View style={styles.space}></View>

          {/* <Text>Result: {result}</Text> */}
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    flexDirection: 'column',
    flexWrap: 'wrap',
  },
  item: {
    width: '100%',
  },
  centerAd: {
    alignItems: "center",
    justifyContent: "center"
  },
  space: { height: 10 },
});
