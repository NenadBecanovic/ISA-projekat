import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefineUnavailablePeriodHouseComponent } from './define-unavailable-period-house.component';

describe('DefineUnavailablePeriodHouseComponent', () => {
  let component: DefineUnavailablePeriodHouseComponent;
  let fixture: ComponentFixture<DefineUnavailablePeriodHouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DefineUnavailablePeriodHouseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DefineUnavailablePeriodHouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
