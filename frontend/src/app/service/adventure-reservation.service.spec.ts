/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { AdventureReservationService } from './adventure-reservation.service';

describe('Service: AdventureReservation', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AdventureReservationService]
    });
  });

  it('should ...', inject([AdventureReservationService], (service: AdventureReservationService) => {
    expect(service).toBeTruthy();
  }));
});
