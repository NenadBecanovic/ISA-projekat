import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Adventure} from "../model/adventure";
import {FishingAdventure} from "../model/fishing-adventure";

@Injectable({
  providedIn: 'root'
})
export class FishingAdventureService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/fishingAdventure';

  public getFishingAdventureById(id: number): Observable<FishingAdventure>{
    return this._http.get<FishingAdventure>(`${this.userPath}/getFishingAdventureById/` + id)
  }
}
