import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeZeroFormComponent } from './trade-zero-form.component';

describe('TradeZeroFormComponent', () => {
  let component: TradeZeroFormComponent;
  let fixture: ComponentFixture<TradeZeroFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TradeZeroFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TradeZeroFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
