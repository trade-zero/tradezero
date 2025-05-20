import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskManagementListComponent } from './risk-management-list.component';

describe('RiskManagementListComponent', () => {
  let component: RiskManagementListComponent;
  let fixture: ComponentFixture<RiskManagementListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RiskManagementListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RiskManagementListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
