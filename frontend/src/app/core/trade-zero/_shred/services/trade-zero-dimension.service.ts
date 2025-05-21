import { Injectable , Injector } from '@angular/core';
import { BaseResourceService } from '@shared/services/base-resource.service';
import { HelpConfig } from '@shared/config/help-config';
import { TradeZeroDimensionModel } from '@core/trade-zero/_shred/models/trade-zero-dimension.model';

@Injectable({
  providedIn: 'root'
})
export class TradeZeroDimensionService extends BaseResourceService<TradeZeroDimensionModel> {

    constructor(
        protected override injector: Injector ,
        private helpConfig: HelpConfig ,
    ) {
        super(`${helpConfig.TRADE_ZERO}tradezero-dimensions` , injector);
    }
}
