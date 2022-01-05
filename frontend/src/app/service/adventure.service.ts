import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Boat} from "../model/boat";
import {Adventure} from "../model/adventure";
import {HouseHomeSlide} from "../model/house-home-slide";
import {AdventureHomeSlide} from "../model/adventure-home-slide";

@Injectable({
  providedIn: 'root'
})
export class AdventureService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/adventure';

  // public getBoatById(id: number): Observable<Boat>{
  //   return this._http.get<Boat>(`${this.userPath}/getBoatById/`+id)
  // }

  public findAll(): Observable<Adventure[]>{
    return this._http.get<Adventure[]>(`${this.userPath}/findAll`)
  }

  public findAllAdventuresForHomePage(): Observable<AdventureHomeSlide[]>{
    return this._http.get<AdventureHomeSlide[]>(`${this.userPath}/findAdventuresForHomePage`)
  }
}
