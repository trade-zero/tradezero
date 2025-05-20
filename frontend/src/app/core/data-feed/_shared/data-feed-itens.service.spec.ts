import { TestBed } from '@angular/core/testing';

import { DataFeedItensService } from './data-feed-itens.service';

describe('DataFeedItensService', () => {
  let service: DataFeedItensService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataFeedItensService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
