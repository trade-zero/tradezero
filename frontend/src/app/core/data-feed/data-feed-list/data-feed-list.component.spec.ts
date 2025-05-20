import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataFeedListComponent } from './data-feed-list.component';

describe('DataFeedListComponent', () => {
  let component: DataFeedListComponent;
  let fixture: ComponentFixture<DataFeedListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataFeedListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DataFeedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
