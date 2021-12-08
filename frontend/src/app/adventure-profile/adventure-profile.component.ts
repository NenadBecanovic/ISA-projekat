import { Component, OnInit } from '@angular/core';
import { AddActionDialogComponent } from '../add-action-dialog/add-action-dialog/add-action-dialog.component';
import { AdditionalService } from '../model/additional-service';
import { Address } from '../model/address';
import { FishingAdventure } from '../model/fishing-adventure';
import { MyUser } from '../model/my-user';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

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

  address: Address = new Address("Kotor","Kotor","Crna Gora",0,0)
  user: MyUser = new MyUser("Kapetan","Kuka","","","kuka","",this.address);
  service1: AdditionalService= new AdditionalService("STAPOVI", 2000);
  service2: AdditionalService= new AdditionalService("STAPOVI", 3000);
  additionalServices: Array<AdditionalService>;
  fishingAdventure: FishingAdventure;

  constructor(public dialog: MatDialog) {
    this.additionalServices = new Array<AdditionalService>();
    this.additionalServices.push(this.service1);
    this.additionalServices.push(this.service2);
    this.fishingAdventure = new FishingAdventure("Ludilo avantura", this.address, "Neverovatna avantura kapetana kuke!", 5, "SVA OPREMA", "Kapetan mora da se slusa", 15000, this.additionalServices,false, 20, this.user);
   }

  ngOnInit() {
  }

  showMap(){
    alert("MAPA");
  }

  addActionDialog(){
    alert("NOVA AKCIJA");
    const dialogRef = this.dialog.open(AddActionDialogComponent, {
      width: '250px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      alert("RADI");
    });
  }
}
