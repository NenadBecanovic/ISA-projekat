import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteImageHouseComponent } from './delete-image-house.component';

describe('DeleteImageHouseComponent', () => {
  let component: DeleteImageHouseComponent;
  let fixture: ComponentFixture<DeleteImageHouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteImageHouseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteImageHouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
