import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdventureReservation } from '../model/adventure-reservation';
import { TimePeriod } from '../model/time-period';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AdventureReservationService {


  constructor(private _http:HttpClient) { }

  private readonly userPath = environment.backend_api + 'api/fishingAdventureReservations';

  public getAllActionsByFishingAdventureId(id: number): Observable<AdventureReservation[]> {
    return this._http.get<AdventureReservation[]>(`${this.userPath}/getAllActionsByFishingAdventureId/`+id)
  }

  public getAllByFishingAdventureId(id: number): Observable<AdventureReservation[]> {
    return this._http.get<AdventureReservation[]>(`${this.userPath}/getAllByAdventureId/`+id)
  }

  public getAllAvaibilityPeriodsByInstructorId(id: number): Observable<AdventureReservation[]> {
    return this._http.get<AdventureReservation[]>(`${this.userPath}/getAllAvaibilityPeriodsByInstructorId/`+id)
  }

  public getAdventureReservationsByGuestId(id: number): Observable<AdventureReservation[]> {
    return this._http.get<AdventureReservation[]>(`${this.userPath}/getFishingAdventureReservationsByGuestId/`+id)
  }

  public getAdventureReservationsByInstructorId(id: number): Observable<AdventureReservation[]> {
    return this._http.get<AdventureReservation[]>(`${this.userPath}/getAdventureReservationsByInstructorId/`+id)
  }

  public delete(id: number): Observable<boolean> {
    return this._http.delete<boolean>(`${this.userPath}/delete/`+id)
  }

  public calculatePeriodProfit(timePeriod: TimePeriod): Observable<number>{
    return this._http.get<number>(`${this.userPath}/getCompanyProfit/`+timePeriod.startDate+`/`+timePeriod.endDate)
  }
}
