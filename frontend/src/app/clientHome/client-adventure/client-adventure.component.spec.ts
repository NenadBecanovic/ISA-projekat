import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientAdventureComponent } from './client-adventure.component';

describe('ClientAdventureComponent', () => {
  let component: ClientAdventureComponent;
  let fixture: ComponentFixture<ClientAdventureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientAdventureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientAdventureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
