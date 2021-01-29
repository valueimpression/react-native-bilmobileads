import React from 'react';
import {
    findNodeHandle,
    requireNativeComponent,
    UIManager,
    ViewStyle
} from 'react-native';

interface IAdBannerProps {
    adUnitId: string,
    style: ViewStyle,
    onAdLoaded?: (w: number, h: number) => void,
    onAdOpened?: () => void,
    onAdClosed?: () => void,
    onAdClicked?: () => void,
    onAdLeftApplication?: () => void,
    onAdFailedToLoad?: (error: string) => void,
}

let BANNER = "RNBilAdBanner"

class AdBanner extends React.Component<IAdBannerProps> {

    _bannerView: any;

    constructor(props: IAdBannerProps) {
        super(props)
    }

    componentDidMount() {
    }

    componentWillUnmount() {
        this.destroy();
    }

    load() {
        UIManager.dispatchViewManagerCommand(
            findNodeHandle(this._bannerView),
            UIManager.getViewManagerConfig(BANNER).Commands.load,
            [],
        );
    }

    show() {
        UIManager.dispatchViewManagerCommand(
            findNodeHandle(this._bannerView),
            UIManager.getViewManagerConfig(BANNER).Commands.show,
            [],
        );
    }

    hide() {
        UIManager.dispatchViewManagerCommand(
            findNodeHandle(this._bannerView),
            UIManager.getViewManagerConfig(BANNER).Commands.hide,
            [],
        );
    }

    destroy() {
        UIManager.dispatchViewManagerCommand(
            findNodeHandle(this._bannerView),
            UIManager.getViewManagerConfig(BANNER).Commands.destroy,
            [],
        );
    }

    onNativeEvent(data: any, props: any) {
        const { type, message, width, height } = data.nativeEvent;

        switch (type) {
            case "loaded":
                if (props.onAdLoaded) props.onAdLoaded(width, height);
                break;
            case "opened":
                if (props.onAdOpened) props.onAdOpened();
                break;
            case "closed":
                if (props.onAdClosed) props.onAdClosed();
                break;
            case "clicked":
                if (props.onAdClicked) props.onAdClicked();
                break;
            case "leftApplication":
                if (props.onAdLeftApplication) props.onAdLeftApplication();
                break;
            case "failedToLoad":
                if (props.onAdFailedToLoad) props.onAdFailedToLoad(message);
                break;
            default:
                break;
        }
    }

    render() {
        return (
            <RNBilAdBanner
                style={this.props.style}
                ref={el => (this._bannerView = el)}
                adUnitId={this.props.adUnitId}
                onNativeEvent={(nativeEvent) => {
                    this.onNativeEvent(nativeEvent, this.props)
                }}
            />
        );
    }
}

type RNBilAdBannerViewProps = {
    adUnitId: string,
    style?: ViewStyle,
    onNativeEvent?: (nativeEvent: any) => void,
};
const RNBilAdBanner = requireNativeComponent<RNBilAdBannerViewProps>(BANNER);

export default AdBanner;