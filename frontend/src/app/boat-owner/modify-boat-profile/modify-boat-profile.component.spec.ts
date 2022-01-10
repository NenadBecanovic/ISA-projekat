import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyBoatProfileComponent } from './modify-boat-profile.component';

describe('ModifyBoatProfileComponent', () => {
  let component: ModifyBoatProfileComponent;
  let fixture: ComponentFixture<ModifyBoatProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifyBoatProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModifyBoatProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
