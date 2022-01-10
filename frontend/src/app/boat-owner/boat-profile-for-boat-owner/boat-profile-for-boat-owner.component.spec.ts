import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatProfileForBoatOwnerComponent } from './boat-profile-for-boat-owner.component';

describe('BoatProfileForBoatOwnerComponent', () => {
  let component: BoatProfileForBoatOwnerComponent;
  let fixture: ComponentFixture<BoatProfileForBoatOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatProfileForBoatOwnerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatProfileForBoatOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
