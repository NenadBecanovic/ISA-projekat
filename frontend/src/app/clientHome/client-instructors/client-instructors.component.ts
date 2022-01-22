import { Component, OnInit } from '@angular/core';
import {MyUser} from "../../model/my-user";
import {MyUserService} from "../../service/my-user.service";
import {Router} from "@angular/router";
import * as _ from "underscore";
import {Boat} from "../../model/boat";
import {AlertService} from "ngx-alerts";
import {ReservationCheck} from "../../model/reservation-check";

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
  instructorFilterGrade: number = 0;
  dateStartString: string = '';
  dateStart: Date = new Date();
  hourNumber: number = 0;
  maxGuest: string = '';
  request: ReservationCheck = new ReservationCheck();

  constructor(private _myUserService: MyUserService, private _router: Router, private _alertService: AlertService) { }

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
    if(this.hourNumber <1){
      this._alertService.warning("Unesite broj dana veci od 0")
      return;
    }else{
      if(this.maxGuest != ''){
        try {
          var maxGuestNumber = Number.parseInt(this.maxGuest)
          if(maxGuestNumber < 1){
            this._alertService.warning("Unesite broj gostiju veci od 0")
            return;
          }
        }catch (e){
          this._alertService.warning("Unesite broj gostiju veci od 0")
          return;
        }
      }
      maxGuestNumber = 0;
    }
    var startDate = Date.parse(this.dateStartString)
    this.dateStart =  new Date(startDate)

    var actionStart  = Number(this.dateStart)
    this.request.startMilis = actionStart
    this.request.endMilis = actionStart + this.hourNumber*60*60*1000
    this.request.maxGuest = maxGuestNumber;
    this._myUserService.findAllAvailableInstructors(this.request).subscribe(
      (users:MyUser[])=>{
        this.instructors = users;
        this.instructorsFilter = users
        this.instructorsSearch = users
      },(error => {

      }))

  }

  changeSortName() {
    this.instructorSurname = 0;
    this.instructorGrade = 0;
    if(this.instructorName === 1){
      this.instructors = _.sortBy(this.instructors, 'firstName',);
    }else if(this.instructorName ===2){
      this.instructors = _.sortBy(this.instructors, 'firstName',).reverse();
    }
  }

  viewProfile(id: number) {
    this._router.navigate(['client/instructor',id])
  }

  changeSortGrade() {
    this.instructorSurname = 0;
    this.instructorName = 0;
    if(this.instructorGrade === 1){
      this.instructors = _.sortBy(this.instructors, 'grade',);
    }else if(this.instructorGrade ===2){
      this.instructors = _.sortBy(this.instructors, 'grade',).reverse();
    }
  }

  changeSortLastName() {
    this.instructorGrade = 0;
    this.instructorName = 0;
    if(this.instructorSurname === 1){
      this.instructors = _.sortBy(this.instructors, 'lastName',);
    }else if(this.instructorSurname ===2){
      this.instructors = _.sortBy(this.instructors, 'lastName',).reverse();
    }
  }

  filterGrade() {
    if(this.instructorFilterGrade == 5){
      this.instructors = this.instructorsFilter.filter(s => s.grade == 5 )
    }else if(this.instructorFilterGrade == 4) {
      this.instructors = this.instructorsFilter.filter(s => s.grade >= 4 && s.grade < 5)
    }else if(this.instructorFilterGrade == 3) {
      this.instructors = this.instructorsFilter.filter(s => s.grade >= 3 && s.grade < 4)
    }else if(this.instructorFilterGrade == 2) {
      this.instructors = this.instructorsFilter.filter(s => s.grade >= 2 && s.grade < 3)
    }else if(this.instructorFilterGrade == 1) {
      this.instructors = this.instructorsFilter.filter(s => s.grade >= 1 && s.grade < 2)
    }else if(this.instructorFilterGrade == 0) {
      this.instructors = this.instructorsFilter.filter(s => s.firstName.includes(''))
    }
  }

  restartAll() {
    this.instructorSurname = 0;
    this.instructorName = 0;
    this.instructorGrade = 0;
    this.instructorFilterGrade = 0;
    this.loadData()

  }
}
