import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AdditionalService } from '../model/additional-service';
import { Address } from '../model/address';
import { FishingAdventure } from '../model/fishing-adventure';
import { FishingAdventureInstructorDTO } from '../model/fishing-adventure-instructorDTO';
import { MyUser } from '../model/my-user';
import { AddAdventureDialogComponent } from './add-adventure-dialog/add-adventure-dialog.component';
import { CalendarDialogComponent } from './calendar-dialog/calendar-dialog.component';
import { DefineAvaibilityPeriodComponent } from './define-avaibility-period/define-avaibility-period.component';
import { MakeReservationDialogComponent } from './make-reservation-dialog/make-reservation-dialog.component';

@Component({
  selector: 'app-fishing-instructor-profile',
  templateUrl: './fishing-instructor-profile.component.html',
  styleUrls: ['./fishing-instructor-profile.component.css']
})
export class FishingInstructorProfileComponent implements OnInit {

  address: Address = new Address(0,"Kotor","Kotor","Crna Gora",0,0,31100);
  service1: AdditionalService= new AdditionalService(0,"STAPOVI", 2000,false);
  service2: AdditionalService= new AdditionalService(0,"STAPOVI", 3000,false);
  additionalServices = new Array<AdditionalService>();
  instructor: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO(1,"Kapetan","Kuka","","",this.address, "065454545", "Najjaci sam na svetu");
  adventure: FishingAdventure = new FishingAdventure(2,"Avanturica", this.address, "Mnogo dobra",5,"SVA","Be good",30,this.additionalServices,false,10,this.instructor);
  filterTerm!: string;

  adventures = [this.adventure, this.adventure, this.adventure, this.adventure]

  constructor(public dialog: MatDialog) {
    this.additionalServices.push(this.service1);
    this.additionalServices.push(this.service2);
   }

  ngOnInit() {
  }

  addAdventure(){
    const dialogRef = this.dialog.open(AddAdventureDialogComponent, {
      width: '600px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  showCalendarDialog(){
    const dialogRef = this.dialog.open(CalendarDialogComponent, {
      width: '1500px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  defineAvaibilityDialog(){
    const dialogRef = this.dialog.open(DefineAvaibilityPeriodComponent, {
      width: '650px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  makeReservation(a: FishingAdventure){
    const dialogRef = this.dialog.open(MakeReservationDialogComponent, {
      width: '320px',
      data: {},
    });
    dialogRef.componentInstance.adventureId = a.id;
    dialogRef.componentInstance.additionalServices = a.services;
    dialogRef.afterClosed().subscribe(result => {
      
    });
  }
}
