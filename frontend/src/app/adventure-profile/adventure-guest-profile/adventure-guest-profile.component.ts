import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Address } from 'src/app/model/address';
import { MyUser } from 'src/app/model/my-user';
import { MyUserService } from 'src/app/service/my-user.service';

@Component({
  selector: 'app-adventure-guest-profile',
  templateUrl: './adventure-guest-profile.component.html',
  styleUrls: ['./adventure-guest-profile.component.css']
})
export class AdventureGuestProfileComponent implements OnInit {

  address: Address = new Address(0,"","","",0,0,0)
  user: MyUser = new MyUser(0, '','','','','','',this.address, '','');
  id: number = 0;

  constructor(private _router: Router, private _route: ActivatedRoute, private _myUserService: MyUserService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.id = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  private loadData() {
    this._myUserService.getUserById(this.id).subscribe(
      (user: MyUser) => {
          this.user = user
        console.log(this.user)
      }
    )
  }

}
