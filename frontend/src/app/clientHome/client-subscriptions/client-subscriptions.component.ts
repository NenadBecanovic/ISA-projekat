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

// const ELEMENT_DATA: PeriodicElement[] = [
//   {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
//   {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
//   {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
//   {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
//   {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
//   {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
//   {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
//   {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
//   {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
//   {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
// ];

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
    this._myUserService.deleteSubscription(id).subscribe(   // subscribe - da bismo dobili odgovor beka
      (boolean: Boolean) => {
        console.log(boolean)
      },
      (error) => {

      },
    )
  }
}
