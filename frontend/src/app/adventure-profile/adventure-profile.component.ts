import { Component, OnInit, ViewChild } from '@angular/core';
import { AdditionalService } from '../model/additional-service';
import { Address } from '../model/address';
import { FishingAdventure } from '../model/fishing-adventure';
import { MyUser } from '../model/my-user';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AdventureProfileService } from '../service/adventure-profile.service';
import { AdventureReservationsDialogComponent } from './adventure-reservations-dialog/adventure-reservations-dialog.component';
import { FishingAdventureInstructorDTO } from '../model/fishing-adventure-instructorDTO';
import { ActivatedRoute, Router } from '@angular/router';
import {ImageService} from "../service/image.service";
import {Image} from "../model/image";
import { AdditionalServicesService } from '../service/additional-services.service';
import { AddImageDialogComponent } from './add-image-dialog/add-image-dialog.component';
import { AdventureReservationService } from '../service/adventure-reservation.service';
import { AdventureReservation } from '../model/adventure-reservation';
import { AddFishingAdventureActionDialogComponent } from './add-action-dialog/add-action-dialog.component';
import { EditAdventureProfileDialogComponent } from './edit-adventure-profile-dialog/edit-adventure-profile-dialog.component';

@Component({
  selector: 'app-adventure-profile',
  templateUrl: './adventure-profile.component.html',
  styleUrls: ['./adventure-profile.component.css']
})
export class AdventureProfileComponent implements OnInit {

  address: Address = new Address(0,"Kotor","Kotor","Crna Gora",0,0,31100)
  user: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO(1,"Kapetan","Kuka","","",this.address, "065454545", "Najjaci sam na svetu");
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  fishingAdventure: FishingAdventure= new FishingAdventure(0,"", this.address, "", 0, "", "", 0,true, 0);
  images: Image[] = new Array<Image>();
  actions: AdventureReservation[] = new Array<AdventureReservation>();
  isLoaded: boolean = false;
  adventureId: number = 0;
  savings: number[] = new Array();

  constructor(public dialog: MatDialog, private _route: ActivatedRoute, private _adventureService: AdventureProfileService, private _additionalServices: AdditionalServicesService, private _imageService: ImageService, private _router: Router,
    private _adventureReservationService: AdventureReservationService) {
   }

  ngOnInit() {
    // @ts-ignore
    this.adventureId = +this._route.snapshot.paramMap.get('id');
    this.loadData();
  }

  showMap(){
    alert("MAPA");
  }

  addActionDialog(){
    const dialogRef = this.dialog.open(AddFishingAdventureActionDialogComponent, {
      width: '500px',
      height: '570px',
      data: {},
    });
    dialogRef.componentInstance.adventure = this.fishingAdventure;
    dialogRef.componentInstance.additionalServices = this.additionalServices;
    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  editAdventureDialog(){
    const dialogRef = this.dialog.open(EditAdventureProfileDialogComponent, {
      width: '800px',
      height: '570px',
      data: {},
    });
    dialogRef.componentInstance.adventure = this.fishingAdventure;
    dialogRef.componentInstance.additionalServices = this.additionalServices;
    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  showReservationsDialog(id: number){
    const dialogRef = this.dialog.open(AdventureReservationsDialogComponent, {
      width: '1000px',
      height: '570px',
      data: {},
    });
    dialogRef.componentInstance.adventureId = id;
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
    this._adventureService.getFishingAdventureById(this.adventureId).subscribe(
      (fishingAdventure: FishingAdventure) => {
        this.fishingAdventure = fishingAdventure
        this.address = this.fishingAdventure.address;

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
            this.actions = actions;
            for(let action of actions){
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
        )
      }
    )
  }

  showCharts() {
    this._router.navigate(['/adventure-chart', this.adventureId])
  }
}
