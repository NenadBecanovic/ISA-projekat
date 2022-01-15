import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {House} from "../model/house";
import {BoatHomeSlide} from "../model/boat-home-slide";
import {HouseHomeSlide} from "../model/house-home-slide";
import {HouseReservation} from "../model/house-reservation";
import {ReservationCheck} from "../model/reservation-check";

@Injectable({
  providedIn: 'root'
})
export class HouseService {

  constructor(private _http: HttpClient) {
  }

  private readonly userPath = 'http://localhost:8080/api/house';

  public getHouseById(id: number): Observable<House> {
    return this._http.get<House>(`${this.userPath}/getHouseById/` + id)
  }

  public findAll(): Observable<House[]> {
    return this._http.get<House[]>(`${this.userPath}/findAll/`)
  }

  public findAllHousesForHomePage(): Observable<HouseHomeSlide[]> {
    return this._http.get<HouseHomeSlide[]>(`${this.userPath}/findHouseForHomePage`)
  }

  public edit(house :House):Observable < House > {
      return this._http.put<House>(`${this.userPath}/edit/` + house.id, house)
  }

  public getAll():Observable < House[] > {
      return this._http.get<House[]>(`${this.userPath}/getAll`)
  }

  public delete(id: number): Observable <boolean> {
      return this._http.delete<boolean>(`${this.userPath}/delete/` + id)
  }

  public save(house: House): Observable<House> {
    return this._http.post<House>(`${this.userPath}/add`, house)
  }

  public getAvailableHouses(request :ReservationCheck):Observable < House[] > {
    return this._http.post<House[]>(`${this.userPath}/findAllAvailableHouses`, request)
  }

}
