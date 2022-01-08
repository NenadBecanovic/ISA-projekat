import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientSubscriptionsComponent } from './client-subscriptions.component';

describe('ClientSubscriptionsComponent', () => {
  let component: ClientSubscriptionsComponent;
  let fixture: ComponentFixture<ClientSubscriptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientSubscriptionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientSubscriptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
