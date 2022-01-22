import { Time } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { AdditionalService } from 'src/app/model/additional-service';
import { AdventureReservation } from 'src/app/model/adventure-reservation';
import { FishingAdventure } from 'src/app/model/fishing-adventure';
import { AdditionalServicesService } from 'src/app/service/additional-services.service';
import { AdventureProfileService } from 'src/app/service/adventure-profile.service';
import { AdventureReservationService } from 'src/app/service/adventure-reservation.service';

@Component({
  selector: 'app-add-action-dialog',
  templateUrl: './add-action-dialog.component.html',
  styleUrls: ['./add-action-dialog.component.css']
})
export class AddFishingAdventureActionDialogComponent implements OnInit {

  adventure!: FishingAdventure;
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  actionAdditionalServices: AdditionalService[] = new Array();
  adventureAction: AdventureReservation = new AdventureReservation(0, '', '', 0, this.additionalServices, 0, true);
  durationHours: number = 0;
  durationMinutes: number = 0;
  date: Date = new Date();
  startDate: string = '';

  constructor(public dialogRef: MatDialogRef<AddFishingAdventureActionDialogComponent>, private _adventureService: AdventureProfileService, private _adventureReservationService: AdventureReservationService,
              private _alertService: AlertService) { }

  ngOnInit() {
    this.startDate = new Date().toISOString().slice(0, 16);
  }

  addAction() {
    if(this.adventureAction.startDate === '' || this.durationHours == 0 && this.durationMinutes == 0){
      alert('Odaberite datum!')
    }else if(this.adventureAction.maxGuests > this.adventure.capacity){
      this._alertService.warning('Broj gostiju je veći od dozvoljenog! Maksimum gostiju: ' + this.adventure.capacity);
    }else{
      this.adventureAction.adventureId = this.adventure.id;
      this.adventureAction.isAction = true;
      this.adventureAction.isAvailable = true;
      this.adventureAction.availabilityPeriod = false;

      var startDate = Date.parse(this.adventureAction.startDate)
      this.date =  new Date(startDate)
      var actionStart  = Number(this.date) 
      this.date.setHours(this.date.getHours() + this.durationHours);
      this.date.setMinutes(this.date.getMinutes() + this.durationMinutes);
      var actionEnd = Number(this.date)
      this.adventureAction.startDate = actionStart.toString()
      this.adventureAction.endDate = actionEnd.toString()

      for (let a of this.additionalServices)
      {
          if (a.checked == true)
          {
            this.actionAdditionalServices.push(a)
          }
      }

      this.adventureAction.additionalServices = this.actionAdditionalServices

      this._adventureService.saveReservation(this.adventureAction).subscribe(   // subscribe - da bismo dobili odgovor beka
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
