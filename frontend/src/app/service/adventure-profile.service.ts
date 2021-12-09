import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdventureProfileService {
  private readonly adventurePath = 'http://localhost:8080/api/adventure';

  constructor(private _http: HttpClient) {}


  public uploadImage(image: File){
    const formData = new FormData();

    formData.append('image', image);

    return this._http.post(this.adventurePath + '/image-upload', formData);
  }

}
