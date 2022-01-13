import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddImageBoatComponent } from './add-image-boat.component';

describe('AddImageBoatComponent', () => {
  let component: AddImageBoatComponent;
  let fixture: ComponentFixture<AddImageBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddImageBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddImageBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
