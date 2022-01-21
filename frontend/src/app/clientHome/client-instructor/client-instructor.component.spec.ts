import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientInstructorComponent } from './client-instructor.component';

describe('ClientInstructorComponent', () => {
  let component: ClientInstructorComponent;
  let fixture: ComponentFixture<ClientInstructorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientInstructorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientInstructorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
