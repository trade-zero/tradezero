import { TestBed } from '@angular/core/testing';

import { TradeZeroDimensionService } from './trade-zero-dimension.service';

describe('TradeZeroDimensionService', () => {
  let service: TradeZeroDimensionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TradeZeroDimensionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
