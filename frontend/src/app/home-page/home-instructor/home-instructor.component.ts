import { Component, OnInit } from '@angular/core';
import {MyUser} from "../../model/my-user";
import {MyUserService} from "../../service/my-user.service";

@Component({
  selector: 'app-home-instructor',
  templateUrl: './home-instructor.component.html',
  styleUrls: ['./home-instructor.component.css']
})
export class HomeInstructorComponent implements OnInit {
  instructors: MyUser[] = new Array()
  instructorNameSearch: any;
  instructorAddressSearch: any;
  instructorName: any;
  displayedColumns: string[] = ['Ime', 'Prezime', 'Adresa', 'Ocena', 'avanture']
  instructorGrade: any;

  constructor(private _myUserService: MyUserService) { }

  ngOnInit(): void {
    this.loadData()
  }

  loadData() {
      this._myUserService.getAllInstructors().subscribe(
        (users:MyUser[])=>{
          this.instructors = users;
          console.log(this.instructors)
        },(error => {

        }))
  }

  searchInstructors() {

  }

  changeSortName() {

  }

  viewProfile(id: number) {

  }

  changeSortGrade() {

  }
}
