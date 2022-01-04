import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddActionBoatProfileComponent } from './add-action-boat-profile.component';

describe('AddActionBoatProfileComponent', () => {
  let component: AddActionBoatProfileComponent;
  let fixture: ComponentFixture<AddActionBoatProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddActionBoatProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddActionBoatProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
