import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateReservationHouseComponent } from './create-reservation-house.component';

describe('CreateReservationHouseComponent', () => {
  let component: CreateReservationHouseComponent;
  let fixture: ComponentFixture<CreateReservationHouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateReservationHouseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateReservationHouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
