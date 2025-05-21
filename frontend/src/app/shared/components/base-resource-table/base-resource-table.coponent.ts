import { inject, Injectable, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { BaseResourceModel } from '@shared/models/base-resource.model';
import { TableColumModel } from '@shared/models/table-colum.model';
import { ActivatedRoute } from '@angular/router';

@Injectable()
export abstract class BaseResourceTable<T extends BaseResourceModel> implements OnInit, OnDestroy {

    readonly activatedRoute = inject(ActivatedRoute);
    abstract table: TableColumModel[];
    dataRows: T[] = [];

    protected destroy$ = new Subject<void>();

    ngOnInit() {
        this.loadData();
    }

    ngOnDestroy() {
        this.destroy$.next();
        this.destroy$.complete();
    }

    abstract loadData(): void;
}
