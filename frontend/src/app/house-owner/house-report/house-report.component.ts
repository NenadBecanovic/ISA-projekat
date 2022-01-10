import { Component, OnInit } from '@angular/core';
import {Report} from "../../model/report";
import {ActivatedRoute, Router} from "@angular/router";
import {HouseReservation} from "../../model/house-reservation";
import {ReportService} from "../../service/report.service";
import {AlertService} from "ngx-alerts";

@Component({
  selector: 'app-house-report',
  templateUrl: './house-report.component.html',
  styleUrls: ['./house-report.component.css']
})
export class HouseReportComponent implements OnInit {

  houseReservationId: number = 0;
  houseId: number = 0;
  report: Report = new Report(0, '', false, false, 0, 0, 0)
  alreadyDone: boolean = false;

  constructor(private _route: ActivatedRoute, private _reportService: ReportService, private _router: Router, private _alertService: AlertService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.houseReservationId = +this._route.snapshot.paramMap.get('id');
    // @ts-ignore
    this.houseId = +this._route.snapshot.paramMap.get('houseId');

    this.loadData();
  }

  addReport() {
    this.report.houseReservationId = this.houseReservationId;

    this._reportService.save(this.report).subscribe(
      (report: Report) => {
        this._router.navigate(['house-profile-for-house-owner/', this.houseId])
      },
      (error) => {
        this._alertService.danger('Doslo je do greske');
      }
    )
  }

  private loadData() {
    this._reportService.getReportByHouseReservationId(this.houseReservationId).subscribe(
      (report: Report) => {
        this.alreadyDone = true;
      }
    )
  }
}
