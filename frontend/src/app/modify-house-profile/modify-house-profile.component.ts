import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-modify-house-profile',
  templateUrl: './modify-house-profile.component.html',
  styleUrls: ['./modify-house-profile.component.css']
})
export class ModifyHouseProfileComponent implements OnInit {

  id: number = 0;
  constructor(private _route: ActivatedRoute) { }

  ngOnInit(): void {
    // @ts-ignore
    this.id = +this._route.snapshot.paramMap.get('id');
    console.log(this.id)
  }

}
