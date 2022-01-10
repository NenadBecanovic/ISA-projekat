import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MyUser} from "../../model/my-user";
import {Address} from "../../model/address";
import {MyUserService} from "../../service/my-user.service";

@Component({
  selector: 'app-guest-profile',
  templateUrl: './guest-profile.component.html',
  styleUrls: ['./guest-profile.component.css']
})
export class GuestProfileComponent implements OnInit {

  address: Address = new Address(0,"","","",0,0,31100)
  user: MyUser = new MyUser(0, '','','','','','',this.address, '','');
  houseReservationId: number = 0;

  constructor(private _router: Router, private _route: ActivatedRoute, private _myUserService: MyUserService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.houseReservationId = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  private loadData() {
    this._myUserService.findUserByHouseReservationId(this.houseReservationId).subscribe(
      (user: MyUser) => {
          this.user = user
        console.log(this.user)
      }
    )
  }
}
