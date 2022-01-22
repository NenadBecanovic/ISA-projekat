import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { MatDialogRef } from '@angular/material/dialog';
import { AlertService } from 'ngx-alerts';
import { AdditionalService } from 'src/app/model/additional-service';
import { FishingAdventure } from 'src/app/model/fishing-adventure';
import { AdditionalServicesService } from 'src/app/service/additional-services.service';
import { AdventureProfileService } from 'src/app/service/adventure-profile.service';

@Component({
  selector: 'app-edit-adventure-profile-dialog',
  templateUrl: './edit-adventure-profile-dialog.component.html',
  styleUrls: ['./edit-adventure-profile-dialog.component.css']
})
export class EditAdventureProfileDialogComponent implements OnInit {

  adventure!: FishingAdventure;
  additionalServices!: AdditionalService[];
  additionalService: AdditionalService = new AdditionalService(0,"",0,false);

  constructor(public dialogRef: MatDialogRef<EditAdventureProfileDialogComponent>,private _adventureService: AdventureProfileService, private _alertService: AlertService,
          private _additionalServicesService: AdditionalServicesService) { }

  ngOnInit() {
  }

  addService(){
    if(this.additionalService.name != '' && this.additionalService.price != 0){
      this.additionalService.adventureId = this.adventure.id;
      this.additionalService.checked = false;

      this._adventureService.saveAdditionalService(this.additionalService).subscribe(   // subscribe - da bismo dobili odgovor beka
        (additionalService: AdditionalService) => {
          this.reloadServices();
          this.additionalService.name = '';
          this.additionalService.price = 0;
        },
        (error) => {
          this._alertService.danger('Doslo je do greske');
        },
      )
    }
  }

  checkboxChanged($event: MatCheckboxChange) {
    if (this.adventure.cancellationFree == true)
    {
      this.adventure.cancellationFee = 0
    }
  }

  deleteAdditionalService(id: number) {
    this._adventureService.deleteAdditionalService(id,this.adventure.id).subscribe(   // OBAVEZNO SE MORA SUBSCRIBE-OVATI !!!
      (boolean:boolean) =>{
        this.reloadServices()
      },
      (error) => {
        this._alertService.danger('Postoji rezervacija sa odabranom dodatnom uslugom!');
      }
    )
  }

  reloadServices(){
    this._additionalServicesService.getAllByFishingAdventureId(this.adventure.id).subscribe(
      (additionalServices: AdditionalService[]) => {
        this.additionalServices = additionalServices
      }
    )
  }

  editProfile() {
    if (this.adventure.name != '' && this.adventure.promoDescription != '' && this.adventure.address.street != '' && this.adventure.address.city != '' &&
      this.adventure.address.state != '' && this.adventure.address.postalCode >= 0 && this.adventure.pricePerHour > 0 && this.adventure.behaviourRules != '' &&
      this.adventure.address.longitude >= 0 && this.adventure.address.latitude >= 0 &&
      this.adventure.capacity > 0 &&  this.adventure.fishingEquipment != '') {

      if (!this.adventure.cancellationFree && this.adventure.cancellationFee == 0)
      {
        this._alertService.warning('Unesite % nadoknade u slucaju otkazivanja');
      }
      else
      {
        if(this.adventure.cancellationFee > 100)
        {
          this._alertService.warning('Uslovi otkazivanja su u vrednostima 0-100');
        }
        else {
          this._adventureService.edit(this.adventure).subscribe(   // subscribe - da bismo dobili odgovor beka
            (adventure: FishingAdventure) => {
              this.dialogRef.close();
            },
            (error) => {
              // console.log(error)
              this._alertService.danger('Doslo je do greske');
            },
          )
        }
      }
    }
    else {
      this._alertService.warning('Niste ispravno popunili sva polja!');
    }
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
