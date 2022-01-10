import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AdditionalService } from '../model/additional-service';
import { Address } from '../model/address';
import { FishingAdventure } from '../model/fishing-adventure';
import { FishingAdventureInstructorDTO } from '../model/fishing-adventure-instructorDTO';
import { MyUser } from '../model/my-user';
import { AdventureProfileService } from '../service/adventure-profile.service';
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
  instructor: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO(1,"Kapetan","Kuka","","",this.address, "065454545", "Najjaci sam na svetu");
  filterTerm!: string;

  constructor(public dialog: MatDialog, private _adventureService: AdventureProfileService, private _router: Router) {

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
      width: '320px',
      data: {},
    });
    dialogRef.componentInstance.adventure = a;
    dialogRef.componentInstance.instructorId = this.instructor.id;
    dialogRef.afterClosed().subscribe(result => {
      
    });
  }

  loadData() { // ucitavanje iz baze
    this._adventureService.getFishingAdventuresByInstructor(1).subscribe(
      (adventures: FishingAdventure[]) => {
        this.adventures = adventures
      }
    )
  }
}
