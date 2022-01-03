import { TestBed } from '@angular/core/testing';

import { HouseReservationService } from './house-reservation.service';

describe('HouseReservationService', () => {
  let service: HouseReservationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HouseReservationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
