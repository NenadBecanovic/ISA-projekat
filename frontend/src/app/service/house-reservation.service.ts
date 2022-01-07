import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HouseReservationSlide} from "../model/house-reservation-slide";
import {HouseReservation} from "../model/house-reservation";

@Injectable({
  providedIn: 'root'
})
export class HouseReservationService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/houseReservations';

  public getAllByHouseId(id: number): Observable<HouseReservationSlide[]> {
    return this._http.get<HouseReservationSlide[]>(`${this.userPath}/getAllByHouseId/`+id)
  }

  public getAllActionsByHouseId(id: number): Observable<HouseReservationSlide[]> {
    return this._http.get<HouseReservationSlide[]>(`${this.userPath}/getAllActionsByHouseId/`+id)
  }

  public getAllByHouseIdPlane(id: number): Observable<HouseReservation[]> {
    return this._http.get<HouseReservation[]>(`${this.userPath}/getAllByHouseIdPlane/`+id)
  }

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
  }
}
