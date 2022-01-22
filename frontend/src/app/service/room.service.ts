import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../model/room";
import {AdditionalService} from "../model/additional-service";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = environment.backend_api + 'api/room';

  public getAllByHouseId(id: number): Observable<Room[]>{
    return this._http.get<Room[]>(`${this.userPath}/getAllByHouseId/`+id)
  }

  public save(room: Room): Observable<Room> {
    return this._http.post<Room>(`${this.userPath}/add`, room)
  }

  public delete(id: number): Observable<boolean> {
    return this._http.delete<boolean>(`${this.userPath}/delete/`+id)
  }
}
