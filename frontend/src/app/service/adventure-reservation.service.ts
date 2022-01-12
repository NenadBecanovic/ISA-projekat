import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdventureReservation } from '../model/adventure-reservation';

@Injectable({
  providedIn: 'root'
})
export class AdventureReservationService {


  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/fishingAdventureReservations';

  public getAllActionsByFishingAdventureId(id: number): Observable<AdventureReservation[]> {
    return this._http.get<AdventureReservation[]>(`${this.userPath}/getAllActionsByFishingAdventureId/`+id)
  }
/*
  public getHouseReservationById(id: number): Observable<HouseReservation> {
    return this._http.get<HouseReservation>(`${this.userPath}/getHouseReservationById/`+id)
  }

  public getHouseReservationsByGuestId(id: number): Observable<HouseReservation[]> {
    return this._http.get<HouseReservation[]>(`${this.userPath}/getHouseReservationsByGuestId/`+id)
  }
*/
  public save(adventureReservation: AdventureReservation): Observable<AdventureReservation> { 
    return this._http.post<AdventureReservation>(`${this.userPath}/add`, adventureReservation)
  }

  public saveUnavailablePeriod(adventureReservation: AdventureReservation, instructorId: number): Observable<AdventureReservation> { 
    return this._http.post<AdventureReservation>(`${this.userPath}/saveUnavailablePeriod/`+instructorId, adventureReservation)
  }

  public getAdventureReservationsByInstructorId(id: number): Observable<AdventureReservation[]> {
    return this._http.get<AdventureReservation[]>(`${this.userPath}/getAdventureReservationsByInstructorId/`+id)
  }

  public delete(id: number): Observable<boolean> {
    return this._http.delete<boolean>(`${this.userPath}/delete/`+id)
  }
}
