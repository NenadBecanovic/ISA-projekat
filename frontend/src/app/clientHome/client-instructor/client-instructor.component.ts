import { Component, OnInit } from '@angular/core';
import {Address} from "../../model/address";
import {AdditionalService} from "../../model/additional-service";
import {FishingAdventure} from "../../model/fishing-adventure";
import {FeedbackInfo} from "../../model/feedback-info";
import {AdventureReservation} from "../../model/adventure-reservation";
import {FishingAdventureInstructorDTO} from "../../model/fishing-adventure-instructorDTO";
import {MatDialog} from "@angular/material/dialog";
import {AdventureProfileService} from "../../service/adventure-profile.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AdventureReservationService} from "../../service/adventure-reservation.service";
import {AuthentificationService} from "../../auth/authentification/authentification.service";
import {AlertService} from "ngx-alerts";
import {FeedbackService} from "../../service/feedback.service";
import {MyUser} from "../../model/my-user";
import {
  AddAdventureDialogComponent
} from "../../fishing-instructor-profile/add-adventure-dialog/add-adventure-dialog.component";
import {CalendarDialogComponent} from "../../fishing-instructor-profile/calendar-dialog/calendar-dialog.component";
import {
  DefineAvaibilityPeriodComponent
} from "../../fishing-instructor-profile/define-avaibility-period/define-avaibility-period.component";
import {ClientProfileComponent} from "../dialog/client-profile/client-profile.component";
import {
  EditPersonalDescriptionDialogComponent
} from "../../fishing-instructor-profile/edit-personal-description-dialog/edit-personal-description-dialog.component";
import {DeleteAccountComponent} from "../dialog/delete-account/delete-account.component";
import {
  MakeReservationDialogComponent
} from "../../fishing-instructor-profile/make-reservation-dialog/make-reservation-dialog.component";
import {MyUserService} from "../../service/my-user.service";

@Component({
  selector: 'app-client-instructor',
  templateUrl: './client-instructor.component.html',
  styleUrls: ['./client-instructor.component.css']
})
export class ClientInstructorComponent implements OnInit {
  address: Address = new Address(0,"Kotor","Kotor","Crna Gora",0,0,31100);
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  adventures: FishingAdventure[] = new Array<FishingAdventure>();
  allFeedbacks: FeedbackInfo[] = new Array<FeedbackInfo>();
  a: FishingAdventure = new FishingAdventure(0,"F",this.address,"",0,",","",0,true,0);
  allReservations: AdventureReservation[] = new Array<AdventureReservation>();
  allActions: AdventureReservation[] = new Array<AdventureReservation>();
  allAvaibilityPeriods: AdventureReservation[] = new Array<AdventureReservation>();
  instructor: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO();
  user: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  filterTerm!: string;
  id: number = 0;

  constructor(public dialog: MatDialog, private _adventureService: AdventureProfileService, private _router: Router, private _adventureReservationService: AdventureReservationService,
              private _authentificationService: AuthentificationService, private alertService: AlertService, private _feedbackService: FeedbackService, private  _myUserService: MyUserService,
              private _route: ActivatedRoute) {

  }

  ngOnInit() {
    // @ts-ignore
    this.id =  +this._route.snapshot.paramMap.get('id');
    this._authentificationService.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        this._myUserService.getUserById(this.id).subscribe(   // subscribe - da bismo dobili odgovor beka
          (user: MyUser) => {
            this.instructor.id = user.id;
            this.instructor.firstName = user.firstName;
            this.instructor.lastName = user.lastName;
            this.instructor.addressDTO = user.addressDTO;
            this.instructor.username = user.username;
            this.instructor.phone = user.phoneNumber;
            this.instructor.email = user.email;
            this.instructor.personalDescription = user.personalDescription;
            this.loadData();
          },
          (error) => {
          },
        )


      },
      (error) => {
      },
    )

  }

  goToAdventure(id: number){
    this._router.navigate(['/adventure-profile/'+id]);
  }

  addAdventure(){
    const dialogRef = this.dialog.open(AddAdventureDialogComponent, {
      width: '800px',
      data: {},
      backdropClass: 'dialog-background'
    });
    dialogRef.componentInstance.newFishingAdventure.instructorId = this.instructor.id;
    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  showCalendarDialog(){
    const dialogRef = this.dialog.open(CalendarDialogComponent, {
      width: '1500px',
      data: {},
    });
    dialogRef.componentInstance.allReservations = this.allReservations;
    dialogRef.componentInstance.allActions = this.allActions;
    dialogRef.componentInstance.allAvaibilityPeriods = this.allAvaibilityPeriods;
    dialogRef.afterClosed().subscribe(result => {

    });
  }

  defineAvaibilityDialog(){
    const dialogRef = this.dialog.open(DefineAvaibilityPeriodComponent, {
      width: '650px',
      data: {},
    });
    dialogRef.componentInstance.instructorId = this.instructor.id;
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  getCurrentGuest(): number{
    var currentDateAndTime = Number(new Date());
    for(let reservation of this.allReservations){
      if(Number(reservation.startDate) < currentDateAndTime && Number(reservation.endDate) > currentDateAndTime){
        return reservation.guestId;
      }
    }
    return 3;
  }

  loadData() { // ucitavanje iz baze
    this._adventureService.getFishingAdventuresByInstructor(this.instructor.id).subscribe(
      (adventures: FishingAdventure[]) => {
        this.adventures = adventures
      }
    )

    this._adventureReservationService.getAdventureReservationsByInstructorId(this.instructor.id).subscribe(
      (allReservations: AdventureReservation[]) => {
        this.allReservations = allReservations;
      }
    )

    this._adventureService.getAllActionsByInstructorId(this.instructor.id).subscribe(
      (allActions: AdventureReservation[]) => {
        this.allActions = allActions;
      }
    )

    this._adventureReservationService.getAllAvaibilityPeriodsByInstructorId(this.instructor.id).subscribe(
      (allAvaibilityPeriods: AdventureReservation[]) => {
        this.allAvaibilityPeriods = allAvaibilityPeriods;
      }
    )

    this._feedbackService.getAllFeedbacksByAdventureId(this.instructor.id).subscribe(
      (feedbacks: FeedbackInfo[]) => {
        this.allFeedbacks = feedbacks;
      }
    )
  }

  openProfileDialog() {
    const dialogRef = this.dialog.open(ClientProfileComponent, {
      width: '600px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  editPersonalDescription() {
    const dialogRef = this.dialog.open(EditPersonalDescriptionDialogComponent, {
      width: '600px',
      height: '450px',
      data: {},
    });
    dialogRef.componentInstance.instructorId = this.instructor.id;
    dialogRef.componentInstance.personalDescription = this.instructor.personalDescription;
    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  deleteProfileDialog() {
    const dialogRef = this.dialog.open(DeleteAccountComponent,{
      width: '400px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  makeReservation(adventure: FishingAdventure) {
    var currentGuest = this.getCurrentGuest();
    if(currentGuest != 0 && currentGuest != null){
      const dialogRef = this.dialog.open(MakeReservationDialogComponent,{
        width: '600px',
        data: {},
      });
      dialogRef.componentInstance.adventureReservation.guestId = currentGuest;
      dialogRef.componentInstance.adventure = adventure;
      dialogRef.componentInstance.instructorId = this.instructor.id;
      dialogRef.componentInstance.loadData();
      dialogRef.afterClosed().subscribe(result => {
        window.location.reload();
      });
    }
    this.alertService.danger('Nema trenutnog gosta za novu rezervaciju!');

  }

  // instructorChart(id: number) {
  //   this._router.navigate(['/instructor-chart', id])
  // }
}
