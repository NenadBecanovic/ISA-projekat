import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Report } from '../model/report';
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
  allReports: Report[] = new Array<Report>();

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

  getCurrentGuest(): number{
   /* var currentDateAndTime = Number(new Date());
    for(let reservation of this.allReservations){
      if(Number(reservation.startDate) < currentDateAndTime && Number(reservation.endDate) > currentDateAndTime){
        return reservation.guestId;
      }
    }*/
    return 0;
  }

  loadData() { // ucitavanje iz baze
  /*  this._adventureService.getFishingAdventuresByInstructor(1).subscribe(
      (adventures: FishingAdventure[]) => {
        this.adventures = adventures
      }
    )

    this._adventureReservationService.getAdventureReservationsByInstructorId(1).subscribe(
      (allReservations: AdventureReservation[]) => {
        this.allReservations = allReservations;
      }
    )*/
  }
}
