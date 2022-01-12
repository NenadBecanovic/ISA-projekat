import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddImageHouseComponent } from './add-image-house.component';

describe('AddImageHouseComponent', () => {
  let component: AddImageHouseComponent;
  let fixture: ComponentFixture<AddImageHouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddImageHouseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddImageHouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
