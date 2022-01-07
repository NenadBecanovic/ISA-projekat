import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FishingAdventure } from '../model/fishing-adventure';

@Injectable({
  providedIn: 'root'
})
export class AdventureProfileService {
  private readonly userPath = 'http://localhost:8080/api/fishingAdventure';

  constructor(private _http: HttpClient) {}

  public getFishingAdventureById(id: number): Observable<FishingAdventure>{
    return this._http.get<FishingAdventure>(`${this.userPath}/getFishingAdventureById/`+id)
  }

  public getFishingAdventuresByInstructor(id: number): Observable<FishingAdventure[]>{
    return this._http.get<FishingAdventure[]>(`${this.userPath}/getFishingAdventuresByInstructor/`+id)
  }

  public uploadImage(image: any, id: number){
    
    return this._http.post(`${this.userPath}/image-upload/`+id, image);
  }
}
