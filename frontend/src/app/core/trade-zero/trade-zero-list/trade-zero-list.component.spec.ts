import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeZeroListComponent } from './trade-zero-list.component';

describe('TradeZeroListComponent', () => {
  let component: TradeZeroListComponent;
  let fixture: ComponentFixture<TradeZeroListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TradeZeroListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TradeZeroListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
