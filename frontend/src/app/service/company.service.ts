import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Company } from '../model/company';
import { UserCategory } from '../model/user-category';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/company';

  public save(userCategory: UserCategory): Observable<UserCategory> {
    return this._http.post<UserCategory>(`${this.userPath}/addCategory`, userCategory)
  }

  public getAllUserCategories(): Observable<UserCategory[]>{
    return this._http.get<UserCategory[]>(`${this.userPath}/getAllUserCategories`)
  }

  public getCompanyInfo(): Observable<Company>{
    return this._http.get<Company>(`${this.userPath}/getCompanyInfo`)
  }

  public delete(id: number): Observable<boolean> {
    return this._http.delete<boolean>(`${this.userPath}/delete/`+id)
  }

}
