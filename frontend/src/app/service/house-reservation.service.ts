import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HouseReservationSlide} from "../model/house-reservation-slide";

@Injectable({
  providedIn: 'root'
})
export class HouseReservationService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/houseReservations';

  public getAllByHouseId(id: number): Observable<HouseReservationSlide[]> {
    return this._http.get<HouseReservationSlide[]>(`${this.userPath}/getAllByHouseId/`+id)
  }
}
