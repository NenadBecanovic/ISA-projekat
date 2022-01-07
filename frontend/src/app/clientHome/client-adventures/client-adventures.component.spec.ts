import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientAdventuresComponent } from './client-adventures.component';

describe('ClientAdventuresComponent', () => {
  let component: ClientAdventuresComponent;
  let fixture: ComponentFixture<ClientAdventuresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientAdventuresComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientAdventuresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
