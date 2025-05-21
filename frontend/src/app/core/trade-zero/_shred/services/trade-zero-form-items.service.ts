import { Injectable } from '@angular/core';
import { DataFeedModel } from '@core/data-feed/_shared/data-feed.model';
import { FormItemBase } from '@shared/components/dynamic-form-generator/form-factory/form-item-base';
import { FormItemInputText } from '@shared/components/dynamic-form-generator/form-factory/form-item-input-text';
import { TradeZeroDimensionModel } from '@core/trade-zero/_shred/models/trade-zero-dimension.model';
import { TradeZeroFactModel } from '@core/trade-zero/_shred/models/trade-zero-fact.model';
import { FormItemCheckbox } from '@shared/components/dynamic-form-generator/form-factory/form-item-checkbox';

@Injectable({
    providedIn: 'root'
})
export class TradeZeroFormItemsService {

    getAgent(value: DataFeedModel = new DataFeedModel()) {

        const items: FormItemBase<string>[] = [
            new FormItemInputText({
                key: 'name',
                label: 'NOME',
                value: value.name,
                required: true,
                order: 1
            })
        ];

        return items;
    }

    getTradeZeroDimension(value: TradeZeroDimensionModel = new TradeZeroDimensionModel()) {
        const items: FormItemBase<string>[] = [
            new FormItemInputText({
                key: 'tradeZeroDimUuid',
                label: 'UUID',
                value: value.tradeZeroDimUuid,
                required: true,
                order: 1
            }),

            new FormItemInputText({
                key: 'tradeAsset',
                label: 'ASSET',
                value: value.tradeAsset,
                required: true,
                order: 2
            }),

            new FormItemInputText({
                key: 'tradeTimeFrame',
                label: 'TRADE TIME FRAME',
                value: value.tradeTimeFrame,
                required: true,
                order: 3
            }),

            new FormItemInputText({
                key: 'balanceInitial',
                label: 'BALANÇO INICIAL',
                value: value.balanceInitial,
                required: true,
                order: 4
            }),

            new FormItemInputText({
                key: 'drawdown',
                label: 'DRAW DOWN',
                value: value.drawdown,
                required: true,
                order: 5
            }),

            new FormItemInputText({
                key: 'maxVolume',
                label: 'VOLUME MAXIMO',
                value: value.maxVolume,
                required: true,
                order: 6
            }),

            new FormItemInputText({
                key: 'maxHold',
                label: 'MAX HOLD',
                value: value.maxVolume,
                required: true,
                order: 7
            }),

            new FormItemInputText({
                key: 'lookBack',
                label: 'LOOK BACK',
                value: value.lookBack,
                required: true,
                order: 8
            }),

            new FormItemInputText({
                key: 'lookForward',
                label: 'LOOK FORWARD',
                value: value.lookForward,
                required: true,
                order: 9
            }),

            new FormItemInputText({
                key: 'backPropagateSize',
                label: 'BACK PROPAGATE SIZE',
                value: value.backPropagateSize,
                required: true,
                order: 10
            }),

            new FormItemInputText({
                key: 'maxEpisode',
                label: 'MAX EPISODE',
                value: value.maxEpisode,
                required: true,
                order: 11
            })
        ];

        return items;
    }

    getTradeZeroFact(value: TradeZeroFactModel = new TradeZeroFactModel()) {

        const items: FormItemBase<string>[] = [

            new FormItemInputText({
                key: 'tradeZeroFactUuid',
                label: 'Trade Zero Fact UUID',
                value: value.tradeZeroFactUuid,
                required: true,
                order: 1
            }),

            new FormItemInputText({
                key: 'tradeZeroDimUuid',
                label: 'Trade Zero Dim UUID',
                value: value.tradeZeroDimUuid,
                required: true,
                order: 2
            }),

            new FormItemInputText({
                key: 'agentDimUuid',
                label: 'Agent Dim UUID',
                value: value.agentDimUuid,
                required: true,
                order: 3
            }),

            new FormItemInputText({
                key: 'epoch',
                label: 'Epch',
                value: value.epoch,
                required: true,
                order: 4
            }),

            new FormItemCheckbox({
                key: 'trained',
                label: 'Trained',
                value: value.trained,
                required: true,
                order: 5
            }),
        ];
    }
}
