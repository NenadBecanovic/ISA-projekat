import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Feedback} from "../model/feedback";
import {Observable} from "rxjs";
import {HouseReservation} from "../model/house-reservation";
import {BoatReservation} from "../model/boat-reservation";
import {AdventureReservation} from "../model/adventure-reservation";
import {ActionDTO} from "../model/action-dto";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ClientReservationService {
  constructor(private _http:HttpClient) { }

  private readonly userPath = environment.backend_api + 'api/user/reservation';

  public saveHouseReservation(houseReservation: HouseReservation): Observable<boolean>{
    return this._http.post<boolean>(`${this.userPath}/house`,houseReservation)
  }

  public saveBoatReservation(boatReservation: BoatReservation): Observable<boolean> {
    return this._http.post<boolean>(`${this.userPath}/boat`,boatReservation)
  }

  public saveAdventureReservation(adventureReservation: AdventureReservation):Observable<boolean> {
    return this._http.post<boolean>(`${this.userPath}/adventure`,adventureReservation)
  }

  public editHouseAction(action: ActionDTO) {
    return this._http.post<boolean>(`${this.userPath}/action/house`,action)
  }

  public editBoatAction(action: ActionDTO) {
    return this._http.post<boolean>(`${this.userPath}/action/boat`,action)
  }

  public editAdventureAction(action: ActionDTO) {
    return this._http.post<boolean>(`${this.userPath}/action/adventure`,action)
  }
}
