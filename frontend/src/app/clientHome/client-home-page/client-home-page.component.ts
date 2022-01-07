import { Component, OnInit } from '@angular/core';
import {Address} from "../../model/address";
import {MyUser} from "../../model/my-user";
import {AuthentificationService} from "../../authentification/authentification.service";


@Component({
  selector: 'app-client-home-page',
  templateUrl: './client-home-page.component.html',
  styleUrls: ['./client-home-page.component.css']
})
export class ClientHomePageComponent implements OnInit {

  sidebarOpen = false;
  address: Address = new Address(0,"","","",0,0,0)
  myUser: MyUser = new MyUser(0,"","","","","","",this.address,"",
    "")

  constructor(private _authentification: AuthentificationService) { }

  ngOnInit(): void {
    this.loadDate()
  }

  loadDate(){
    this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.myUser = user;
      },
      (error) => {
      },
    )
  }

  sideBarToggler($event: any) {
    this.sidebarOpen = !this.sidebarOpen;
  }
}
