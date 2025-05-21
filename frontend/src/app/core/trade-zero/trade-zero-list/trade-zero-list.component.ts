import { Component , OnInit } from '@angular/core';
import { TableColumModel } from '@shared/models/table-colum.model';
import { TradeZeroFactService } from '@core/trade-zero/_shred/services/trade-zero-fact.service';
import { takeUntil } from 'rxjs';
import { BaseResourceTable } from '@shared/components/base-resource-table/base-resource-table.coponent';
import { TradeZeroFactModel } from '@core/trade-zero/_shred/models/trade-zero-fact.model';

@Component({
  selector: 'app-trade-zero-list',
  imports: [],
  templateUrl: './trade-zero-list.component.html',
  styleUrl: './trade-zero-list.component.scss'
})
export class TradeZeroListComponent extends BaseResourceTable<TradeZeroFactModel> {

    table: TableColumModel[] = [
        { field: 'tradeZeroFactUuid', header: 'tradeZeroFactUuid' },
        { field: 'tradeZeroDimUuid', header: 'tradeZeroDimUuid' },
        { field: 'agentDimUuid', header: 'agentDimUuid' },
        { field: 'epoch', header: 'epoch' },
        { field: 'trained', header: 'trained' },
    ];

    constructor(
        private service: TradeZeroFactService
    ) {
        super();
    }


    loadData() {
        this.service.getAll().pipe(takeUntil(this.destroy$)).subscribe(
            {
                next: (result) => this.dataRows = result,
                error: (error) => console.error('Erro ao carregar candidatos:', error)
            }
        );
    }
}
