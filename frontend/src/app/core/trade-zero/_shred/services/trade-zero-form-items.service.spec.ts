import { TestBed } from '@angular/core/testing';

import { TradeZeroFormItemsService } from './trade-zero-form-items.service';

describe('TradeZeroFormItemsService', () => {
  let service: TradeZeroFormItemsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TradeZeroFormItemsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
