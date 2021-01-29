
export default class RewardedItem {
    type: String = "";
    amount: number = 0;

    constructor(type: String, amount: number) {
        this.type = type;
        this.amount = amount;
    }
}