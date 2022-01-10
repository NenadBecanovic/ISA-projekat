import {Injectable, Injector} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';

import {Observable} from 'rxjs';
import {AuthentificationService} from "../authentification/authentification.service";



@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private inj: Injector) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {


    const authService: AuthentificationService = this.inj.get(AuthentificationService);
    request = request.clone({
      setHeaders: {
        Authorization: `X-Auth-Token ${authService.getToken()}`
      }
    });


    return next.handle(request)

  }
}

