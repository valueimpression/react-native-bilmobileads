import { NativeModules } from 'react-native';

type BilmobileadsType = {
  multiply(a: number, b: number): Promise<number>;
};

const { Bilmobileads } = NativeModules;

export default Bilmobileads as BilmobileadsType;
