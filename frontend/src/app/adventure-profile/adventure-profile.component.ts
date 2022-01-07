import { Component, OnInit, ViewChild } from '@angular/core';
import { AdditionalService } from '../model/additional-service';
import { Address } from '../model/address';
import { FishingAdventure } from '../model/fishing-adventure';
import { MyUser } from '../model/my-user';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AdventureProfileService } from '../service/adventure-profile.service';
import { AdventureReservationsDialogComponent } from '../fishing-instructor-profile/adventure-reservations-dialog/adventure-reservations-dialog.component';
import { FishingAdventureInstructorDTO } from '../model/fishing-adventure-instructorDTO';
import { Router } from '@angular/router';
import {ImageService} from "../service/image.service";
import {Image} from "../model/image";
import { AdditionalServicesService } from '../service/additional-services.service';

class ImageSnippet {
  constructor(public src: string, public file: File) {}
}
@Component({
  selector: 'app-adventure-profile',
  templateUrl: './adventure-profile.component.html',
  styleUrls: ['./adventure-profile.component.css']
})
export class AdventureProfileComponent implements OnInit {

  slides = [
    {
      image: /* "../images/slide1.jpg" */
        "https://mackenzienz.com/wp-content/uploads/2015/09/Fishing-PAge-BAnner.jpg?fbclid=IwAR1c5dayMLBGnI0cFCy6Z8zFfBjmkX17Z9krEkjT5v2vDbf5xKxvVNgFSmk"
    },
    {
      image:
        "https://www.offshorewest.com/wp-content/uploads/2017/01/Homepage-Banner-fishing.jpg?fbclid=IwAR3QvNqAVnKMCsDJE0Hk_F50RZCOkcj9pwQy9Mfn_HvWftbAf21PmbvmRo0"
    }
  ];

  address: Address = new Address(0,"Kotor","Kotor","Crna Gora",0,0,31100)

  // user: MyUser = new MyUser(0,"Kapetan","Kuka","","","kuka","",this.address, "065454545", "Zelim");
  service1: AdditionalService= new AdditionalService(0,"STAPOVI", 2000, false);
  service2: AdditionalService= new AdditionalService(0,"STAPOVI", 3000, false);
  fishingAdventure: FishingAdventure;
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

  imageAdded(e: any){
     /* const file = e.target.files[0];
      this.createBase64Image(file);
      this.newImage=URL.createObjectURL(file);*/
      const file: File = e.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

      this.selectedFile = new ImageSnippet(event.target.result, file);

      this._adventureService.uploadImage(this.selectedFile.file).subscribe(
        (res) => {
          alert("OK");
        },
        (err) => {

        })
    });

    reader.readAsDataURL(file);
  }

  createBase64Image(file: Blob){
    /*  const reader= new FileReader();
      reader.onload = (e) =>{
        let img = e.target.result;
        img.replace("data:image\/(png|jpg|jpeg);base64", "");
      //  console.log(img);
        this.form.backendImage = img;
      }
      reader.readAsDataURL(file);*/
  }

  loadData() { // ucitavanje iz baze
    this._adventureService.getFishingAdventureById(1).subscribe(
      (fishingAdventure: FishingAdventure) => {
        this.fishingAdventure = fishingAdventure
        this.address = this.fishingAdventure.address;

        this._additionalServices.getAllByBoatId(this.fishingAdventure.id).subscribe(
          (additionalServices: AdditionalService[]) => {
            this.additionalServices = additionalServices
          }
        )

        this._imageService.getAllByBoatId(this.fishingAdventure.id).subscribe(
          (images: Image[]) => {
            this.images = images
            this.isLoaded = true;
          }
        )
      }
    )
  }
}
