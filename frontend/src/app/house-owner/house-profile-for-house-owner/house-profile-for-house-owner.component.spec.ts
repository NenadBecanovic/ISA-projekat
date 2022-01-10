import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HouseProfileForHouseOwnerComponent } from './house-profile-for-house-owner.component';

describe('HouseProfileForHouseOwnerComponent', () => {
  let component: HouseProfileForHouseOwnerComponent;
  let fixture: ComponentFixture<HouseProfileForHouseOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HouseProfileForHouseOwnerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HouseProfileForHouseOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
