import { Component, OnInit } from '@angular/core';
import {Report} from "../../model/report";
import {ActivatedRoute, Router} from "@angular/router";
import {ReportService} from "../../service/report.service";
import {AlertService} from "ngx-alerts";

@Component({
  selector: 'app-boat-report',
  templateUrl: './boat-report.component.html',
  styleUrls: ['./boat-report.component.css']
})
export class BoatReportComponent implements OnInit {

  boatReservationId: number = 0;
  boatId: number = 0;
  report: Report = new Report(0, '', false, false, 0, 0, 0)
  alreadyDone: boolean = false;

  constructor(private _route: ActivatedRoute, private _reportService: ReportService, private _router: Router, private _alertService: AlertService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.boatReservationId = +this._route.snapshot.paramMap.get('id');
    // @ts-ignore
    this.boatId = +this._route.snapshot.paramMap.get('boatId');

    this.loadData();
  }

  addReport() {
    this.report.boatReservationId = this.boatReservationId;

    this._reportService.save(this.report).subscribe(
      (report: Report) => {
        this._router.navigate(['boat-profile-for-boat-owner/', this.boatId])
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      }
    )
  }

  private loadData() {
    this._reportService.getReportByBoatReservationId(this.boatReservationId).subscribe(
      (report: Report) => {
        this.alreadyDone = true;
      }
    )
  }
}
