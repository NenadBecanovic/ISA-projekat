import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../model/room";
import {MyUser} from "../model/my-user";
import {DeleteRequest} from "../model/delete-request"
import {Image} from "../model/image";

@Injectable({
  providedIn: 'root'
})
export class MyUserService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/user';

  public updateUser(myUser: MyUser): Observable<MyUser>{
    return this._http.put<MyUser>(`${this.userPath}/updateUser/`, myUser)
  }

  public createDeleteRequest(deleteRequest: DeleteRequest): Observable<DeleteRequest>{
    return this._http.post<DeleteRequest>(`${this.userPath}/saveDeleteRequest`, deleteRequest)
  }

  public getAllByHouseId(id: number): Observable<MyUser[]>{
    return this._http.get<MyUser[]>(`${this.userPath}/getAllByHouseId/`+id)
  }

  public findUserByHouseReservationId(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByHouseReservationId/`+id)
  }

}
