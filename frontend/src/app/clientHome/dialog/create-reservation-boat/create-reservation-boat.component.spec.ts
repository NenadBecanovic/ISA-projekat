import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateReservationBoatComponent } from './create-reservation-boat.component';

describe('CreateReservationBoatComponent', () => {
  let component: CreateReservationBoatComponent;
  let fixture: ComponentFixture<CreateReservationBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateReservationBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateReservationBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
