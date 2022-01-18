import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { EditAdventureProfileDialogComponent } from '../adventure-profile/edit-adventure-profile-dialog/edit-adventure-profile-dialog.component';
import { AuthentificationService } from '../auth/authentification/authentification.service';
import { ClientProfileComponent } from '../clientHome/dialog/client-profile/client-profile.component';
import { DeleteAccountComponent } from '../clientHome/dialog/delete-account/delete-account.component';
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
import { EditPersonalDescriptionDialogComponent } from './edit-personal-description-dialog/edit-personal-description-dialog.component';
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
  allActions: AdventureReservation[] = new Array<AdventureReservation>();
  allAvaibilityPeriods: AdventureReservation[] = new Array<AdventureReservation>();
  instructor: FishingAdventureInstructorDTO = new FishingAdventureInstructorDTO();
  filterTerm!: string;

  constructor(public dialog: MatDialog, private _adventureService: AdventureProfileService, private _router: Router, private _adventureReservationService: AdventureReservationService,
          private _authentificationService: AuthentificationService) {

   }

  ngOnInit() {
    this._authentificationService.getUserByEmail().subscribe(   // subscribe - da bismo dobili odgovor beka
      (user: MyUser) => {
        this.instructor.id = user.id;
        this.instructor.firstName = user.firstName;
        this.instructor.lastName = user.lastName;
        this.instructor.addressDTO = user.addressDTO;
        this.instructor.username = user.username;
        this.instructor.phone = user.phoneNumber;
        this.instructor.email = user.email;
        this.instructor.personalDescription = user.personalDescription;
        this.loadData();
      },
      (error) => {
      },
    )
    
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
    dialogRef.componentInstance.allActions = this.allActions;
    dialogRef.componentInstance.allAvaibilityPeriods = this.allAvaibilityPeriods;
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

  getCurrentGuest(): number{
    var currentDateAndTime = Number(new Date());
    for(let reservation of this.allReservations){
      if(Number(reservation.startDate) < currentDateAndTime && Number(reservation.endDate) > currentDateAndTime){
        return reservation.guestId;
      }
    }
    return 3;
  }

  loadData() { // ucitavanje iz baze
    this._adventureService.getFishingAdventuresByInstructor(this.instructor.id).subscribe(
      (adventures: FishingAdventure[]) => {
        this.adventures = adventures
      }
    )

    this._adventureReservationService.getAdventureReservationsByInstructorId(this.instructor.id).subscribe(
      (allReservations: AdventureReservation[]) => {
        this.allReservations = allReservations;
      }
    )

    this._adventureService.getAllActionsByInstructorId(this.instructor.id).subscribe(
      (allActions: AdventureReservation[]) => {
        this.allActions = allActions;
      }
    )

    this._adventureReservationService.getAllAvaibilityPeriodsByInstructorId(this.instructor.id).subscribe(
      (allAvaibilityPeriods: AdventureReservation[]) => {
        this.allAvaibilityPeriods = allAvaibilityPeriods;
      }
    )
  }

  openProfileDialog() {
    const dialogRef = this.dialog.open(ClientProfileComponent, {
      width: '600px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  editPersonalDescription() {
    const dialogRef = this.dialog.open(EditPersonalDescriptionDialogComponent, {
      width: '600px',
      height: '450px',
      data: {},
    });
    dialogRef.componentInstance.instructorId = this.instructor.id;
    dialogRef.componentInstance.personalDescription = this.instructor.personalDescription;
    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  deleteProfileDialog() {
    const dialogRef = this.dialog.open(DeleteAccountComponent,{
      width: '400px',
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  makeReservation(adventure: FishingAdventure) {
    const dialogRef = this.dialog.open(MakeReservationDialogComponent,{
      width: '600px',
      data: {},
    });
    dialogRef.componentInstance.adventureReservation.guestId = this.getCurrentGuest();
    dialogRef.componentInstance.adventure = adventure;
    dialogRef.componentInstance.instructorId = this.instructor.id;
    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  // instructorChart(id: number) {
  //   this._router.navigate(['/instructor-chart', id])
  // }
}
