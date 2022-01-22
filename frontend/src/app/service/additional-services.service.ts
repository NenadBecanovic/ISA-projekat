import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../model/room";
import {AdditionalService} from "../model/additional-service";
import {HouseReservation} from "../model/house-reservation";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AdditionalServicesService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = environment.backend_api + 'api/additionalServices';

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

  public getAllByBoatReservationId(id: number): Observable<AdditionalService[]>{
    return this._http.get<AdditionalService[]>(`${this.userPath}/getAllByBoatReservationId/`+id)
  }

  public delete(id: number): Observable<boolean> {
    return this._http.delete<boolean>(`${this.userPath}/delete/`+id)
  }

  public save(additionalService: AdditionalService): Observable<AdditionalService> {
    return this._http.post<AdditionalService>(`${this.userPath}/add`, additionalService)
  }
}
