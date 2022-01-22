import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {House} from "../model/house";
import {Address} from "../model/address";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor(private _http:HttpClient) { }

  private readonly userPath = environment.backend_api + 'api/address';

  public getAddressById(id: number): Observable<Address>{
    return this._http.get<Address>(`${this.userPath}/getAddressById/`+id)
  }
}
