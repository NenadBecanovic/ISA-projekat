import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestProfileBoatComponent } from './guest-profile-boat.component';

describe('GuestProfileBoatComponent', () => {
  let component: GuestProfileBoatComponent;
  let fixture: ComponentFixture<GuestProfileBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GuestProfileBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestProfileBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
