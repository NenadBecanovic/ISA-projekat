import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateReservationForClientBoatComponent } from './create-reservation-for-client-boat.component';

describe('CreateReservationForClientBoatComponent', () => {
  let component: CreateReservationForClientBoatComponent;
  let fixture: ComponentFixture<CreateReservationForClientBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateReservationForClientBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateReservationForClientBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
