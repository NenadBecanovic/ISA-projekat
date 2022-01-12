import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatChartComponent } from './boat-chart.component';

describe('BoatChartComponent', () => {
  let component: BoatChartComponent;
  let fixture: ComponentFixture<BoatChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatChartComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
