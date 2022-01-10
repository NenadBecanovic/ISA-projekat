import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MyUserService} from "../service/my-user.service";
import {Address} from "../model/address";
import {MyUser} from "../model/my-user";

@Component({
  selector: 'app-guest-profile-boat',
  templateUrl: './guest-profile-boat.component.html',
  styleUrls: ['./guest-profile-boat.component.css']
})
export class GuestProfileBoatComponent implements OnInit {

  address: Address = new Address(0,"","","",0,0,31100)
  user: MyUser = new MyUser(0, '','','','','','',this.address, '','');
  boatReservationId: number = 0;

  constructor(private _router: Router, private _route: ActivatedRoute, private _myUserService: MyUserService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.boatReservationId = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  private loadData() {
    this._myUserService.findUserByBoatReservationId(this.boatReservationId).subscribe(
      (user: MyUser) => {
        this.user = user
        console.log(this.user)
      }
    )
  }
}
