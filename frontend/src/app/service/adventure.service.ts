import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Boat} from "../model/boat";
import {Adventure} from "../model/adventure";
import {HouseHomeSlide} from "../model/house-home-slide";
import {AdventureHomeSlide} from "../model/adventure-home-slide";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AdventureService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = environment.backend_api + 'api/adventure';

  public findAll(): Observable<Adventure[]>{
    return this._http.get<Adventure[]>(`${this.userPath}/findAll`)
  }

  public findAllAdventuresForHomePage(): Observable<AdventureHomeSlide[]>{
    return this._http.get<AdventureHomeSlide[]>(`${this.userPath}/findAdventuresForHomePage`)
  }
}
