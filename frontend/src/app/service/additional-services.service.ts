import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../model/room";
import {AdditionalService} from "../model/additional-service";

@Injectable({
  providedIn: 'root'
})
export class AdditionalServicesService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/additionalServices';

  public getAllByHouseId(id: number): Observable<AdditionalService[]>{
    return this._http.get<AdditionalService[]>(`${this.userPath}/getAllByHouseId/`+id)
  }

  public getAllByBoatId(id: number): Observable<AdditionalService[]>{
    return this._http.get<AdditionalService[]>(`${this.userPath}/getAllByBoatId/`+id)
  }

  public getAllByHouseReservationId(id: number): Observable<AdditionalService[]>{
    return this._http.get<AdditionalService[]>(`${this.userPath}/getAllByHouseReservationId/`+id)
  }

  public getAllByFishingAdventureId(id: number): Observable<AdditionalService[]>{
    return this._http.get<AdditionalService[]>(`${this.userPath}/getAllByFishingAdventureId/`+id)
  }
}
