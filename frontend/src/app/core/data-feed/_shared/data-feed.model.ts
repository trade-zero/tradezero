import { BaseResourceModel } from "@shared/models/base-resource.model";

export class DataFeedModel extends BaseResourceModel{

    name?: string;
    trade_asset?: string;
    trade_time_frame?: string;
    datetime?: string;
    year?: string;
    month_?: string;
    day_of_month?: string;
    day_of_week?: string;
    hour?: string;
    week_of_year?: string;
    quarter_?: string;
    start_of_week?: string;
    start_of_month?: string;
    is_weekend?: string;

    constructor(props: Partial<DataFeedModel> = {}) {
        super();
        Object.assign(this, props);
    }

}
