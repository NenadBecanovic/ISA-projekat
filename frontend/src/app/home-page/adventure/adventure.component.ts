import { Component, OnInit } from '@angular/core';
import {Address} from "../../model/address";
import {FishingAdventureInstructorDTO} from "../../model/fishing-adventure-instructorDTO";
import {AdditionalService} from "../../model/additional-service";
import {FishingAdventure} from "../../model/fishing-adventure";
import {Image} from "../../model/image";
import {AdventureReservation} from "../../model/adventure-reservation";
import {MatDialog} from "@angular/material/dialog";
import {ActivatedRoute, Router} from "@angular/router";
import {AdventureProfileService} from "../../service/adventure-profile.service";
import {AdditionalServicesService} from "../../service/additional-services.service";
import {ImageService} from "../../service/image.service";
import {AdventureReservationService} from "../../service/adventure-reservation.service";
import {MyUserService} from "../../service/my-user.service";
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

@Component({
  selector: 'app-adventure',
  templateUrl: './adventure.component.html',
  styleUrls: ['./adventure.component.css']
})
export class AdventureComponent implements OnInit {
  address: Address = new Address(0,"Kotor","Kotor","Crna Gora",0,0,31100)
  instructor: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO();
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  fishingAdventure: FishingAdventure= new FishingAdventure(0,"", this.address, "", 0, "", "", 0,true, 0);
  images: Image[] = new Array<Image>();
  actions: AdventureReservation[] = new Array<AdventureReservation>();
  isLoaded: boolean = false;
  adventureId: number = 0;
  savings: number[] = new Array();
  currentImageId: number = 0;

  constructor(public dialog: MatDialog, private _route: ActivatedRoute, private _adventureService: AdventureProfileService, private _additionalServices: AdditionalServicesService, private _imageService: ImageService, private _router: Router,
              private _adventureReservationService: AdventureReservationService, private _myUserService: MyUserService) {
  }

  ngOnInit() {
    // @ts-ignore
    this.adventureId = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  showMap(){
    alert("MAPA");
  }


  loadData() { // ucitavanje iz baze
    this._adventureService.getFishingAdventureById(this.adventureId).subscribe(
      (fishingAdventure: FishingAdventure) => {
        this.fishingAdventure = fishingAdventure
        this.address = this.fishingAdventure.address;

        this._myUserService.getFishingAdventureInstructor(this.fishingAdventure.instructorId).subscribe(
          (instructor: FishingAdventureInstructorDTO) => {
            this.instructor = instructor
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
      }
    )
  }



  goToInstructor(){
    this._router.navigate(['/fishing-instructor/'+this.instructor.id]);
  }



}
