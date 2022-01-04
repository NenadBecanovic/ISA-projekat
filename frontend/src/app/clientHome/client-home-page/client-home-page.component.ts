import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-client-home-page',
  templateUrl: './client-home-page.component.html',
  styleUrls: ['./client-home-page.component.css']
})
export class ClientHomePageComponent implements OnInit {

  sidebarOpen = false;

  constructor() { }

  ngOnInit(): void {
    this.loadDate()
  }

  loadDate(){

  }

  sideBarToggler($event: any) {
    this.sidebarOpen = !this.sidebarOpen;
  }
}
