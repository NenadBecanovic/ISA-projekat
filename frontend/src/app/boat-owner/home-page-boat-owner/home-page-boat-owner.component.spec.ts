import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePageBoatOwnerComponent } from './home-page-boat-owner.component';

describe('HomePageBoatOwnerComponent', () => {
  let component: HomePageBoatOwnerComponent;
  let fixture: ComponentFixture<HomePageBoatOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomePageBoatOwnerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomePageBoatOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
