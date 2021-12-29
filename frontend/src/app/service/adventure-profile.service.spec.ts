/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { AdventureProfileService } from './adventure-profile.service';

describe('Service: AdventureProfile', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AdventureProfileService]
    });
  });

  it('should ...', inject([AdventureProfileService], (service: AdventureProfileService) => {
    expect(service).toBeTruthy();
  }));
});
