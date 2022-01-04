import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../model/room";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/room';

  public getAllByHouseId(id: number): Observable<Room[]>{
    return this._http.get<Room[]>(`${this.userPath}/getAllByHouseId/`+id)
  }
}
