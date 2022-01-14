import { Component, OnInit } from '@angular/core';
import {MyUser} from "../../model/my-user";
import {Address} from "../../model/address";
import {Adventure} from "../../model/adventure";
import {ActivatedRoute} from "@angular/router";
import {MyUserService} from "../../service/my-user.service";

@Component({
  selector: 'app-instructor',
  templateUrl: './instructor.component.html',
  styleUrls: ['./instructor.component.css']
})
export class InstructorComponent implements OnInit {
  address: Address = new Address(0,"","","",0,0, 0)
  instructor: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  filterTerm: string = '';
  adventures: Adventure[] = new Array();
  id:number = 0;

  constructor(private _route: ActivatedRoute, private _myUserService: MyUserService) { }

  ngOnInit(): void {
    // @ts-ignore
    this.id =  +this._route.snapshot.paramMap.get('email');
    this.loadData(this.id)
  }

  loadData(id: number) {

      this._myUserService.getUserById(id).subscribe((myUser:MyUser)=>
      {
        this.instructor = myUser
      })
  }
}
