import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Room} from "../model/room";
import {Image} from "../model/image";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = environment.backend_api + 'api/image';

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

  public uploadHouseImage(image: any, id: number){
    return this._http.post(`${this.userPath}/uploadHouseImage/`+id, image);
  }

  public uploadBoatImage(image: any, id: number){
    return this._http.post(`${this.userPath}/uploadBoatImage/`+id, image);
  }
}
