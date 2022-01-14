import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientHouseComponent } from './client-house.component';

describe('ClientHouseComponent', () => {
  let component: ClientHouseComponent;
  let fixture: ComponentFixture<ClientHouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientHouseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientHouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
