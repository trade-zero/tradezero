import { Injectable } from '@angular/core';
import { DataFeedModel } from "@core/data-feed/_shared/data-feed.model";

@Injectable({
    providedIn: 'root'
})
export class DataFeedItensService {

    constructor() {
    }

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

    getItems(value: DataFeedModel = new DataFeedModel()) {

        const items: Form<string>[] = [

            new FormItemInputText(
                {
                    key: 'name' ,
                    label: 'NOME' ,
                    value: value.name ,
                    required: true ,
                    order: 1 ,
                }
            ) ,

        ]
    }
}

