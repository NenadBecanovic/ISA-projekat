import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarDialogBoatComponent } from './calendar-dialog-boat.component';

describe('CalendarDialogBoatComponent', () => {
  let component: CalendarDialogBoatComponent;
  let fixture: ComponentFixture<CalendarDialogBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalendarDialogBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalendarDialogBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
