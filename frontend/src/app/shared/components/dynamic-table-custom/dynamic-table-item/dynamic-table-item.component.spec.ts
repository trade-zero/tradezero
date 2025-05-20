import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicTableItemComponent } from './dynamic-table-item.component';

describe('DynamicTableItemComponent', () => {
  let component: DynamicTableItemComponent;
  let fixture: ComponentFixture<DynamicTableItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DynamicTableItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DynamicTableItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
