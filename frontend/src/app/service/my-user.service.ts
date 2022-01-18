import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../model/room";
import {MyUser} from "../model/my-user";
import {DeleteRequest} from "../model/delete-request"
import {Image} from "../model/image";
import {Subscription} from "../model/subscription";
import {Feedback} from "../model/feedback";
import {CancelReservation} from "../model/cancel-reservation";
import {Appeal} from "../model/appeal";
import { UserInfo } from '../model/user-info';
import { AdminAnswer } from '../model/admin-answer';
import { ReportAppealAnswer } from '../model/report-appeal-answer';
import { NewUserRequest } from '../model/new-user-request';
import { FishingAdventureInstructorDTO } from '../model/fishing-adventure-instructorDTO';
import {ReservationCheck} from "../model/reservation-check";
import {Boat} from "../model/boat";

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

  public getAllByBoatId(id: number): Observable<MyUser[]>{
    return this._http.get<MyUser[]>(`${this.userPath}/getAllByBoatId/`+id)
  }

  public findUserByHouseReservationId(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByHouseReservationId/`+id)
  }

  public findUserByBoatReservationId(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByBoatReservationId/`+id)
  }

  public findUserByHouseid(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByHouseId/`+id)
  }

  public findUserByBoatId(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByBoatId/`+id)
  }

  public saveSubscription(subscription: Subscription): Observable<Subscription>{
    return this._http.post<Subscription>(`${this.userPath}/saveSubscription/`,subscription)
  }

  public checkIfUserIsSubscribes(userId: number, ownerId: number): Observable<Boolean>{
    return this._http.get<Boolean>(`${this.userPath}/checkIfUserIsSubscribed/`+ userId + '/' + ownerId)
  }

  public findAllSubscriptionsByUserId(userId: number): Observable<Subscription[]>{
    return this._http.get<Subscription[]>(`${this.userPath}/findAllSubscriptionsByUserId/`+ userId)
  }

  public deleteSubscription(subscriptionId: number): Observable<Boolean>{
    return this._http.delete<Boolean>(`${this.userPath}/deleteSubscriptionById/`+ subscriptionId)
  }

  public saveApeal(appeal: Appeal): Observable<Appeal>{
    return this._http.post<Appeal>(`${this.userPath}/saveAppeal`,appeal)
  }

  public findUserByFishingAdventureReservationId(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByFishingAdventureReservationId/`+id)
  }

  public cancelReservation(cancelReservation: CancelReservation): Observable<CancelReservation>{
    return this._http.post<CancelReservation>(`${this.userPath}/cancelReservation`, cancelReservation)
  }

  public getAllUsers(): Observable<UserInfo[]>{
    return this._http.get<UserInfo[]>(`${this.userPath}/getAllUsers`);
  }

  public deleteUser(id: number): Observable<Boolean>{
    return this._http.put<Boolean>(`${this.userPath}/delete`,id);
  }

  public deleteUserWithRequest(id: number, adminAnswer: AdminAnswer): Observable<Boolean>{
    return this._http.put<Boolean>(`${this.userPath}/deleteUserWithRequest/`+id, adminAnswer);
  }

  public declineDeleteRequest(id: number, adminAnswer: AdminAnswer): Observable<Boolean>{
    return this._http.put<Boolean>(`${this.userPath}/declineDeleteRequest/`+id, adminAnswer);
  }

  public getAllDeleteRequests(): Observable<DeleteRequest[]>{
    return this._http.get<DeleteRequest[]>(`${this.userPath}/getAllDeleteRequests`)
  }

  public getAllAppeals(): Observable<Appeal[]>{
    return this._http.get<Appeal[]>(`${this.userPath}/getAllAppeals`);
  }

  public sendAppealResponse(id: number, adminAnswer: ReportAppealAnswer): Observable<Boolean>{
    return this._http.put<Boolean>(`${this.userPath}/sendAppealResponse/`+id, adminAnswer);
  }

  public getAllNewUserRequests(): Observable<NewUserRequest[]>{
    return this._http.get<NewUserRequest[]>(`${this.userPath}/getAllNewUserRequests`);
  }

  public activateNewUser(id: number): Observable<Boolean>{
    return this._http.put<Boolean>(`${this.userPath}/activateNewUser`,id);
  }

  public declineNewUserRequest(id: number, adminAnswer: AdminAnswer): Observable<Boolean>{
    return this._http.put<Boolean>(`${this.userPath}/declineNewUserRequest/`+id, adminAnswer);
  }

  public editInstructorPersonalDescription(id: number, personalDescription: String): Observable<Boolean>{
    return this._http.put<Boolean>(`${this.userPath}/editInstructorPersonalDescription/`+id, personalDescription);
  }

  public getAllInstructors(): Observable<MyUser[]>{
    return this._http.get<MyUser[]>(`${this.userPath}/getAllInstructors`)
  }

  public getUserById(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserById/` + id)
  }

  public findUserByAdventureId(id: number): Observable<MyUser>{
    return this._http.get<MyUser>(`${this.userPath}/findUserByAdventureId/`+id)
  }

  public findAllAvailableInstructors(request: ReservationCheck): Observable<MyUser[]>{
    return this._http.post<MyUser[]>(`${this.userPath}/getAllAvailableInstructors`, request)
  }
}

