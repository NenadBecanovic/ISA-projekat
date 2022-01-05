import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePageHouseOwnerComponent } from './home-page-house-owner.component';

describe('HomePageHouseOwnerComponent', () => {
  let component: HomePageHouseOwnerComponent;
  let fixture: ComponentFixture<HomePageHouseOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomePageHouseOwnerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomePageHouseOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
