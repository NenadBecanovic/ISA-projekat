import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarDialogHouseComponent } from './calendar-dialog-house.component';

describe('CalendarDialogComponent', () => {
  let component: CalendarDialogHouseComponent;
  let fixture: ComponentFixture<CalendarDialogHouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalendarDialogHouseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalendarDialogHouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
