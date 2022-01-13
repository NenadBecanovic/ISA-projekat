import { Component, OnInit } from '@angular/core';
import {AuthentificationService} from "../../auth/authentification/authentification.service";

@Component({
  selector: 'app-plain-header',
  templateUrl: './plain-header.component.html',
  styleUrls: ['./plain-header.component.css']
})
export class PlainHeaderComponent implements OnInit {

  isCollapsed: boolean = true;

  constructor(private _authService: AuthentificationService) { }

  ngOnInit(): void {
  }

  logout() {
    this._authService.logout()
  }
}
