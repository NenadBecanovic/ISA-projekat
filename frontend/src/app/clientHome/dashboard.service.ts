import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private readonly userPath = 'http://localhost:8080/user';

  constructor(private _http:HttpClient) { }

}
