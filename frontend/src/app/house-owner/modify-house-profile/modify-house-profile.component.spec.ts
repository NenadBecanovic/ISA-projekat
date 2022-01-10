import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyHouseProfileComponent } from './modify-house-profile.component';

describe('ModifyHouseProfileComponent', () => {
  let component: ModifyHouseProfileComponent;
  let fixture: ComponentFixture<ModifyHouseProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifyHouseProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifyHouseProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
