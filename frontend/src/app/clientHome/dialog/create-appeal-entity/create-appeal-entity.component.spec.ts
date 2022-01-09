import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAppealEntityComponent } from './create-appeal-entity.component';

describe('CreateAppealEntityComponent', () => {
  let component: CreateAppealEntityComponent;
  let fixture: ComponentFixture<CreateAppealEntityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateAppealEntityComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAppealEntityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
