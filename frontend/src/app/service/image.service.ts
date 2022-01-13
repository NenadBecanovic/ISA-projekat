import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../model/room";
import {Image} from "../model/image";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/image';

  public getAllByHouseId(id: number): Observable<Image[]>{
    return this._http.get<Image[]>(`${this.userPath}/getAllByHouseId/`+id)
  }

  public getAllByBoatId(id: number): Observable<Image[]>{
    return this._http.get<Image[]>(`${this.userPath}/getAllByBoatId/`+id)
  }

  public getAllByFishingAdventureId(id: number): Observable<Image[]>{
    return this._http.get<Image[]>(`${this.userPath}/getAllByFishingAdventureId/`+id)
  }

  public uploadImage(image: any, id: number){
    return this._http.post(`${this.userPath}/adventureImageUpload/`+id, image);
  }

  public deleteImage(id: number){
    return this._http.delete(`${this.userPath}/deleteImage/`+id);
  }
}
