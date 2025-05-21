import { Injectable } from '@angular/core';
import { DataFeedModel } from "@core/data-feed/_shared/data-feed.model";
import { FormItemBase } from '@shared/components/dynamic-form-generator/form-factory/form-item-base';
import { FormItemInputText } from '@shared/components/dynamic-form-generator/form-factory/form-item-input-text';

@Injectable({
    providedIn: 'root'
})
export class DataFeedItensService {

    getItems(value: DataFeedModel = new DataFeedModel()) {

        const items: FormItemBase<string>[] = [

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

