import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstructorChartComponent } from './instructor-chart.component';

describe('InstructorChartComponent', () => {
  let component: InstructorChartComponent;
  let fixture: ComponentFixture<InstructorChartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InstructorChartComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InstructorChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
