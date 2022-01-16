import { TestBed } from '@angular/core/testing';

import { FishingAdventureService } from './fishing-adventure.service';

describe('FishingAdventureService', () => {
  let service: FishingAdventureService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FishingAdventureService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
