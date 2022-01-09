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
    return this._http.get<AdventureReservation[]>(`${this.userPath}/getAllByAdventureId/`+id)
  }
/*
  public getAllActionsByHouseId(id: number): Observable<AdventureReservationSlide[]> {
    return this._http.get<AdventureReservationSlide[]>(`${this.userPath}/getAllActionsByHouseId/`+id)
  }*/
/*
  public getHouseReservationById(id: number): Observable<HouseReservation> {
    return this._http.get<HouseReservation>(`${this.userPath}/getHouseReservationById/`+id)
  }

  public getHouseReservationsByGuestId(id: number): Observable<HouseReservation[]> {
    return this._http.get<HouseReservation[]>(`${this.userPath}/getHouseReservationsByGuestId/`+id)
  }

  public save(houseReservation: HouseReservation): Observable<HouseReservation> { // saljem post zahtev (rezervaciju vikendice) na bekend
    return this._http.post<HouseReservation>(`${this.userPath}/add`, houseReservation)
  }

  public delete(id: number): Observable<boolean> {
    return this._http.delete<boolean>(`${this.userPath}/delete/`+id)
  }*/
}
