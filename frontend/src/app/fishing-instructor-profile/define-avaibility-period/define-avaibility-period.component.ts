import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { AdditionalService } from 'src/app/model/additional-service';
import { Address } from 'src/app/model/address';
import { AdventureReservation } from 'src/app/model/adventure-reservation';
import { AdventureProfileService } from 'src/app/service/adventure-profile.service';
import { AdventureReservationService } from 'src/app/service/adventure-reservation.service';

@Component({
  selector: 'app-define-avaibility-period',
  templateUrl: './define-avaibility-period.component.html',
  styleUrls: ['./define-avaibility-period.component.css']
})
export class DefineAvaibilityPeriodComponent implements OnInit {

  adress: Address = new Address(0,'', '', '', 0, 0, 0);
  additionalServices: AdditionalService[] = new Array<AdditionalService>();
  adventureAvaibilityReservation: AdventureReservation = new AdventureReservation(0, '', '', 0, this.additionalServices, 0, true);
  date: Date = new Date();
  endDate: Date = new Date();
  instructorId: number = 0;

  constructor(public dialogRef: MatDialogRef<DefineAvaibilityPeriodComponent>, private _adventureService: AdventureProfileService, private _alertService: AlertService) { }

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
    document.getElementsByName("startDate").setAttribute("min", day);
    //@ts-ignore
    document.getElementByName("endDate").setAttribute("min", day);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  addPeriod() {
    if(this.adventureAvaibilityReservation.startDate === '' || this.adventureAvaibilityReservation.endDate === ''){
      alert('Odaberite datume')
    }else if(Date.parse(this.adventureAvaibilityReservation.startDate) >= Date.parse(this.adventureAvaibilityReservation.endDate)){
      alert('Zavrsetak mora biti nakon pocetka!')
    }else{
      this.adventureAvaibilityReservation.availabilityPeriod = true;
      this.adventureAvaibilityReservation.isAvailable = false;
      this.adventureAvaibilityReservation.adventureId = 0;
      this.adventureAvaibilityReservation.isAction = false;

      var startDate = Date.parse(this.adventureAvaibilityReservation.startDate)   // parsiranje datuma pocetka u milisekunde
      this.date =  new Date(startDate)

      var endingDate = Date.parse(this.adventureAvaibilityReservation.endDate)   // parsiranje datuma pocetka u milisekunde
      this.endDate =  new Date(endingDate)

      this.adventureAvaibilityReservation.startDate = Date.parse(this.date.toString()).toString()
      this.adventureAvaibilityReservation.endDate = Date.parse(this.endDate.toString()).toString()
      this._adventureService.saveUnavailablePeriod(this.adventureAvaibilityReservation,this.instructorId).subscribe(   // subscribe - da bismo dobili odgovor beka
        (adventureReservation: AdventureReservation) => {
          this.dialogRef.close();
        },
        (error) => {
          this._alertService.danger('Doslo je do greske');
        },
      )
    }
  }
}
