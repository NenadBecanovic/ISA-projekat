import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteImageBoatComponent } from './delete-image-boat.component';

describe('DeleteImageBoatComponent', () => {
  let component: DeleteImageBoatComponent;
  let fixture: ComponentFixture<DeleteImageBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteImageBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteImageBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
