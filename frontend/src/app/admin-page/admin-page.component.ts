import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthentificationService } from '../auth/authentification/authentification.service';
import { ClientProfileComponent } from '../clientHome/dialog/client-profile/client-profile.component';
import { Address } from '../model/address';
import { AdminAnswer } from '../model/admin-answer';
import { Appeal } from '../model/appeal';
import { DeleteRequest } from '../model/delete-request';
import { FeedbackInfo } from '../model/feedback-info';
import { MyUser } from '../model/my-user';
import { NewUserRequest } from '../model/new-user-request';
import { Report } from '../model/report';
import { ReportInfo } from '../model/report-info';
import { UserInfo } from '../model/user-info';
import { FeedbackService } from '../service/feedback.service';
import { MyUserService } from '../service/my-user.service';
import { ReportService } from '../service/report.service';
import { AdminRegistrationDialogComponent } from './admin-registration-dialog/admin-registration-dialog.component';
import { AppealAnswerDialogComponent } from './appeal-answer-dialog/appeal-answer-dialog.component';
import { CompanyProfitDialogComponent } from './company-profit-dialog/company-profit-dialog.component';
import { DeleteRequestAnswerDialogComponent } from './delete-request-answer-dialog/delete-request-answer-dialog.component';
import { EditCompanyRulesDialogComponent } from './edit-company-rules-dialog/edit-company-rules-dialog.component';
import { NewAdminPasswordDialogComponent } from './new-admin-password-dialog/new-admin-password-dialog.component';
import { ReportAnswerDialogComponent } from './report-answer-dialog/report-answer-dialog.component';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {

  filterTerm!: string;
  content: string = "users";
  address: Address = new Address(0, '', '', '', 0, 0, 0);
  admin: MyUser = new MyUser(0,'','','','','','', this.address,'','');
  reviewedReports: ReportInfo[] = new Array<ReportInfo>();
  notReviewedReports: ReportInfo[] = new Array<ReportInfo>();
  answeredAppeals: Appeal[] = new Array<Appeal>();
  unansweredAppeals: Appeal[] = new Array<Appeal>();
  unansweredDeleteRequests: DeleteRequest[] = new Array<DeleteRequest>();
  answeredDeleteRequests: DeleteRequest[] = new Array<DeleteRequest>();
  newUserRequests: NewUserRequest[] = new Array<NewUserRequest>();
  allUsers: UserInfo[] = new Array<UserInfo>();
  approvedFeedbacks: FeedbackInfo[] = new Array<FeedbackInfo>();
  notApprovedFeedbacks: FeedbackInfo[] = new Array<FeedbackInfo>();

  constructor(public dialog: MatDialog, private _myUserService: MyUserService, private _reportService: ReportService, private _feedbackService: FeedbackService, private _authentificationService: AuthentificationService) { }

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

  loadData() { // ucitavanje iz baze
    this._authentificationService.getUserByEmail().subscribe(
      (user: MyUser) => {
        this.admin = user;
        if(this.admin.isFirstLogin){
          const dialogRef = this.dialog.open(NewAdminPasswordDialogComponent, {
            width: '550px',
            height: '250px',
            data: {},
          });
          dialogRef.componentInstance.admin = this.admin;
          dialogRef.afterClosed().subscribe(result => {
            this.loadData();
          });
        }
      }
    )

    this._myUserService.getAllUsers().subscribe(
      (users: UserInfo[]) => {
        this.allUsers = users;
      }
    )

    this._myUserService.getAllNewUserRequests().subscribe(
      (newUsers: NewUserRequest[]) => {
        this.newUserRequests = newUsers;
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

    this._myUserService.getAllAppeals().subscribe(
      (allAppeals: Appeal[]) => {
        for(let appeal of allAppeals){
          if(appeal.isAnswered){
            this.answeredAppeals.push(appeal);
          }else{
            this.unansweredAppeals.push(appeal);
          }
        }
      }
    )

    this._feedbackService.getAll().subscribe(
      (allFeedbacks: FeedbackInfo[]) => {
        for(let feedback of allFeedbacks){
          if(feedback.isApproved){
            this.approvedFeedbacks.push(feedback);
          }else{
            this.notApprovedFeedbacks.push(feedback);
          }
        }
      }
    )

    this._reportService.getAllReports().subscribe(
      (allReports: ReportInfo[]) => {
        for(let report of allReports){
          if(report.isReviewed){
            this.reviewedReports.push(report);
          }else{
            this.notReviewedReports.push(report);
          }
        }
      }
    )
  }

  makeReservation(){}

  deleteUser(id: number){
    this._myUserService.deleteUser(id).subscribe(
      (ok: Boolean) => {
        this.loadData();
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

  answerOnAppeal(ownerId: number, guestId: number, appealId: number){
    const dialogRef = this.dialog.open(AppealAnswerDialogComponent, {
      width: '550px',
      height: '550px',
      data: {},
    });
    dialogRef.componentInstance.answer.ownerId = ownerId;
    dialogRef.componentInstance.answer.guestId = guestId;
    dialogRef.componentInstance.appealId = appealId;
    dialogRef.afterClosed().subscribe(result => {
      this.loadData();
    });
  }

  answerOnReport(ownerId: number, guestId: number, id: number){
    const dialogRef = this.dialog.open(ReportAnswerDialogComponent, {
      width: '550px',
      height: '550px',
      data: {},
    });
    dialogRef.componentInstance.answer.ownerId = ownerId;
    dialogRef.componentInstance.answer.guestId = guestId;
    dialogRef.componentInstance.reportId = id;
    dialogRef.afterClosed().subscribe(result => {
      this.loadData();
    });
  }

  deleteFeedback(id: number){
    this._feedbackService.delete(id).subscribe(
      (ok: Boolean) => {
        this.loadData();
      },
      (error) => {
        // console.log(error)
      }
    )
  }

  approveFeedback(feedback: FeedbackInfo){
    this._feedbackService.approveFeedback(feedback).subscribe(
      (ok: Boolean) => {
        this.loadData();
      },
      (error) => {
        // console.log(error)
      }
    )
  }

  activateNewUser(id: number){
    this._myUserService.activateNewUser(id).subscribe(
      (ok: Boolean) => {
        this.loadData();
      },
      (error) => {
        // console.log(error)
      }
    )
  }

  declineNewUser(id: number){
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

  addAdmin(){
    const dialogRef = this.dialog.open(AdminRegistrationDialogComponent, {
      width: '700px',
      height: '570px',
      data: {},
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadData();
    });
  }

  addAdventure(){}

  openProfileDialog() {
    const dialogRef = this.dialog.open(ClientProfileComponent, {
      width: '600px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  companyRules(){
    const dialogRef = this.dialog.open(EditCompanyRulesDialogComponent, {
      width: '1000px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  calculateCompanyProfits(){
    const dialogRef = this.dialog.open(CompanyProfitDialogComponent, {
      width: '650px',
      data: {},
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }
}
