import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataFeedFormComponent } from './data-feed-form.component';

describe('DataFeedFormComponent', () => {
  let component: DataFeedFormComponent;
  let fixture: ComponentFixture<DataFeedFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataFeedFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DataFeedFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
