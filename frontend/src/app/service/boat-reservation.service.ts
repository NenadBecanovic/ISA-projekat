import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HouseReservationSlide} from "../model/house-reservation-slide";
import {BoatReservationSlide} from "../model/boat-reservation-slide";

@Injectable({
  providedIn: 'root'
})
export class BoatReservationService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/boatReservations';

  public getAllByBoatId(id: number): Observable<BoatReservationSlide[]> {
    return this._http.get<BoatReservationSlide[]>(`${this.userPath}/getAllByBoatId/`+id)
  }
}
