import { BaseResourceModel } from '@shared/models/base-resource.model';

export class TradeZeroFactModel extends BaseResourceModel{

    tradeZeroFactUuid?: string;
    tradeZeroDimUuid?: string;
    agentDimUuid?: string;
    epoch?: number;
    trained?: boolean

    constructor(props: Partial<TradeZeroFactModel> = {}) {
        super();
        Object.assign(this, props);
    }

}
