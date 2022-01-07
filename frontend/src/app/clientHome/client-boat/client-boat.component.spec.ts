import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientBoatComponent } from './client-boat.component';

describe('ClientBoatComponent', () => {
  let component: ClientBoatComponent;
  let fixture: ComponentFixture<ClientBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
