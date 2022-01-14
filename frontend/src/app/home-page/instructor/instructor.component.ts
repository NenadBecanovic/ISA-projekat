import { Component, OnInit } from '@angular/core';
import {MyUser} from "../../model/my-user";
import {Address} from "../../model/address";
import {Adventure} from "../../model/adventure";
import {ActivatedRoute, Router} from "@angular/router";
import {MyUserService} from "../../service/my-user.service";
import {AdventureProfileService} from "../../service/adventure-profile.service";
import {FishingAdventure} from "../../model/fishing-adventure";

@Component({
  selector: 'app-instructor',
  templateUrl: './instructor.component.html',
  styleUrls: ['./instructor.component.css']
})
export class InstructorComponent implements OnInit {
  address: Address = new Address(0,"","","",0,0, 0)
  instructor: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  filterTerm: string = '';
  adventures: FishingAdventure[] = new Array();
  id:number = 0;

  constructor(private _route: ActivatedRoute, private _myUserService: MyUserService, private _adventureService: AdventureProfileService,
              private _router: Router) { }

  ngOnInit(): void {
    // @ts-ignore
    this.id =  +this._route.snapshot.paramMap.get('id');
    this.loadData(this.id)
  }

  loadData(id: number) {

      this._myUserService.getUserById(id).subscribe((myUser:MyUser)=>
      {
        this.instructor = myUser

      },(error => {}))
    this._adventureService.getFishingAdventuresByInstructor(id).subscribe((fishingAdventures:FishingAdventure[]) =>
    {
      this.adventures = fishingAdventures;
      console.log(this.adventures)
    })
  }

  showAdventure(id: number) {
    this._router.navigate(['adventure', id])
  }
}
