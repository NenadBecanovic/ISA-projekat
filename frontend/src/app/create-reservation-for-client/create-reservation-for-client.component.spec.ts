import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateReservationForClientComponent } from './create-reservation-for-client.component';

describe('CreateReservationForClientComponent', () => {
  let component: CreateReservationForClientComponent;
  let fixture: ComponentFixture<CreateReservationForClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateReservationForClientComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateReservationForClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
