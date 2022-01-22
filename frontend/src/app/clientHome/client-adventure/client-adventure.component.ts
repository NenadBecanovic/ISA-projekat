import { Component, OnInit } from '@angular/core';
import {Address} from "../../model/address";
import {FishingAdventureInstructorDTO} from "../../model/fishing-adventure-instructorDTO";
import {AdditionalService} from "../../model/additional-service";
import {FishingAdventure} from "../../model/fishing-adventure";
import {Image} from "../../model/image";
import {AdventureReservation} from "../../model/adventure-reservation";
import {FeedbackInfo} from "../../model/feedback-info";
import {MatDialog} from "@angular/material/dialog";
import {ActivatedRoute, Router} from "@angular/router";
import {AdventureProfileService} from "../../service/adventure-profile.service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {ImageService} from "../../service/image.service";
import {AdventureReservationService} from "../../service/adventure-reservation.service";
import {MyUserService} from "../../service/my-user.service";
import {FeedbackService} from "../../service/feedback.service";
import {
  AddFishingAdventureActionDialogComponent
} from "../../adventure-profile/add-action-dialog/add-action-dialog.component";
import {
  EditAdventureProfileDialogComponent
} from "../../adventure-profile/edit-adventure-profile-dialog/edit-adventure-profile-dialog.component";
import {
  AdventureReservationsDialogComponent
} from "../../adventure-profile/adventure-reservations-dialog/adventure-reservations-dialog.component";
import {AddImageDialogComponent} from "../../adventure-profile/add-image-dialog/add-image-dialog.component";
import {DeleteImageDialogComponent} from "../../adventure-profile/delete-image-dialog/delete-image-dialog.component";
import {conditionallyCreateMapObjectLiteral} from "@angular/compiler/src/render3/view/util";
import {
  CreateReservationInstructorComponent
} from "../dialog/create-reservation-instructor/create-reservation-instructor.component";
import {ActionDTO} from "../../model/action-dto";
import {ClientReservationService} from "../../service/client-reservation-service";
import {AlertService} from "ngx-alerts";
import {AuthentificationService} from "../../auth/authentification/authentification.service";
import {MyUser} from "../../model/my-user";

@Component({
  selector: 'app-client-adventure',
  templateUrl: './client-adventure.component.html',
  styleUrls: ['./client-adventure.component.css']
})
export class ClientAdventureComponent implements OnInit {
  address: Address = new Address(0,"Kotor","Kotor","Crna Gora",0,0,31100)
  currentuser: MyUser = new MyUser(0,"","","","","","",this.address, "", "");
  instructor: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO();
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  fishingAdventure: FishingAdventure= new FishingAdventure(0,"", this.address, "", 0, "", "", 0,true, 0);
  images: Image[] = new Array<Image>();
  actions: AdventureReservation[] = new Array<AdventureReservation>();
  allFeedbacks: FeedbackInfo[] = new Array<FeedbackInfo>();
  isLoaded: boolean = false;
  adventureId: number = 0;
  savings: number[] = new Array();
  currentImageId: number = 0;
  action: ActionDTO = new ActionDTO();

  constructor(public dialog: MatDialog, private _route: ActivatedRoute, private _adventureService: AdventureProfileService, private _additionalServices: AdditionalServicesService, private _imageService: ImageService, private _router: Router,
              private _adventureReservationService: AdventureReservationService, private _myUserService: MyUserService, private _feedbackService: FeedbackService, private _clientResrvationService: ClientReservationService,
              private _alertService: AlertService, private _authentification: AuthentificationService,) {
  }

  ngOnInit() {
    // @ts-ignore
    this.adventureId = +this._route.snapshot.paramMap.get('id');

    this.loadData();
  }






  showReservationsDialog(id: number){
    const dialogRef = this.dialog.open(AdventureReservationsDialogComponent, {
      width: '1000px',
      height: '590px',
      data: {},
    });
    dialogRef.componentInstance.adventureId = id;
    dialogRef.componentInstance.onLoad();
    dialogRef.afterClosed().subscribe(result => {

    });
  }

  imageAdded(id: number){
    const dialogRef = this.dialog.open(AddImageDialogComponent, {
      width: '500px',
      data: {},
    });
    dialogRef.componentInstance.id = id;
    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  loadData() { // ucitavanje iz baze
    this._authentification.getUserByEmail().subscribe((user: MyUser) =>{
        this.currentuser = user;
    })

    this._adventureService.getFishingAdventureById(this.adventureId).subscribe(
      (fishingAdventure: FishingAdventure) => {
        this.fishingAdventure = fishingAdventure
        this.address = this.fishingAdventure.address;
        console.log(this.fishingAdventure);
        this._adventureService.getFishingAdventureInstructor(this.fishingAdventure.id).subscribe(
          (instructor: FishingAdventureInstructorDTO) => {
            this.instructor = instructor
            console.log(this.instructor)
          }
        )

        this._additionalServices.getAllByFishingAdventureId(this.fishingAdventure.id).subscribe(
          (additionalServices: AdditionalService[]) => {
            this.additionalServices = additionalServices
          }
        )

        this._imageService.getAllByFishingAdventureId(this.fishingAdventure.id).subscribe(
          (images: Image[]) => {
            this.images = images
            this.isLoaded = true;
          }
        )

        this._adventureReservationService.getAllActionsByFishingAdventureId(this.fishingAdventure.id).subscribe(
          (actions: AdventureReservation[]) => {
            for(let action of actions){
              if(action.guestId == null){
                this.actions.push(action);
                let startDate = new Date(Number(action.startDate))
                let startHours = startDate.getHours();
                let startMinutes = startDate.getMinutes();
                let endDate = new Date(Number(action.endDate))
                let endHours = endDate.getHours();
                let endMinutes = endDate.getMinutes();
                var price = ((endHours*60 + endMinutes) - (startHours*60 + startMinutes)) * this.fishingAdventure.pricePerHour / 60;
                for(let service of action.additionalServices){
                  price += service.price;
                }
                var discount = price - action.price;
                this.savings.push(discount);
              }
            }
          }
        )

        this._feedbackService.getAllFeedbacksByAdventureId(this.fishingAdventure.id).subscribe(
          (feedbacks: FeedbackInfo[]) => {
            this.allFeedbacks = feedbacks;
          }
        )
      }
    )
  }

  reservate() {
    const dialogRef = this.dialog.open(CreateReservationInstructorComponent,{
      width: '600px',
      data: {
        adventureId: this.adventureId,
        instructorId: this.instructor.id
      },
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }


  setActionAdventure(id: number, i: number) {
    this.action.entityId = id
    this.action.userId = this.currentuser.id

    if (confirm("Da li ste sigurni da zelite da rezervisete akciju. Usteda je " + this.savings[i].toString() + " dinara" )) {
      this._clientResrvationService.editAdventureAction(this.action).subscribe((bool: boolean)=>{
        if(bool){
          this._alertService.success("Rezervacija je uspjesna, pogledajte mejl");
          this.loadData()
        }else{
          this._alertService.warning("Rezervacija je vec zauzeta");
        }
      }, (error => {
        this._alertService.danger("Doslo je do greske pokusajte opet");
      }))
    }
  }
}
