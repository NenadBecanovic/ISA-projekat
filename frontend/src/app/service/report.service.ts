import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {House} from "../model/house";
import {Observable} from "rxjs";
import {Report} from "../model/report";
import {Room} from "../model/room";

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
}
