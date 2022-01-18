import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AdditionalService } from '../model/additional-service';
import { AdventureReservation } from '../model/adventure-reservation';
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

  public save(fishingAdventure: NewFishingAdventure): Observable<number> {
    return this._http.post<number>(`${this.userPath}/add`, fishingAdventure)
  }

  public edit(fishingAdventure :FishingAdventure):Observable <FishingAdventure> {
    return this._http.put<FishingAdventure>(`${this.userPath}/edit/` + fishingAdventure.id, fishingAdventure)
  }

  public delete(id: number): Observable<Boolean> {
    return this._http.delete<Boolean>(`${this.userPath}/delete/`+id)
  }

  public saveAdditionalService(additionalService: AdditionalService): Observable<AdditionalService> {
    return this._http.post<AdditionalService>(`${this.userPath}/addAdditionalService`, additionalService)
  }

  public saveReservation(adventureReservation: AdventureReservation): Observable<AdventureReservation> {
    return this._http.post<AdventureReservation>(`${this.userPath}/addReservation`, adventureReservation)
  }

  public saveUnavailablePeriod(adventureReservation: AdventureReservation, instructorId: number): Observable<AdventureReservation> {
    return this._http.post<AdventureReservation>(`${this.userPath}/saveUnavailablePeriod/`+instructorId, adventureReservation)
  }

  public getAllActionsByInstructorId(id: number): Observable<AdventureReservation[]> {
    return this._http.get<AdventureReservation[]>(`${this.userPath}/getAllActionsByInstructorId/`+id)
  }
}
