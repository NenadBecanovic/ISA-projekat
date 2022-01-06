import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientHouseReservationsComponent } from './client-house-reservations.component';

describe('ClientHouseReservationsComponent', () => {
  let component: ClientHouseReservationsComponent;
  let fixture: ComponentFixture<ClientHouseReservationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientHouseReservationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientHouseReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
