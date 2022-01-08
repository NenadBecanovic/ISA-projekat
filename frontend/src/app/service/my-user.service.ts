import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../model/room";
import {MyUser} from "../model/my-user";
import {DeleteRequest} from "../model/delete-request"
import {Image} from "../model/image";
import {Subscription} from "../model/subscription";

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

  public findUserByHouseid(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByHouseId/`+id)
  }

  public findUserByBoatid(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByBoatid/`+id)
  }

  public saveSubscription(subscription: Subscription): Observable<Subscription>{
    return this._http.post<Subscription>(`${this.userPath}/saveSubscription/`,subscription)
  }

  public checkIfUserIsSubscribes(userId: number, ownerId: number): Observable<Boolean>{
    return this._http.get<Boolean>(`${this.userPath}/saveSubscription/`+ userId + '/' + ownerId)
  }

  public findAllSubscriptionsByUserId(userId: number): Observable<Subscription[]>{
    return this._http.get<Subscription[]>(`${this.userPath}/findAllSubscriptionsByUserId/`+ userId)
  }

  public deleteSubscription(subscriptionId: number): Observable<Boolean>{
    return this._http.delete<Boolean>(`${this.userPath}/deleteSubscriptionById/`+ subscriptionId)
  }


}
