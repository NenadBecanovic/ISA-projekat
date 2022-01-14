import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientHousesComponent } from './client-houses.component';

describe('ClientHousesComponent', () => {
  let component: ClientHousesComponent;
  let fixture: ComponentFixture<ClientHousesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientHousesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientHousesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
