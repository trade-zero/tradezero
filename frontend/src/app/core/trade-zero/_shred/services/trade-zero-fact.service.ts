import { Injectable , Injector } from '@angular/core';
import { BaseResourceService } from '@shared/services/base-resource.service';
import { TradeZeroFactModel } from '@core/trade-zero/_shred/models/trade-zero-fact.model';
import { HelpConfig } from '@shared/config/help-config';

@Injectable({
  providedIn: 'root'
})
export class TradeZeroFactService extends BaseResourceService<TradeZeroFactModel>{

    constructor(
        protected override injector: Injector,
        private helpConfig: HelpConfig,
    ) {
        super(`${helpConfig.TRADE_ZERO}tradezero-facts`, injector);
    }
}
