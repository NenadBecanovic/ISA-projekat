/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

<<<<<<< HEAD:frontend/src/app/adventure-profile/add-image-dialog/add-image-dialog.component.spec.ts
import { AddImageDialogComponent } from './add-image-dialog.component';

describe('AddImageDialogComponent', () => {
  let component: AddImageDialogComponent;
  let fixture: ComponentFixture<AddImageDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddImageDialogComponent ]
=======
import { CalendarDialogComponent } from './calendar-dialog.component';

describe('CalendarDialogComponent', () => {
  let component: CalendarDialogComponent;
  let fixture: ComponentFixture<CalendarDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CalendarDialogComponent ]
>>>>>>> develop:frontend/src/app/fishing-instructor-profile/calendar-dialog/calendar-dialog.component.spec.ts
    })
    .compileComponents();
  }));

  beforeEach(() => {
<<<<<<< HEAD:frontend/src/app/adventure-profile/add-image-dialog/add-image-dialog.component.spec.ts
    fixture = TestBed.createComponent(AddImageDialogComponent);
=======
    fixture = TestBed.createComponent(CalendarDialogComponent);
>>>>>>> develop:frontend/src/app/fishing-instructor-profile/calendar-dialog/calendar-dialog.component.spec.ts
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
