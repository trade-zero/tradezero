import { ConfigService } from './config.service';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn : 'root'
})
export class HelpConfig {

    constructor(private configService: ConfigService) {}

    public get TRADE_ZERO(): string { return this.configService.getEndPoint('TRADE_ZERO', 'TRADE_ZERO'); }

}
