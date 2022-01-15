import { Component, OnInit } from '@angular/core';
import {MyUser} from "../../model/my-user";
import {MyUserService} from "../../service/my-user.service";
import {Router} from "@angular/router";
import * as _ from "underscore";

@Component({
  selector: 'app-client-instructors',
  templateUrl: './client-instructors.component.html',
  styleUrls: ['./client-instructors.component.css']
})
export class ClientInstructorsComponent implements OnInit {
  instructors: MyUser[] = new Array()
  instructorsFilter: MyUser[] = new Array()
  instructorsSearch: MyUser[] = new Array()
  instructorName: any;
  displayedColumns: string[] = ['Ime', 'Prezime', 'Adresa', 'Ocena', 'avanture']
  instructorGrade: any;
  instructorSurname: any;

  constructor(private _myUserService: MyUserService, private _router: Router) { }

  ngOnInit(): void {
    this.loadData()
  }

  loadData() {
    this._myUserService.getAllInstructors().subscribe(
      (users:MyUser[])=>{
        this.instructors = users;
        this.instructorsFilter = users
        this.instructorsSearch = users
      },(error => {

      }))
  }

  searchInstructors() {

  }

  changeSortName() {
    if(this.instructorName === 1){
      this.instructors = _.sortBy(this.instructors, 'firstName',);
    }else if(this.instructorName ===2){
      this.instructors = _.sortBy(this.instructors, 'firstName',).reverse();
    }
  }

  viewProfile(id: number) {
    console.log(id)
    this._router.navigate(['instructor',id])
  }

  changeSortGrade() {
    if(this.instructorGrade === 1){
      this.instructors = _.sortBy(this.instructors, 'grade',);
    }else if(this.instructorGrade ===2){
      this.instructors = _.sortBy(this.instructors, 'grade',).reverse();
    }
  }

  changeSortLastName() {
    if(this.instructorSurname === 1){
      this.instructors = _.sortBy(this.instructors, 'lastName',);
    }else if(this.instructorSurname ===2){
      this.instructors = _.sortBy(this.instructors, 'lastName',).reverse();
    }
  }

}
