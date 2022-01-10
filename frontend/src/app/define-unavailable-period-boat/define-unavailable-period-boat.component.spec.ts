import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefineUnavailablePeriodBoatComponent } from './define-unavailable-period-boat.component';

describe('DefineUnavailablePeriodBoatComponent', () => {
  let component: DefineUnavailablePeriodBoatComponent;
  let fixture: ComponentFixture<DefineUnavailablePeriodBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DefineUnavailablePeriodBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DefineUnavailablePeriodBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
