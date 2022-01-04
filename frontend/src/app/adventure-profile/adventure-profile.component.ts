import { Component, OnInit, ViewChild } from '@angular/core';
import { AdditionalService } from '../model/additional-service';
import { Address } from '../model/address';
import { FishingAdventure } from '../model/fishing-adventure';
import { MyUser } from '../model/my-user';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AdventureProfileService } from '../service/adventure-profile.service';
import { AdventureReservationsDialogComponent } from '../fishing-instructor-profile/adventure-reservations-dialog/adventure-reservations-dialog.component';
import { FishingAdventureInstructorDTO } from '../model/fishing-adventure-instructorDTO';

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
  user: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO(1,"Kapetan","Kuka","","",this.address, "065454545", "Najjaci sam na svetu");
  service1: AdditionalService= new AdditionalService(0,"STAPOVI", 2000,false);
  service2: AdditionalService= new AdditionalService(0,"STAPOVI", 3000,false);
  additionalServices: Array<AdditionalService>;
  fishingAdventure: FishingAdventure;
  selectedFile!: ImageSnippet;

  constructor(public dialog: MatDialog, private adventureService: AdventureProfileService) {
    this.additionalServices = new Array<AdditionalService>();
    this.additionalServices.push(this.service1);
    this.additionalServices.push(this.service2);
    this.fishingAdventure = new FishingAdventure(0,"Ludilo avantura", this.address, "Neverovatna avantura kapetana kuke!", 5, "SVA OPREMA", "Kapetan mora da se slusa", 4000, this.additionalServices,false, 20, this.user);
   }

  ngOnInit() {
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

      this.adventureService.uploadImage(this.selectedFile.file).subscribe(
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
}
