import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdventureChartComponent } from './adventure-chart.component';

describe('AdventureChartComponent', () => {
  let component: AdventureChartComponent;
  let fixture: ComponentFixture<AdventureChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdventureChartComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdventureChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
