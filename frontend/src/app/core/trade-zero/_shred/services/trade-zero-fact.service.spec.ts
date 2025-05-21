import { TestBed } from '@angular/core/testing';

import { TradeZeroFactService } from './trade-zero-fact.service';

describe('TradeZeroFactService', () => {
  let service: TradeZeroFactService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TradeZeroFactService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
