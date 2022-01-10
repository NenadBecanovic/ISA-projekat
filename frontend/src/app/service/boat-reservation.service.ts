import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HouseReservationSlide} from "../model/house-reservation-slide";
import {BoatReservationSlide} from "../model/boat-reservation-slide";
import {HouseReservation} from "../model/house-reservation";
import {BoatReservation} from "../model/boat-reservation";

@Injectable({
  providedIn: 'root'
})
export class BoatReservationService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/boatReservations';

  public getAllByBoatId(id: number): Observable<BoatReservationSlide[]> {
    return this._http.get<BoatReservationSlide[]>(`${this.userPath}/getAllByBoatId/`+id)
  }

  public getBoatReservationById(id: number): Observable<BoatReservation> {
    return this._http.get<BoatReservation>(`${this.userPath}/getBoatReservationById/`+id)
  }

  public save(boatReservation: BoatReservation): Observable<BoatReservation> {
    return this._http.post<BoatReservation>(`${this.userPath}/add`, boatReservation)
  }

  public delete(id: number): Observable<boolean> {
    return this._http.delete<boolean>(`${this.userPath}/delete/`+id)
  }

  public getAllActionsByBoatId(id: number): Observable<BoatReservationSlide[]> {
    return this._http.get<BoatReservationSlide[]>(`${this.userPath}/getAllActionsByBoatId/`+id)
  }

  public getAllByBoatIdPlane(id: number): Observable<BoatReservation[]> {
    return this._http.get<BoatReservation[]>(`${this.userPath}/getAllByBoatIdPlane/`+id)
  }

  public getBoatReservationsByGuestId(id: number): Observable<BoatReservation[]> {
    return this._http.get<BoatReservation[]>(`${this.userPath}/getBoatReservationsByGuestId/`+id)
  }

  public getBoatReservationByBoatOwnerId(id: number): Observable<BoatReservation[]> {
    return this._http.get<BoatReservation[]>(`${this.userPath}/getBoatReservationByBoatOwnerId/`+id)
  }
}
