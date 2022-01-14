import { Component, OnInit } from '@angular/core';
import {DeleteRequest} from "../../model/delete-request";
import {MyUser} from "../../model/my-user";
import {Address} from "../../model/address";
import {AuthentificationService} from "../../auth/authentification/authentification.service";
import {MyUserService} from "../../service/my-user.service";
import {Subscription} from "../../model/subscription";

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}



@Component({
  selector: 'app-client-subscriptions',
  templateUrl: './client-subscriptions.component.html',
  styleUrls: ['./client-subscriptions.component.css']
})
export class ClientSubscriptionsComponent implements OnInit {
  address: Address = new Address(0,"Luka 11","Novi Sad","Srbija",0,0,21000)
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  displayedColumns: string[] = ['Ime', 'Prezime', 'Email', 'Odjavi se'];
  dataSource: Subscription[] = new Array();

  constructor(private _authentification: AuthentificationService,private _myUserService: MyUserService) { }

  ngOnInit(): void {
    this.loadData();
  }



  loadData() {
    this._authentification.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        this._myUserService.findAllSubscriptionsByUserId(this.user.id).subscribe(   // subscribe - da bismo dobili odgovor beka
          (subscriptions: Subscription[]) => {
                console.log(subscriptions)
                this.dataSource = subscriptions
          },
          (error) => {

          },
        )
      },
      (error) => {
      },
    )


  }

  deleteSubscription(id: number) {
    if (confirm("Da li ste sigurni da zelite da otkazete pretplatu?" )) {
      this._myUserService.deleteSubscription(id).subscribe(   // subscribe - da bismo dobili odgovor beka
        (boolean: Boolean) => {
          this.loadData()
        },
        (error) => {

        },
      )
    }
  }
}
