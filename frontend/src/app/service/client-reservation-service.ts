import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Feedback} from "../model/feedback";
import {Observable} from "rxjs";
import {HouseReservation} from "../model/house-reservation";
import {BoatReservation} from "../model/boat-reservation";

@Injectable({
  providedIn: 'root'
})
export class ClientReservationService {
  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/user/reservation';

  public saveHouseReservation(houseReservation: HouseReservation): Observable<boolean>{
    return this._http.post<boolean>(`${this.userPath}/house`,houseReservation)
  }

  public saveBoatReservation(boatReservation: BoatReservation) {
    return this._http.post<boolean>(`${this.userPath}/boat`,boatReservation)
  }
}
