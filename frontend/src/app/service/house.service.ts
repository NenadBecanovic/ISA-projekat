import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {House} from "../model/house";

@Injectable({
  providedIn: 'root'
})
export class HouseService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/house';

  public getHouseById(id: number): Observable<House>{
    return this._http.get<House>(`${this.userPath}/getHouseById/`+id)
  }

  public edit(house: House): Observable<House>{
    return this._http.put<House>(`${this.userPath}/edit/`+ house.id, house)
  }

}
