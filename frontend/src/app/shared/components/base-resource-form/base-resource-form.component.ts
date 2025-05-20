import { ActivatedRoute, Router } from '@angular/router';
import { AfterViewInit, ChangeDetectorRef, Injectable, Injector, OnDestroy, OnInit } from '@angular/core';
import { DialogService } from 'primeng/dynamicdialog';
import { FormGroup } from '@angular/forms';
import { Location } from '@angular/common';
import { Subscription } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';

import { BaseResourceService } from '@shared/services/base-resource.service';
import { BaseResourceModel } from '@shared/models/base-resource.model';

@Injectable()
export abstract class BaseResourceFormComponent<T extends BaseResourceModel> implements OnInit, AfterViewInit, OnDestroy {

  form: FormGroup = new FormGroup({});

  protected activatedRoute: ActivatedRoute;
  protected router: Router;
  protected cdr: ChangeDetectorRef;
  protected location: Location;
  protected subscription: Subscription;
  protected translateService: TranslateService;
  protected dialogService: DialogService;

  constructor(
    protected injector: Injector,
    public resource: T,
    protected service: BaseResourceService<T>,
  ) {

    this.activatedRoute = injector.get(ActivatedRoute);
    this.router = injector.get(Router);
    this.cdr = injector.get(ChangeDetectorRef);
    this.location = injector.get(Location);
    this.translateService = injector.get(TranslateService);
    this.dialogService = injector.get(DialogService);
    this.subscription = new Subscription();
  }

  ngOnInit(): void {
    if (this.activatedRoute.snapshot.params['id']) {
      this.resource = this.activatedRoute.snapshot.data['resource'];
    }
  }

  ngAfterViewInit(): void {
    this.form.updateValueAndValidity();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
