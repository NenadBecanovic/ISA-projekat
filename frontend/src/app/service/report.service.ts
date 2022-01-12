import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {House} from "../model/house";
import {Observable} from "rxjs";
import {Report} from "../model/report";
import {Room} from "../model/room";
import { ReportInfo } from '../model/report-info';
import { ReportAppealAnswer } from '../model/report-appeal-answer';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  constructor(private _http: HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/report';

  public save(report: Report): Observable<Report> {
    return this._http.post<Report>(`${this.userPath}/add`, report)
  }

  public getReportByHouseReservationId(id: number): Observable<Report> {
    return this._http.get<Report>(`${this.userPath}/getReportByHouseReservationId/`+ id)
  }

  public getReportByBoatReservationId(id: number): Observable<Report> {
    return this._http.get<Report>(`${this.userPath}/getReportByBoatReservationId/`+ id)
  }

  public getAllReports(): Observable<ReportInfo[]> {
    return this._http.get<ReportInfo[]>(`${this.userPath}/getAllReports`)
  }

  public sendReportResponse(id: number, answer: ReportAppealAnswer): Observable<Boolean> {
    return this._http.put<Boolean>(`${this.userPath}/sendReportResponse`+id, answer);
  }
}
