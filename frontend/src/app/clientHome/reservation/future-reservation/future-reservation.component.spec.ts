import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FutureReservationComponent } from './future-reservation.component';

describe('FutureReservationComponent', () => {
  let component: FutureReservationComponent;
  let fixture: ComponentFixture<FutureReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FutureReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FutureReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
