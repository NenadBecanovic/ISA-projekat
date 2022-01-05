import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {House} from "../model/house";
import {BoatHomeSlide} from "../model/boat-home-slide";
import {HouseHomeSlide} from "../model/house-home-slide";

@Injectable({
  providedIn: 'root'
})
export class HouseService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/house';

  public getHouseById(id: number): Observable<House>{
    return this._http.get<House>(`${this.userPath}/getHouseById/`+id)
  }

  public findAll(): Observable<House[]>{
    return this._http.get<House[]>(`${this.userPath}/findAll/`)
  }

  public findAllHousesForHomePage(): Observable<HouseHomeSlide[]>{
    return this._http.get<HouseHomeSlide[]>(`${this.userPath}/findHouseForHomePage`)
  }
}
