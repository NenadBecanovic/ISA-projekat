import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstructorReservationHistoryComponent } from './instructor-reservation-history.component';

describe('InstructorReservationHistoryComponent', () => {
  let component: InstructorReservationHistoryComponent;
  let fixture: ComponentFixture<InstructorReservationHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InstructorReservationHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InstructorReservationHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
