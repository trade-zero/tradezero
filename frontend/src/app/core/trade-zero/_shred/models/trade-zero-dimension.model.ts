import { BaseResourceModel } from "@shared/models/base-resource.model";

export class TradeZeroDimensionModel extends BaseResourceModel{

    tradeZeroDimUuid?: string;
    tradeAsset?: any[];
    tradeTimeFrame?: string;
    balanceInitial?: number;
    drawdown?: number;
    maxVolume?: number;
    maxHold?: number;
    lookBack?: number;
    lookForward?: number;
    backPropagateSize?: number;
    maxEpisode?: number;

    constructor(props: Partial<TradeZeroDimensionModel> = {}) {
        super();
        Object.assign(this, props);
    }

}
