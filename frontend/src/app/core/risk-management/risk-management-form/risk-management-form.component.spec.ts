import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskManagementFormComponent } from './risk-management-form.component';

describe('RiskManagementFormComponent', () => {
  let component: RiskManagementFormComponent;
  let fixture: ComponentFixture<RiskManagementFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RiskManagementFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RiskManagementFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
