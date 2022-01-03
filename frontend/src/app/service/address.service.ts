import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {House} from "../model/house";
import {Address} from "../model/address";

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = 'http://localhost:8080/api/address';

  public getAddressById(id: number): Observable<Address>{
    return this._http.get<Address>(`${this.userPath}/getAddressById/`+id)
  }
}
