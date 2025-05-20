import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicTableExpansiveComponent } from './dynamic-table-expansive.component';

describe('DynamicTableExpansiveComponent', () => {
  let component: DynamicTableExpansiveComponent;
  let fixture: ComponentFixture<DynamicTableExpansiveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DynamicTableExpansiveComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DynamicTableExpansiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
