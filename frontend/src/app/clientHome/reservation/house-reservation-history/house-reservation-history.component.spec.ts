import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HouseReservationHistoryComponent } from './house-reservation-history.component';

describe('HouseReservationHistoryComponent', () => {
  let component: HouseReservationHistoryComponent;
  let fixture: ComponentFixture<HouseReservationHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HouseReservationHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HouseReservationHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
