import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Appeal } from '../model/appeal';
import { DeleteRequest } from '../model/delete-request';
import { Report } from '../model/report';
import { UserInfo } from '../model/user-info';
import { MyUserService } from '../service/my-user.service';
import { ReportService } from '../service/report.service';
import { AppealAnswerDialogComponent } from './appeal-answer-dialog/appeal-answer-dialog.component';
import { DeleteRequestAnswerDialogComponent } from './delete-request-answer-dialog/delete-request-answer-dialog.component';
import { ReportAnswerDialogComponent } from './report-answer-dialog/report-answer-dialog.component';

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
  unansweredDeleteRequests: DeleteRequest[] = new Array<DeleteRequest>();
  answeredDeleteRequests: DeleteRequest[] = new Array<DeleteRequest>();
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

  showDeleteRequests(){
    this.content = "delete requests"
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

  loadData() { // ucitavanje iz baze
    this._myUserService.getAllUsers().subscribe(
      (users: UserInfo[]) => {
        this.allUsers = users;
      }
    )

    this._myUserService.getAllDeleteRequests().subscribe(
      (allRequests: DeleteRequest[]) => {
        for(let request of allRequests){
          if(request.isAnswered){
            this.answeredDeleteRequests.push(request);
          }else{
            this.unansweredDeleteRequests.push(request);
          }
        }
      }
    )
/*
    this._adventureReservationService.getAdventureReservationsByInstructorId(1).subscribe(
      (allReservations: AdventureReservation[]) => {
        this.allReservations = allReservations;
      }
    )*/
  }

  makeReservation(){}

  deleteUser(id: number){
    this._myUserService.deleteUser(id).subscribe(
      (ok: Boolean) => {
        
      },
      (error) => {
        // console.log(error)
      }
    )
  }

  answerOnDeleteRequest(id: number){
    const dialogRef = this.dialog.open(DeleteRequestAnswerDialogComponent, {
      width: '550px',
      height: '250px',
      data: {},
    });
    dialogRef.componentInstance.userId = id;
    dialogRef.afterClosed().subscribe(result => {
      this.loadData();
    });
  }

  answerOnAppeal(ownerId: number, guestId: number){
    const dialogRef = this.dialog.open(AppealAnswerDialogComponent, {
      width: '550px',
      height: '500px',
      data: {},
    });
    dialogRef.componentInstance.ownerId = ownerId;
    dialogRef.componentInstance.guestId = guestId;
    dialogRef.afterClosed().subscribe(result => {
      this.loadData();
    });
  }

  answerOnReport(ownerId: number, guestId: number){
    const dialogRef = this.dialog.open(ReportAnswerDialogComponent, {
      width: '550px',
      height: '500px',
      data: {},
    });
    dialogRef.componentInstance.ownerId = ownerId;
    dialogRef.componentInstance.guestId = guestId;
    dialogRef.afterClosed().subscribe(result => {
      this.loadData();
    });
  }
}
