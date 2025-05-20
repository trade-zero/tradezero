import { Injectable, Injector, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { BaseResourceService } from '@shared/services/base-resource.service';
import { BaseResourceModel } from '@shared/models/base-resource.model';

@Injectable()
export abstract class BaseResourceListComponent<T extends BaseResourceModel> implements OnInit, OnDestroy {

  abstract table: any;
  protected activatedRoute: ActivatedRoute;
  protected router: Router;
  protected subscription: Subscription;

  constructor(
    protected injector: Injector,
    protected service: BaseResourceService<T>,
  ) {
    this.activatedRoute = injector.get(ActivatedRoute);
    this.router = injector.get(Router);
    this.subscription = new Subscription();
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  protected abstract initTable(): void;

  add(): void {
  }

  edit(resource: T): void {
  }

}
