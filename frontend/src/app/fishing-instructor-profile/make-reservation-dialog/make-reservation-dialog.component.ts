import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { AdventureProfileComponent } from 'src/app/adventure-profile/adventure-profile.component';
import { AdditionalService } from 'src/app/model/additional-service';
import { AdventureReservation } from 'src/app/model/adventure-reservation';
import { FishingAdventure } from 'src/app/model/fishing-adventure';
import { AdditionalServicesService } from 'src/app/service/additional-services.service';
import { AdventureProfileService } from 'src/app/service/adventure-profile.service';
import { AdventureReservationService } from 'src/app/service/adventure-reservation.service';

@Component({
  selector: 'app-make-reservation-dialog',
  templateUrl: './make-reservation-dialog.component.html',
  styleUrls: ['./make-reservation-dialog.component.css']
})
export class MakeReservationDialogComponent implements OnInit {

  adventure!: FishingAdventure;
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  reservationAdditionalServices: AdditionalService[] = new Array();
  adventureReservation: AdventureReservation = new AdventureReservation(0, '', '', 0, this.additionalServices, 0, true);
  durationHours: number = 0;
  durationMinutes: number = 0;
  date: Date = new Date();
  instructorId!: number;
  isDisabled: boolean = false;

  constructor(public dialogRef: MatDialogRef<MakeReservationDialogComponent>, private _adventureService: AdventureProfileService, private _additionalServicesService: AdditionalServicesService,
          private _alertService: AlertService) { 

  }

  ngOnInit() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0 so need to add 1 to make it 1!
    var yyyy = today.getFullYear();
    var d = '';
    var m = '';
    if(dd<10){
      d='0'+dd
    } 
    if(mm<10){
      m='0'+mm
    } 

    var day = yyyy+'-'+m+'-'+d;
    //@ts-ignore
    document.getElementById("datefield").setAttribute("min", day);
    if(this.adventureReservation.guestId == 0){
      alert("Trenutno nema korisnika za kog se moze napraviti rezervacija!")
      this.isDisabled = true;
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  loadData() { // ucitavanje iz baze
    this._additionalServicesService.getAllByFishingAdventureId(this.adventure.id).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )
  }

  makeReservation() {
    if(this.adventureReservation.startDate === '' || this.durationHours == 0 || this.durationMinutes == 0){
      alert('Odaberite datum!')
    }else{
      this.adventureReservation.adventureId = this.adventure.id;
      this.adventureReservation.isAction = false;
      this.adventureReservation.isAvailable = false;
      this.adventureReservation.availabilityPeriod = false;

      var startDate = Date.parse(this.adventureReservation.startDate)
      this.date =  new Date(startDate)
      var actionStart  = Number(this.date) 
      this.date.setHours(this.date.getHours() + this.durationHours);
      this.date.setMinutes(this.date.getMinutes() + this.durationMinutes);
      var actionEnd = Number(this.date)
      this.adventureReservation.startDate = actionStart.toString()
      this.adventureReservation.endDate = actionEnd.toString()
      
      var price = this.adventure.pricePerHour * this.durationHours + (this.adventure.pricePerHour / 60) * this.durationMinutes;
      this.adventureReservation.price = price;
      for (let a of this.additionalServices)
      {
          if (a.checked == true)
          {
            this.reservationAdditionalServices.push(a)
          }
      }

      this.adventureReservation.additionalServices = this.reservationAdditionalServices

      this._adventureService.saveReservation(this.adventureReservation).subscribe(   // subscribe - da bismo dobili odgovor beka
        (adventureAction: AdventureReservation) => {

          this.dialogRef.close();
        },
        (error) => {
          this._alertService.danger('Doslo je do greske');
        }
        // (HttpStatusCode.Conflict) = { this._alertService.danger('Vec postoji rezervacija u izabranom terminu')}
      )
    }
  }
}
