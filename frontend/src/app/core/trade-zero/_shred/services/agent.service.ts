import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from '@shared/services/base-resource.service';
import { HelpConfig } from '@shared/config/help-config';
import { AgentModel } from '@core/trade-zero/_shred/models/agent.model';

@Injectable({
    providedIn: 'root'
})
export class AgentService extends BaseResourceService<AgentModel> {
    constructor(
        protected override injector: Injector,
        private helpConfig: HelpConfig
    ) {
        super(`${helpConfig.TRADE_ZERO}agents`, injector);
    }
}
