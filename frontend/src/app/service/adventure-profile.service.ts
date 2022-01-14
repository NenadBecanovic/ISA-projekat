import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FishingAdventure } from '../model/fishing-adventure';
import { NewFishingAdventure } from '../model/new-fishing-adventure';

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
    console.log('uslo')
    return this._http.get<FishingAdventure[]>(`${this.userPath}/getFishingAdventuresByInstructor/`+id)
  }

  public save(fishingAdventure: NewFishingAdventure): Observable<FishingAdventure> {
    alert("SAVE")
    return this._http.post<FishingAdventure>(`${this.userPath}/add`, fishingAdventure)
  }

  public edit(fishingAdventure :FishingAdventure):Observable <FishingAdventure> {
    return this._http.put<FishingAdventure>(`${this.userPath}/edit/` + fishingAdventure.id, fishingAdventure)
  }
}
