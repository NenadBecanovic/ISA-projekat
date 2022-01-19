import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Appeal } from '../model/appeal';
import { ReportAppealAnswer } from '../model/report-appeal-answer';

@Injectable({
  providedIn: 'root'
})
export class AppealService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/appeal';

  public saveApeal(appeal: Appeal): Observable<Appeal>{
    return this._http.post<Appeal>(`${this.userPath}/saveAppeal`,appeal)
  }

  public getAllAppeals(): Observable<Appeal[]>{
    return this._http.get<Appeal[]>(`${this.userPath}/getAllAppeals`);
  }

  public sendAppealResponse(id: number, adminAnswer: ReportAppealAnswer): Observable<Boolean>{
    return this._http.put<Boolean>(`${this.userPath}/sendAppealResponse/`+id, adminAnswer);
  }

}
