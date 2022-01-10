import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditHouseActionComponent } from './edit-house-action.component';

describe('EditHouseActionComponent', () => {
  let component: EditHouseActionComponent;
  let fixture: ComponentFixture<EditHouseActionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditHouseActionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditHouseActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
