import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddActionHouseProfileComponent } from './add-action-house-profile.component';

describe('AddActionHouseProfileComponent', () => {
  let component: AddActionHouseProfileComponent;
  let fixture: ComponentFixture<AddActionHouseProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddActionHouseProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddActionHouseProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
