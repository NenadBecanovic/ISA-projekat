import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  constructor() { }


  isLoggedIn(): boolean {
    return this.getToken() !== '';
  }

  getToken(): string {
    // @ts-ignore
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    //  console.log(currentUser);
    const token = currentUser && currentUser.token;
    return token ? token : '';
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
  }

  isLoggedInUser(): boolean {
    return this.getToken() !== '' && this.getCurrentUser().roles.indexOf('ROLE_USER') !== -1;
  }

  isLoggedInAdmin(): boolean {
    return this.getToken() !== '' && this.getCurrentUser().roles.indexOf('ROLE_ADMINISTRATOR') !== -1;
  }

  getCurrentUser(): any {
    if (localStorage['currentUser']) {
      return JSON.parse(localStorage['currentUser']);
    } else {
      return undefined;
    }
  }
}
