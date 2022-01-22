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
import {CreateReservationHouseComponent} from "../dialog/create-reservation-house/create-reservation-house.component";
import {
  CreateReservationInstructorComponent
} from "../dialog/create-reservation-instructor/create-reservation-instructor.component";
import {Subscription} from "../../model/subscription";

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
  isSubscribed: Boolean = true;
  subscription: Subscription = new Subscription(0,this.user,this.user)
  owner: MyUser = new MyUser(0,"","","","","","",this.address, "", "");

  constructor(public dialog: MatDialog, private _adventureService: AdventureProfileService, private _router: Router, private _adventureReservationService: AdventureReservationService,
              private _authentificationService: AuthentificationService, private alertService: AlertService, private _feedbackService: FeedbackService, private  _myUserService: MyUserService,
              private _route: ActivatedRoute, private _alertService: AlertService, private _userService: MyUserService) {

  }

  ngOnInit() {
    // @ts-ignore
    this.id =  +this._route.snapshot.paramMap.get('id');
    this._authentificationService.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.user = user;
        this._myUserService.getUserById(this.id).subscribe(   // subscribe - da bismo dobili odgovor beka
          (user: MyUser) => {
            this.owner = user;
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
    this._router.navigate(['client/instructor/adventure/'+id]);
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






  makeReservation(adventure: FishingAdventure) {

    const dialogRef = this.dialog.open(CreateReservationInstructorComponent,{
      width: '600px',
      data: {
        adventureId: adventure.id,
        instructorId: this.id
      },
    });

    dialogRef.afterClosed().subscribe(result => {

    });

  }

  // instructorChart(id: number) {
  //   this._router.navigate(['/instructor-chart', id])
  // }
  subsrcibe() {
    this._myUserService.checkIfUserIsSubscribes(this.user.id, this.instructor.id).subscribe(
      (isSubscribed: Boolean) => {
        this.isSubscribed = isSubscribed
        if(this.isSubscribed){
          this._alertService.info("Vec ste preplaceni");
        }else{
          if(confirm("Da li sigurne zelite da se pretplatite")) {

            this.subscription.owner = this.owner;
            this.subscription.subscribedUser = this.user;
            this._myUserService.saveSubscription(this.subscription).subscribe(
              (sub: Subscription) => {
                this.subscription = sub;
                this._alertService.success("Uspjesno ste preplaceni");
              }
            )
          }
        }
      }
    )
  }
}
