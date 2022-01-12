import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AdditionalService } from '../model/additional-service';
import { Address } from '../model/address';
import { AdventureReservation } from '../model/adventure-reservation';
import { FishingAdventure } from '../model/fishing-adventure';
import { FishingAdventureInstructorDTO } from '../model/fishing-adventure-instructorDTO';
import { MyUser } from '../model/my-user';
import { AdventureProfileService } from '../service/adventure-profile.service';
import { AdventureReservationService } from '../service/adventure-reservation.service';
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
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  adventures: FishingAdventure[] = new Array<FishingAdventure>();
  a: FishingAdventure = new FishingAdventure(0,"F",this.address,"",0,",","",0,true,0);
  allReservations: AdventureReservation[] = new Array<AdventureReservation>();
  instructor: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO(1,"Kapetan","Kuka","","",this.address, "065454545", "Najjaci sam na svetu");
  filterTerm!: string;

  constructor(public dialog: MatDialog, private _adventureService: AdventureProfileService, private _router: Router, private _adventureReservationService: AdventureReservationService) {

   }

  ngOnInit() {
    this.loadData();
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

  makeReservation(a: FishingAdventure){
    const dialogRef = this.dialog.open(MakeReservationDialogComponent, {
      width: '500px',
      height: '570px',
      data: {},
    });
    dialogRef.componentInstance.adventure = a;
    dialogRef.componentInstance.instructorId = this.instructor.id;
    dialogRef.componentInstance.adventureReservation.guestId = this.getCurrentGuest();
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
    return 0;
  }

  loadData() { // ucitavanje iz baze
    this._adventureService.getFishingAdventuresByInstructor(1).subscribe(
      (adventures: FishingAdventure[]) => {
        this.adventures = adventures
      }
    )

    this._adventureReservationService.getAdventureReservationsByInstructorId(1).subscribe(
      (allReservations: AdventureReservation[]) => {
        this.allReservations = allReservations;
      }
    )
  }
}
