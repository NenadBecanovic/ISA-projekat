import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Appeal } from '../model/appeal';
import { DeleteRequest } from '../model/delete-request';
import { Report } from '../model/report';
import { UserInfo } from '../model/user-info';
import { MyUserService } from '../service/my-user.service';
import { ReportService } from '../service/report.service';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {

  filterTerm!: string;
  content: string = "users";
  reviewedReports: Report[] = new Array<Report>();
  notReviewedReports: Report[] = new Array<Report>();
  answeredAppeals: Appeal[] = new Array<Appeal>();
  unansweredAppeals: Appeal[] = new Array<Appeal>();
  deleteRequests: DeleteRequest[] = new Array<DeleteRequest>();
  //newUsersRequests: NewUserRequest[] = new Array<NewUserRequest>();
  allUsers: UserInfo[] = new Array<UserInfo>();

  constructor(public dialog: MatDialog, private _myUserService: MyUserService, private _reportService: ReportService) { }

  ngOnInit() {
    this.loadData();
  }

  showNewUsersRequests(){
    this.content = "new users"
  }

  showAllUsers(){
    this.content = "users"
  }

  showAppeals(){
    this.content = "appeals"
  }

  showFeedbacks(){
    this.content = "feedbacks"
  }

  showReports(){
    this.content = "reports"
  }

  addAdventure(){
  /*  const dialogRef = this.dialog.open(AddAdventureDialogComponent, {
      width: '800px',
      data: {},
      backdropClass: 'dialog-background'
    });
    dialogRef.componentInstance.newFishingAdventure.instructorId = this.instructor.id;
    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });*/
  }

  showCalendarDialog(){
  /*  const dialogRef = this.dialog.open(CalendarDialogComponent, {
      width: '1500px',
      data: {},
    });
    dialogRef.componentInstance.allReservations = this.allReservations;
    dialogRef.afterClosed().subscribe(result => {
      
    });*/
  }

  defineAvaibilityDialog(){
  /*  const dialogRef = this.dialog.open(DefineAvaibilityPeriodComponent, {
      width: '650px',
      data: {},
    });
    dialogRef.componentInstance.instructorId = this.instructor.id;
    dialogRef.afterClosed().subscribe(result => {     
    });*/
  }

  makeReservation(){
  /*  const dialogRef = this.dialog.open(MakeReservationDialogComponent, {
      width: '500px',
      height: '570px',
      data: {},
    });
    dialogRef.componentInstance.adventure = a;
    dialogRef.componentInstance.instructorId = this.instructor.id;
    dialogRef.componentInstance.adventureReservation.guestId = this.getCurrentGuest();
    dialogRef.afterClosed().subscribe(result => {

    });*/
  }

  loadData() { // ucitavanje iz baze
    this._myUserService.getAllUsers().subscribe(
      (users: UserInfo[]) => {
        this.allUsers = users;
      }
    )
/*
    this._adventureReservationService.getAdventureReservationsByInstructorId(1).subscribe(
      (allReservations: AdventureReservation[]) => {
        this.allReservations = allReservations;
      }
    )*/
  }
}
