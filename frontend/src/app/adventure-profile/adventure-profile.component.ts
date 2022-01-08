import { Component, OnInit, ViewChild } from '@angular/core';
import { AdditionalService } from '../model/additional-service';
import { Address } from '../model/address';
import { FishingAdventure } from '../model/fishing-adventure';
import { MyUser } from '../model/my-user';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AdventureProfileService } from '../service/adventure-profile.service';
import { AdventureReservationsDialogComponent } from './adventure-reservations-dialog/adventure-reservations-dialog.component';
import { FishingAdventureInstructorDTO } from '../model/fishing-adventure-instructorDTO';
import { Router } from '@angular/router';
import {ImageService} from "../service/image.service";
import {Image} from "../model/image";
import { AdditionalServicesService } from '../service/additional-services.service';
import { AddImageDialogComponent } from './add-image-dialog/add-image-dialog.component';

class ImageSnippet {
  constructor(public src: string, public file: File) {}
}
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
  selectedFile!: ImageSnippet;
  images: Image[] = new Array<Image>();
  isLoaded: boolean = false;

  constructor(public dialog: MatDialog, private _adventureService: AdventureProfileService, private _additionalServices: AdditionalServicesService, private _imageService: ImageService, private _router: Router) {
   }

  ngOnInit() {
    this.loadData();
  }

  showMap(){
    alert("MAPA");
  }

  addActionDialog(){
    alert("AKCIJA");
  }

  showReservationsDialog(){
    const dialogRef = this.dialog.open(AdventureReservationsDialogComponent, {
      width: '500px',
      data: {},
    });

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
    this._adventureService.getFishingAdventureById(1).subscribe(
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
      }
    )
  }
}
