import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";



@Component({
  selector: 'app-home-dashboard',
  templateUrl: './home-dashboard.component.html',
  styleUrls: ['./home-dashboard.component.css']
})
export class HomeDashboardComponent implements OnInit {

  constructor(private _router: Router) {}


  ngOnInit(): void {
  }


  goToAllHouses() {
    this._router.navigate(['house']);
  }

}
